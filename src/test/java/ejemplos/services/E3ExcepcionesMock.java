package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class E3ExcepcionesMock {

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
    void manejoExcepciones() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> service.findExamenPorNombreConPreguntas("Matematicas"));
    }
}
