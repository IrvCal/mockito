package ejemplos.services;

import ejemplos.domain.Examen;

import java.util.Optional;

public interface ExamenService {
    Examen findByNombre(String nombre);
    Optional<Examen> findByNombreOptional(String nombre);
    Examen findExamenPorNombreConPreguntas(String nombre);
    Examen guardarExamen(Examen examen);
}
