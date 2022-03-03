package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class E4ArgumentMatchers {
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
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Data.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");// se tiene que ejecutar el metodo donde se encuentre el metodo que se
        //esta haciendo el mock con el when que en este caso esta en el de arriba

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(
                argThat(
                        argument -> //aqui hace una validacion del argumento que se esta mandando
                                argument != isNull() &&
                                // donde aqui se le dice cual va a ser el argumento que se tuvo qe haber mandado para obtener las preguntas de Matematicas
                                        argument.equals(100L)
                ));
    }
    /**
     * Arguments matcher con una clase o
     * al vuelo
     */
    @Test
    void argumentosPersonalizados() {
        when(repository.findAll()).thenReturn(Data.EXAMENES_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Data.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MisArgumentos()));
    }



    public static class MisArgumentos implements ArgumentMatcher<Long>{

        @Override
        public boolean matches(Long argument) {
            System.out.println("Estoy haciendo una validacion de argumento personalizada");
            return argument != isNull() && argument>0;
        }

        @Override
        public String toString() {
            return "Mis argumentos con validacion personalizada";
        }
    }

}
