package tn.esprit.pidev.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidev.Entity.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
  @Transactional
  @Modifying
  void deleteByTrainingIdTraining(@Param("trainingId") int trainingId);

  /*@Query("DELETE FROM Evaluation e WHERE e.training.idTraining= :trainingId")
  void deleteByTrainingId(@Param("trainingId") int trainingId);*/


  @Transactional
  @Query("SELECT e FROM Evaluation e WHERE e.training.idTraining = :trainingId")
  Evaluation getEvaluationByTrainingId(@Param("trainingId") int trainingId);


  List<Evaluation> findByTraining_IdTraining(int idTraining);
}
