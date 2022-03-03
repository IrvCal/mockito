package ejemplos.services;

import ejemplos.domain.Data;
import ejemplos.domain.Examen;
import ejemplos.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class E1ExamenServiceImplTest {

    ExamenRepository repository;
    ExamenService service;
    PreguntaRepository preguntaRepository;

    @BeforeEach
    void setUp() {
        repository = mock(ExamenRepository.class);
        preguntaRepository = Mockito.mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(repository,preguntaRepository);
    }

    @Test
    void findExamenPorNombre() {
        Examen examen = service.findByNombre("Matematicas");
        assertNotNull(examen);
        assertEquals(1, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
    }

    /*
    hasta este punto con el test anterior tenemos que el repo retorna
    o mas bien cuanta con todos los datos indicados en su construccion
    o de la bd pero que pasaria si queremos que retorne una lista
    NULL, para eso sirve mockito
    */
    //test con mockito
    @Test
    void findExamenPorNombreMockito() {
        ExamenRepository repository = Mockito.mock(ExamenRepository.class);// se puede hacer una importacion static y quitrar Mockito y solo dejar mock


        List<Examen> examenes = Arrays.asList(Examen.builder().id(5L).nombre("Matematicas").build(),Examen.builder().id(2L).nombre("Espaniol").build());

        when(repository.findAll()) //cuando se llame a este metodo para esa instancia
                .thenReturn(examenes); // se va a retornar esto

        Examen examen = service.findByNombre("Matematicas");
        assertNotNull(examen);
        assertEquals(1, examen.getId());

    }
    @Test
    void findExamenPorNombreMockitoOTRO() {
        ExamenRepository repository = Mockito.mock(ExmaneRepositoryTO.class);
        List<Examen> examenes = Arrays.asList(
                Examen.builder().id(5L).nombre("Matematicas").build(),
                Examen.builder().id(2L).nombre("Espaniol").build());

        when(repository.findAll()) //cuando se llame a este metodo para esa instancia
                .thenReturn(examenes); // se va a retornar esto, en este caso es como una
        // sobreescritura del metodo de la clase original

        Examen examen = service.findByNombre("Matematicas");
        assertNotNull(examen);
        assertEquals(5, examen.getId());
    }
    /**
     * Aqui se van a hacer pruebas con el metodo que devuelve un Optional (WHEN)
     */
    @Test
    void findExamenPorNombreMockitoOptional() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        Optional<Examen> examen = service.findByNombreOptional("Matematicas");

        assertTrue(examen.isPresent());
        assertEquals(100, examen.orElseThrow().getId());// se puede usar .get pero es mejor .orElseThrow
        assertEquals("Matematicas", examen.orElseThrow().getNombre());
    }
    @Test
    void findExamenPorNombreMockitoOptionalVACIA() {
        ExamenRepository repository = Mockito.mock(ExmaneRepositoryTO.class);
        List<Examen> examenes = Collections.EMPTY_LIST;
        when(repository.findAll()).thenReturn(examenes);
        Optional<Examen> examen = service.findByNombreOptional("Matematicas");
        assertFalse(examen.isPresent());
    }

    @Test
    void preguntasExamen() {
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(100L))//tambien se puede poenr anyLong()
                .thenReturn(Data.PREGUNTAS);
        //lo que se prueba debajo correspondera a todos los datos iniciados con Data
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(100,examen.getId());
        assertEquals(7,examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("e1p1"));
    }
    /**
     * Verify
     */
    @Test
    void restPreguntasExamenVerify(){
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Data.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(100,examen.getId());

        verify(repository).findAll();

        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }
    @Test
    void testPreguntasExamen(){
        when(repository.findAll()).thenReturn(Data.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Data.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Trigonometria avanzada");
        assertNull(examen);
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }
}