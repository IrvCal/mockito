package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.domain.Examen;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.ExamenRepositoryImpl;
import ejemplos.repository.PreguntaRepository;
import ejemplos.repository.PreguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class E6Spys {
//    @Mock                                                                                            //->>>5. poner etiquetas y colocar clases concretas
    @Spy
//    ExamenRepository repository;
    ExamenRepositoryImpl repository;
//    @Mock
    @Spy
//    PreguntaRepository preguntaRepository;
    PreguntaRepositoryImpl preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSpy() {
        //->>>1.Hacer los objs con spy
//        El spy requiere que se cree a partir de una clase concreta NO abstracta ni interfaz porque
        //usa metodos reales
        ExamenRepository repository = spy(ExamenRepositoryImpl.class);
        PreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
        //como se hizo todos a mano se tiene que agrgar esto
        ExamenService service = new ExamenServiceImpl(repository,preguntaRepository);

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertNotNull(examen);

        System.out.println(examen);

    }
    @Test
    void testSpyMetodoMock() {
//        El spy requiere que se cree a partir de una clase concreta NO abstracta ni interfaz porque
        //usa metodos reales
//        ExamenRepository repository = spy(ExamenRepositoryImpl.class);                                  //->>>4.cambiar a las anotaciones @Spy
//        PreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
        //como se hizo todos a mano se tiene que agrgar esto
//        ExamenService service = new ExamenServiceImpl(repository,preguntaRepository);

        //Se puede hacer esto para que el set que se devuelve sea otro que el real
        //aqui se destaca porque hace como una especie de ejecucion de los dos metodos
        //el real y luego el mock pero funciona bien, regresa lo que en el mock se diga
//        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Data.PREGUNTAS_DES);   //->>>2.probar la ejecucion del metodo "doble"
        //Para evitar el comportamiento anterior esto se puede camiar por un doRetorn
        //y queda mas limpio
        doReturn(Data.PREGUNTAS_DES).when(preguntaRepository).findPreguntasPorExamenId(anyLong());      //->>>3.Hacer limpia la ejecucion con doReturn

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertNotNull(examen);

        System.out.println(examen);

    }
}
