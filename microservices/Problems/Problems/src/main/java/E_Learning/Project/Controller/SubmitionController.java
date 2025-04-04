package E_Learning.Project.Controller;

import E_Learning.Project.CodeRunner;
import E_Learning.Project.Entity.Problem;
import E_Learning.Project.Entity.Submition;
import E_Learning.Project.Service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/submitions")
public class SubmitionController {
    private SubmitionService submitionService;
    FileReaderService fileReaderService = new FileReaderServiceImpl();
    CodeCompiler codeCompiler = new CodeCompilerJavaImpl();
    TestCaseExecutor testCaseExecutor = new TestCaseExecutorJavaImpl();

    public SubmitionController(SubmitionService submitionService, ProblemService problemService) {
        this.submitionService = submitionService;
        this.problemService = problemService;
    }

    private ProblemService problemService;



    @PostMapping("/submit/{userId}/{problemId}")
    public ResponseEntity<?> submitProblem(@RequestBody String code,@PathVariable Integer userId, @PathVariable Integer problemId) {
        Submition submition =new Submition();
        Problem problem = this.submitionService.submitProblem(submition,userId,problemId);
        String mainClass = problem.getMainClass();
        CodeRunner codeRunner = new CodeRunner(fileReaderService, codeCompiler, testCaseExecutor,problem.getLinkTotestcases());
        codeRunner.run(mainClass,code);
        try {
            List<Submition> submissions = problem.getSubmitions();
            if (submissions != null && !submissions.isEmpty()) {
                Submition lastSubmition = submissions.get(submissions.size() - 1);
                Integer lastSubmitionId = lastSubmition.getId();
                submition.setId(lastSubmitionId);
            }

if(codeRunner.result.equals("✅ All test cases passed!")){
    submition.setPassed(true);
    submition =  this.submitionService.Update(submition,submition.getId());
}
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "text/plain")
                    .body(codeRunner.result);

        }catch (Exception exception)
        {
            System.out.println("pppp");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/plain")
                .body("noooo");
    }
    @GetMapping("/")
    List<Submition> getSubmitions(){
        return this.submitionService.getSubmitions();    }

    @GetMapping("/{userId}")
    List<Submition> getSubmitionsByUser(@PathVariable Integer userId){
        return this.submitionService.getSubmitions().stream().filter(submition -> submition.getUserId()==userId).toList();    }

    @GetMapping("getProblemTitle/{submitId}")
    String getProblemTitle(@PathVariable Integer submitId){
        return this.submitionService.getSubmitions().stream().filter(submition -> submition.getId()==submitId)
                .map(submition -> submition.getProblem().getTitle()).findAny().get(); }
}
