package ejemplos.domain;

import ejemplos.domain.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Data {

    public static final List<Examen> EXAMENES = Arrays.asList(
            Examen.builder().id(100L).nombre("Matematicas").build(),
            Examen.builder().id(2L).nombre("Espaniol").build());

    public static final List<Examen> EXAMENES_NEGATIVOS = Arrays.asList(
            Examen.builder().id(-100L).nombre("Matematicas").build(),
            Examen.builder().id(-2L).nombre("Espaniol").build());

    public static final List<String> PREGUNTAS = List.of("e1p1","e1p2","e1p3","e2p1","e2p2","e2p3","e3p1");

    public static final List<String> PREGUNTAS_DES = List.of("des1","des2","des3");

    public static final Examen EXAMEN = Examen.builder().id(8L).nombre("Fisica").preguntas(Collections.EMPTY_LIST).build();

    public static final Examen EXAMEN_NO_ID = Examen.builder().nombre("Fisica").preguntas(Collections.EMPTY_LIST).build();

}
