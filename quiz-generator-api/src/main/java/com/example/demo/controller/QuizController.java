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

    /**
     * Query the quiz by quizId
     * @param quizId
     * @return ResponseEntity.ok(quiz)
     */
    @GetMapping("")
    public ResponseEntity<Quiz> getQuiz(@RequestParam(name = "Id") int quizId){
        Quiz quiz = quizMapper.selectQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    /**
     * Query all quizzes
     * @return ResponseEntity.ok(quizList)
     */
    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        List<Quiz> quizList = quizMapper.selectAllQuizzes();
        return ResponseEntity.ok(quizList);
    }

    /**
     * Insert quiz
     * @param quiz
     * @return ResponseEntity.ok("Success")
     */
    @PostMapping("")
    public ResponseEntity<String> saveQuiz(@RequestBody Quiz quiz) {
        quizMapper.saveQuizwithQuestions(quiz);

        return ResponseEntity.ok("Success");
    }

    /**
     * Delete quiz by quizId
     * @param quizId
     * @return ResponseEntity.ok("Success")
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuiz(@RequestParam(name = "Id") int quizId){
        quizMapper.deleteQuizwithQuestions(quizId);
        return ResponseEntity.ok("Success");
    }

}
