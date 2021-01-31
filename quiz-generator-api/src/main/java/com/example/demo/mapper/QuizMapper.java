package com.example.demo.mapper;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuizMapper {
    @Select("SELECT * FROM quiz WHERE quizId = #{quizId}")
    @Results(id = "quiz",value = {@Result(property = "questions", column = "quizId",
            javaType = List.class, many = @Many(select = "selectQuestions")),@Result(property = "quizId", column = "quizId")
    })
    Quiz selectQuizById(Integer quizId);

    @Select("SELECT * FROM quiz")
    @ResultMap("quiz")
    List<Quiz> selectAllQuizzes();

    @Select("SELECT * FROM question WHERE quizid = #{quizId}")
    List<Question> selectQuestions(String quizId);

    @SelectKey(statement = "SELECT MAX(quizid) FROM quiz",keyProperty = "quizId",
            resultType = int.class,keyColumn = "quizid",before = true)
    @Insert("INSERT INTO quiz(timelimit,fullmark) VALUES (#{timeLimit},#{fullMark})")
    void saveQuiz(Quiz quiz);

    @SelectKey(statement = "SELECT MAX(quizid) FROM quiz",keyProperty = "quizId",
            resultType = int.class,keyColumn = "quizid",before = true)
    @Insert("<script>"+
            "INSERT INTO question(description,answer,level,quizid) VALUES" +
            "<foreach collection='list' index='index' item='item' open='(' separator='),(' close=')'>"+
            "#{item.description},#{item.answer},#{item.level},#{quizId}"+
            "</foreach>"+
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "quizId", keyColumn = "quizid")
    void saveQuestions(List<Question> questions);

    default void saveQuizwithQuestions(Quiz quiz){
        saveQuiz(quiz);
        saveQuestions(quiz.getQuestions());
    };

    @Delete("DELETE FROM question WHERE quizid = #{quizId}")
    void deleteQuestions(Integer quizId);
    @Delete("DELETE FROM quiz WHERE quizid = #{quizId}")
    void deleteQuizById(Integer quizId);

    default void deleteQuizwithQuestions(Integer quizId){
        deleteQuestions(quizId);
        deleteQuizById(quizId);
    };
}
