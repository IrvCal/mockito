package ejemplos.repository;

import ejemplos.domain.Examen;

import java.util.List;

public interface ExamenRepository {
    Examen saveExamen(Examen examen);
    List<Examen> findAll();
}
