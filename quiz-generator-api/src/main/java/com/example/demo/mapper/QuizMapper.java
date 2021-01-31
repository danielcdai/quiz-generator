package com.example.demo.mapper;

import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuizMapper {
    @Select("SELECT * FROM quiz WHERE quizid = #{ID}")
    @Results(
            @Result(property = "questions", column = "quizid",
                    javaType = List.class,many = @Many(select = "selectQuestions"))
    )
    Quiz selectQuizById(@Param("ID") Integer quizId);

    @Select("SELECT * FROM quiz")
    @Results(@Result(property = "questions", column = "quizid",
            javaType = List.class,many = @Many(select = "selectQuestions")))
    List<Quiz> selectAllQuizzes();

    @Select("SELECT * FROM question WHERE quizid = #{quizid}")
    List<Question> selectQuestions(String quizid);

    @SelectKey(statement = "SELECT MAX(quizid) FROM quiz",keyProperty = "quizId",
            resultType = int.class,keyColumn = "quizid",before = true)
    @Insert("INSERT INTO quiz(timelimit,fullmark) VALUES (#{timeLimit},#{fullMark})")
    int saveQuiz(Quiz quiz);

    @SelectKey(statement = "SELECT MAX(quizid) FROM quiz",keyProperty = "quizId",
            resultType = int.class,keyColumn = "quizid",before = true)
    @Insert("<script>"+
            "INSERT INTO question(description,answer,level,quizid) VALUES" +
            "<foreach collection='list' index='index' item='item' open='(' separator='),(' close=')'>"+
            "#{item.description},#{item.answer},#{item.level},#{quizId}"+
            "</foreach>"+
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "quizId", keyColumn = "quizid")
    int saveQuestions(List<Question> questions);

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
