package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.domain.Examen;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
En  esta clase se haran ejercicios como en el otro test
pero se va a ver como Mockito tambien tiene inyeccion de dependencias
esto se hace con la anotacion @Mock y el que tiene el constructor @InjectMocks
 */

class E2ExamenesTest {
    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;
    // como es anotacion de mockito se tiene que indicar
    //de que implementacion en concreto es de la que se hara la instanciacion

    @BeforeEach
    void setUp() {
        // se tiene que habilitar el uso de anotaciones
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findExamen() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        Examen examen = service.findByNombre("Matematicas");
        assertNotNull(examen);
    }

    @Test
    void guardarExamenSinPreguntas () {
        when(repository.saveExamen(any(Examen.class))).thenReturn(Data.EXAMEN);
        Examen examen = service.guardarExamen(Data.EXAMEN);
        assertNotNull(examen);
        assertEquals(8L,examen.getId());
        assertEquals("Fisica",examen.getNombre());
        verify(repository).saveExamen(any());//incluso se puede dejar como un parametro vacio que CREO que es t
    }

    @Test
    void guardarExamenConPreguntas () {
        Examen examen = Data.EXAMEN;
        examen.setPreguntas(Data.PREGUNTAS);
        when(repository.saveExamen(any(Examen.class))).thenReturn(examen);// le tuve que  mockear el comportamiento y bien no se porque
        Examen examenGuardado = service.guardarExamen(examen);
        assertNotNull(examenGuardado);
        assertEquals(8L,examenGuardado.getId());
        assertEquals("Fisica",examenGuardado.getNombre());
        verify(repository).saveExamen(any());//incluso se puede dejar como un parametro vacio que CREO que es t
        verify(preguntaRepository).guardarPreguntas(anyList());
    }
    @Test
    void guardarExamenConPreguntasAnswer () {
        //GIVEN
        Examen examen = Data.EXAMEN;
        examen.setPreguntas(Data.PREGUNTAS);
        when(repository.saveExamen(any(Examen.class))).then(new Answer<Examen>() {
            Long id = 8L;
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen1 = invocation.getArgument(0);
                examen1.setId(id++);
                return examen1;
            }
        });
        //WHEN
        Examen examenGuardado = service.guardarExamen(examen);
        Examen examenGuardado2 = service.guardarExamen(examen);
        assertNotNull(examenGuardado);
        assertEquals(9L,examenGuardado.getId());
        assertEquals(10L,examenGuardado2.getId());
        assertEquals("Fisica",examenGuardado.getNombre());
        verify(repository).saveExamen(any());
        verify(preguntaRepository).guardarPreguntas(anyList());
    }


}
