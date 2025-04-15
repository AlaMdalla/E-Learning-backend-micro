package tn.esprit.pidev.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.Entity.QuestionReponse;
import tn.esprit.pidev.Service.IQuestionReponseService;
import tn.esprit.pidev.dto.QuestionReponseDTO;

import java.util.List;

@RestController
@RequestMapping("/trainings/questions")
public class QuestionReponseController {

    private final IQuestionReponseService questionReponseService;


    public QuestionReponseController(IQuestionReponseService questionReponseService) {
        this.questionReponseService = questionReponseService;
    }

    @GetMapping("/retrieve-all")
    public ResponseEntity<List<QuestionReponse>> getAllQuestions() {
        return new ResponseEntity<>(questionReponseService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/retrieve/{idQuestion}")
    public ResponseEntity<QuestionReponse> getQuestionById(@PathVariable("idQuestion") int idQuestion) {
        QuestionReponse question = questionReponseService.getQuestionById(idQuestion);
        if (question != null) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<QuestionReponse> addQuestion(@RequestBody QuestionReponseDTO question) {
        QuestionReponse newQuestion = questionReponseService.addQuestion(question);
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/update/{idQuestion}")
    public ResponseEntity<QuestionReponse> updateQuestion(@PathVariable("idQuestion") int idQuestion, @RequestBody QuestionReponse question) {
        QuestionReponse updatedQuestion = questionReponseService.updateQuestion(idQuestion, question);
        if (updatedQuestion != null) {
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{idQuestion}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("idQuestion") int idQuestion) {
        questionReponseService.deleteQuestion(idQuestion);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


  @GetMapping("/random/{idEvaluation}/{limit}")
  public ResponseEntity<List<QuestionReponse>> getRandomQuestions(
    @PathVariable int idEvaluation,
    @PathVariable int limit) {
    List<QuestionReponse> questions = questionReponseService.getRandomQuestions(idEvaluation, limit);
    return new ResponseEntity<>(questions, HttpStatus.OK);
  }

}
