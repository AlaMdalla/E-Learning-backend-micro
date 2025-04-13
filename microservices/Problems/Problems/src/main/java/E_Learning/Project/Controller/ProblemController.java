package E_Learning.Project.Controller;

import E_Learning.Project.CodeRunner;
import E_Learning.Project.DTO.ProblemDto;
import E_Learning.Project.Entity.Problem;
import E_Learning.Project.Enums.Tags;
import E_Learning.Project.Service.*;
import jakarta.persistence.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController

    @RequestMapping("/problems")


public class ProblemController {

private ProblemService problemService;
    FileReaderService fileReaderService = new FileReaderServiceImpl();
    CodeCompiler codeCompiler = new CodeCompilerJavaImpl();
    TestCaseExecutor testCaseExecutor = new TestCaseExecutorJavaImpl();

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/")

    ProblemDto addProblem(@RequestBody Problem p){
        return this.problemService.addProblem(p);
    }
    @GetMapping("/")
    List<Problem> getProblems(){
        return this.problemService.getProblems();    }
    @GetMapping("/{id}")
    Problem getProblem(@PathVariable Integer id){
        return this.problemService.getProblem(id);    }
    @DeleteMapping("/{id}")
    String deleteProblem(@PathVariable Integer id){
        return this.problemService.deleteProblem(id);
    }
    @PutMapping("/{id}")
    Problem editProblem(@PathVariable Integer id,@RequestBody Problem p){
        return this.problemService.updateProblem(id,p);
    }
<<<<<<< HEAD
    @GetMapping("passedProblems/{userid}")
    List<String> getPassedProblem(@PathVariable Integer userid){
        List<Problem> problems=this.problemService.getProblems();
      return   problems.stream().filter(problem -> problem.getSubmitions()
                .stream().anyMatch(submition ->  submition.getUserId()==userid && submition.isPassed()) ).map(problem -> problem.getTitle())
              .toList();
    // problems=   problems.stream().filter(problem -> problem.getSubmitions().stream().filter(submition -> submition.getUserId()==userid && submition.isPassed()).isParallel()).toList();

    }
    @GetMapping("doneProblems/{userid}")
    List<String> getalldoneProblems(@PathVariable Integer userid){
        List<Problem> problems=this.problemService.getProblems();
        return   problems.stream().filter(problem -> problem.getSubmitions()
                        .stream().anyMatch(submition ->  submition.getUserId()==userid ) ).map(problem -> problem.getTitle())
                .toList();
        // problems=   problems.stream().filter(problem -> problem.getSubmitions().stream().filter(submition -> submition.getUserId()==userid && submition.isPassed()).isParallel()).toList();

    }
=======
>>>>>>> origin/job

}
