package com.example.demo.controller;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RestController
public class QuestionController {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @GetMapping("")
    public ResponseEntity<Question> getQuestion(@RequestParam(name = "Id") int questionId){
        Question question = questionMapper.selectQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questionList = questionMapper.selectAllQuestions();
        return ResponseEntity.ok(questionList);
    }

    @PostMapping("")
    public ResponseEntity<String> saveQuestion(@RequestBody Question question) {
        questionMapper.saveQuestion(question);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuestion(@RequestParam(name = "Id") int questionId){
        questionMapper.delete(questionId);
        return ResponseEntity.ok("Success");
    }
}
