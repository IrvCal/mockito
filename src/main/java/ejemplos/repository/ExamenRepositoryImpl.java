package ejemplos.repository;

import ejemplos.domain.Data;
import ejemplos.domain.Examen;

import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository{
    @Override
    public Examen saveExamen(Examen examen) {
        return examen;
    }

    /**
     * Con mokito se supone que esta clase no seria tan
     * indispensable porque desde ahi se puede hacer una
     * especie de simulacro
     * @return
     */
    @Override
    public List<Examen> findAll() {
         List.of(
                Examen.builder()
                        .id(1L)
                        .nombre("Matematicas")
                        .preguntas(
                                List.of("pregunta 1 de matematicas",
                                        "pregunta 2 de matematics",
                                        "pregunta 3 de matematics"
                                )
                        )
                        .build(),
                Examen.builder()
                        .id(2L)
                        .nombre("Espaniol")
                        .preguntas(
                                List.of("pregunta 1 de Espaniol",
                                        "pregunta 2 de Espaniol",
                                        "pregunta 3 de Espaniol"
                                )
                        )
                        .build(),
                Examen.builder()
                        .id(3L)
                        .nombre("Geografia")
                        .preguntas(
                                List.of("pregunta 1 de Geografia",
                                        "pregunta 2 de Geografia",
                                        "pregunta 3 de Geografia"
                                )
                        )
                        .build()
        );
        return Data.EXAMENES;
    }
}
