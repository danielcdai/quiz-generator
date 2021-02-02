package com.example.demo.mapper;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Quiz Mapper
 */
@Mapper
public interface QuizMapper {
    /**
     * Query the quiz by quizId
     * @param quizId
     * @return Quiz
     */
    @Select("SELECT * FROM quiz WHERE quizId = #{quizId}")
    @Results(id = "quiz",value = {
            @Result(property = "questions", column = "quizId", javaType = List.class,
                    many = @Many(select = "selectQuestions")),
            @Result(property = "quizId", column = "quizId")
    })
    Quiz selectQuizById(Integer quizId);

    /**
     * Query all questions in the designed quiz
     * @param quizId
     * @return List<Question>
     */
    @Select("SELECT * FROM question WHERE quizid = #{quizId}")
    List<Question> selectQuestions(Integer quizId);

    /**
     * Query all quizzes
     * @return List<Quiz>
     */
    @Select("SELECT * FROM quiz")
    @ResultMap("quiz")
    List<Quiz> selectAllQuizzes();


    /**
     * Insert a quiz without questions
     * @param quiz
     */
    @SelectKey(statement = "SELECT MAX(quizid) FROM quiz",keyProperty = "quizId",
            resultType = int.class,keyColumn = "quizid",before = true)
    @Insert("INSERT INTO quiz(timelimit,fullmark) VALUES (#{timeLimit},#{fullMark})")
    void saveQuiz(Quiz quiz);

    /**
     * Insert the questions of the quiz
     * @param questions
     */
    @SelectKey(statement = "SELECT MAX(quizid) FROM quiz",keyProperty = "quizId",
            resultType = int.class,keyColumn = "quizid",before = true)
    @Insert("<script>"+
            "INSERT INTO question(description,answer,level,quizid) VALUES" +
            "<foreach collection='list' index='index' item='item' open='(' separator='),(' close=')'>"+
            "#{item.description},#{item.answer},#{item.level},#{quizId}"+
            "</foreach>"+
            "</script>")
    void saveQuestions(List<Question> questions);

    /**
     * Define a default function to insert the quiz and related questions
     * @param quiz
     */
    default void saveQuizwithQuestions(Quiz quiz){
        saveQuiz(quiz);
        saveQuestions(quiz.getQuestions());
    };

    /**
     * Delete questions by quizId
     * @param quizId
     */
    @Delete("DELETE FROM question WHERE quizid = #{quizId}")
    void deleteQuestions(Integer quizId);

    /**
     * Delete quiz by quizId
     * @param quizId
     */
    @Delete("DELETE FROM quiz WHERE quizid = #{quizId}")
    void deleteQuizById(Integer quizId);

    /**
     * Define a default function to delete the quiz and related questions
     * @param quizId
     */
    default void deleteQuizwithQuestions(Integer quizId){
        deleteQuestions(quizId);
        deleteQuizById(quizId);
    };
}
