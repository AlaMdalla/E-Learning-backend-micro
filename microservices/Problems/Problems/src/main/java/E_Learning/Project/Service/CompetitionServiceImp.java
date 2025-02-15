package E_Learning.Project.Service;

import E_Learning.Project.DTO.CompetitionDto;
import E_Learning.Project.DTO.ProblemDto;
import E_Learning.Project.Entity.Competition;
import E_Learning.Project.Entity.Problem;

import E_Learning.Project.Mapper.CompetitionMapper;
import E_Learning.Project.Mapper.CompetitionMapperImp;
import E_Learning.Project.Mapper.ProblemMapper;
import E_Learning.Project.Mapper.ProblemMapperImpl;
import E_Learning.Project.Repository.CompetitionRepository;
import E_Learning.Project.Repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompetitionServiceImp implements CompetitionService{
    public CompetitionServiceImp(CompetitionRepository competitionRepository,ProblemRepository problemRepository) {
        this.competitionRepository = competitionRepository;
        this.problemRepository=problemRepository;
    }

    private CompetitionRepository competitionRepository;
    private ProblemRepository problemRepository;

    private CompetitionMapper competitionMapper =new CompetitionMapperImp();


    @Override
    public CompetitionDto addCompetition(Competition competition) {

        Competition competition1= this.competitionRepository.save(competition);
        return competitionMapper.toDto(competition1);
    }

    @Override
    public List<Competition> getCompetition() {
        return this.competitionRepository.findAll();
    }

    @Override
    public Competition getCompetition(Integer id) {
        return this.competitionRepository.findById(id).get();
    }

    @Override
    public String deleteCompetition(Integer id) {
        return "";
    }
    @Override
    public CompetitionDto addProblemToCompetition(Integer idProb,Integer idComp){
        Competition competition= this.competitionRepository.findById(idComp).get();
        Problem problem =this.problemRepository.findById(idProb).get();
       List<Problem> problems= competition.getProblems();
       problems.add(problem);
       competition.setProblems(problems);
       this.competitionRepository.save(competition);
       return competitionMapper.toDto(competition);


    }

}
