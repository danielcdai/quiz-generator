package com.example.demo.controller;

import com.example.demo.mapper.QuizMapper;
import com.example.demo.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/quiz")
@RestController
public class QuizController {
    @Autowired(required = false)
    private QuizMapper quizMapper;

    @GetMapping("")
    public ResponseEntity<Quiz> getQuestion(@RequestParam(name = "Id") int quizId){
        Quiz quiz = quizMapper.selectQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuestions(){
        List<Quiz> questionList = quizMapper.selectAllQuizzes();
        return ResponseEntity.ok(questionList);
    }

    @PostMapping("")
    public ResponseEntity<String> saveQuestion(@RequestBody Quiz quiz) {
        quizMapper.saveQuiz(quiz);
        return ResponseEntity.ok("Success");
    }
}
