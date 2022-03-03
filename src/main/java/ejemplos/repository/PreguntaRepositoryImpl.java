package ejemplos.repository;

import ejemplos.domain.Data;

import java.util.List;

public class PreguntaRepositoryImpl implements PreguntaRepository{
    @Override
    public void guardarPreguntas(List<String> preguntas) {
        System.out.println("PreguntaRepositoryImpl.guardarPreguntas");
    }

    @Override
    public List<String> findPreguntasPorExamenId(Long id) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("PreguntaRepositoryImpl.findPreguntasPorExamenId");
        return Data.PREGUNTAS;
    }
}
