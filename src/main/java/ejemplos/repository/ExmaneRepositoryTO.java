package ejemplos.repository;

import ejemplos.domain.Examen;
import java.util.List;

public class ExmaneRepositoryTO implements ExamenRepository {
    @Override
    public Examen saveExamen(Examen examen) {
        return null;
    }

    @Override
    public List<Examen> findAll() {
        System.out.println("Durmiendo 50000 mls");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error durmiendo 50000 mls");
        }
        return null;
    }
}
