package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.PreguntaRepository;
import ejemplos.repository.PreguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

public class E7OrdenInvocaciones {

    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOrden() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matematicas");
        service.findExamenPorNombreConPreguntas("Espaniol");

        final InOrder inOrder = inOrder(preguntaRepository);
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(100L);
    }
    @Test
    void testOrden2() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matematicas");
        service.findExamenPorNombreConPreguntas("Espaniol");

        InOrder inOrder = inOrder(preguntaRepository);
        //primer examen
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(100L);
        //segundo examen
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(2L);
    }
}
