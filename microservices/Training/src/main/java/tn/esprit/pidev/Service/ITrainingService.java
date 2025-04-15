package tn.esprit.pidev.Service;

import tn.esprit.pidev.Entity.Training;
import tn.esprit.pidev.dto.TrainingDTO;

import java.util.List;

public interface ITrainingService
{ List<TrainingDTO> getAllTrainings();
    Training getTrainingById(int idTraining);
    Training saveTraining(Training training);
    void deleteTraining(int idTraining);

}
