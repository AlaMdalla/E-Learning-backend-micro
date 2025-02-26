package E_Learning.Project.Controller;

import E_Learning.Project.DTO.CompetitionDto;
import E_Learning.Project.DTO.ProblemDto;
import E_Learning.Project.Entity.Competition;
import E_Learning.Project.Entity.Problem;
import E_Learning.Project.Service.CompetitionService;
import E_Learning.Project.Service.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Competitions")
public class CompetitionController {
    private CompetitionService competitionService;
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }


    @PostMapping("/")
    CompetitionDto addCompetition(@RequestBody Competition c){
        return this.competitionService.addCompetition(c);
    }
    @GetMapping("/")
    List<Competition> getCompetition(){
        return this.competitionService.getCompetition();    }
    @PutMapping("/{idProblem}/{idCompetition}")
    ResponseEntity<String> addProblemCompetitions(@PathVariable int idProblem, @PathVariable Integer idCompetition){
     CompetitionDto competitionDto=  this.competitionService.addProblemToCompetition(idProblem,idCompetition);

    return ResponseEntity.ok("Success");}

    @GetMapping("/{idCompetition}")
    Competition getCompetition(@PathVariable Integer idCompetition){
        return this.competitionService.getCompetition(idCompetition);    }
}
