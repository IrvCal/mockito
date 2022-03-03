package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.domain.Examen;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.PreguntaRepository;
import ejemplos.repository.PreguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class E5CaptureArgument {
    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntaRepository;

    @Mock
    PreguntaRepositoryImpl preguntaRepositoryImpl;//no se esta inyectando porque tengo que quitar el de arriba
    //este se usa en el test de doCallRealMethod (quitar el de arriba para que se use este)

    @Captor
    ArgumentCaptor<Long> captor;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testArgumentCapture() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matematicas");

//        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(preguntaRepository).findPreguntasPorExamenId(captor.capture());
        assertEquals(100L,captor.getValue());
    }

    /**
     * Metodos DO
     */
    //la primera prueba es con un metodo que es void pero se le pone un thenReturn

    @Test
    void testDoThrow() {
//        asi no se puede porque como el metodo es void thenThrow requiere que el metodo tenga retrn
//        when(preguntaRepository.guardarPreguntas(anyList())).thenThrow(IllegalArgumentException.class);

        // para este tipo de metodos se hace al reves con el doThrow
        doThrow(IllegalArgumentException.class).when(preguntaRepository).guardarPreguntas(anyList());
        Examen examen = Data.EXAMEN;
        assertThrows(IllegalArgumentException.class, () ->
                service.guardarExamen(examen)
        );
    }

    @Test
    void testDoAnswer() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        //esto es un evento mas personalizado dependiendo del argumento
        // aqui por ejemplo se quiere que el comportameinto cambie para id 5
        doAnswer(invocation -> {
           Long id = invocation.getArgument(0);
           return id == 100L? Data.PREGUNTAS:isNull();
        }).when(preguntaRepository).findPreguntasPorExamenId(anyLong());
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        assertEquals(100L,examen.getId());
        assertEquals("Matematicas",examen.getNombre());
        assertNotNull(examen.getPreguntas());

        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    /**
     * En este test se mejora la forma en que se incrementaba el id
     * esto ya se habia hecho en un ejercicioanterior pero aqui se
     * hace diferente
     */
    @Test
    void testNuevaFormaParaIncrementoId() {
        //GIVEN
        Examen examen = Data.EXAMEN_NO_ID;
        examen.setPreguntas(Data.PREGUNTAS);
        /*
        Este era el anterior
        when(repository.saveExamen(any(Examen.class))).then(new Answer<Examen>() {
            Long id = 8L;
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen1 = invocation.getArgument(0);
                examen1.setId(id++);
                return examen1;
            }
        });
        */
        doAnswer(invocation -> {
            Long id = 8L;
            Examen examen1 = invocation.getArgument(0);
            examen1.setId(++id);
            return examen1;
        }).when(repository).saveExamen(any(Examen.class));
        //WHEN
        Examen examenGuardado = service.guardarExamen(examen);
        assertNotNull(examenGuardado);
        assertEquals(9L,examenGuardado.getId());
    }

    @Test
    void testDoCallRealMock() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);

        doCallRealMethod().when(preguntaRepositoryImpl)//se tiene que poner la implementacion de un metodo y no Una Interfaz
                .findPreguntasPorExamenId(anyLong());//aqui se ejecuta el metodo por lo que EN REALIDAD DEVUELVE LO QUE ESTA EN EL METODO

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertNotNull(examen);

    }
}
