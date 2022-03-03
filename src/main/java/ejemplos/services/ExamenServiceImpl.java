package ejemplos.services;

import ejemplos.domain.Examen;
import ejemplos.repository.ExamenRepository;
import ejemplos.repository.PreguntaRepository;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{

    private ExamenRepository repository;
    private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository repository,PreguntaRepository preguntaRepository) {
        this.repository = repository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Examen findByNombre(String nombre) {
        Optional<Examen> examen =
        repository.findAll().stream().filter(e -> e.getNombre().equals(nombre)).findFirst();//Creo que esta y la linea de abajo se pueden juntar en una linea
        if (examen.isPresent())
            return examen.get();
        return null;
    }

    @Override
    public Optional<Examen> findByNombreOptional(String nombre) {
        return  repository.findAll().stream().filter(e -> e.getNombre().equals(nombre)).findFirst();
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examen = findByNombreOptional(nombre);
        Examen e = null;
        if(examen.isPresent()){
            e = examen.orElseThrow();
            List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(e.getId());
            e.setPreguntas(preguntas);
        }
        return e;
    }

    @Override
    public Examen guardarExamen(Examen examen) {
        System.out.println(examen);
        if(!examen.getPreguntas().isEmpty())
            preguntaRepository.guardarPreguntas(examen.getPreguntas());
        return repository.saveExamen(examen);
    }
}
