package E_Learning.Project.Service;

import E_Learning.Project.DTO.CompetitionDto;
import E_Learning.Project.DTO.ProblemDto;
import E_Learning.Project.Entity.Competition;
import E_Learning.Project.Entity.Problem;

import java.util.List;

public interface CompetitionService {
    public CompetitionDto addCompetition(Competition competition);
    public List<Competition> getCompetition();
    public CompetitionDto addProblemToCompetition(Integer idProb,Integer idComp);
    public Competition getCompetition( Integer id);
        public String deleteCompetition( Integer id);
}
