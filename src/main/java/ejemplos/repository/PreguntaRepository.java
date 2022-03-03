package ejemplos.repository;

import java.util.List;

public interface PreguntaRepository {
    void guardarPreguntas (List<String> preguntas);
    List<String> findPreguntasPorExamenId(Long id);
}
