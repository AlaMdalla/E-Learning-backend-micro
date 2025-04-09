package tn.esprit.pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidev.Entity.QuestionReponse;
import java.util.List;

public interface QuestionReponseRepository extends JpaRepository<QuestionReponse, Integer> {

    @Query(value = "SELECT * FROM question_reponse WHERE id_evaluation = :idEvaluation ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<QuestionReponse> getRandomQuestions(@Param("idEvaluation") int idEvaluation);
}
