package com.example.demo.mapper;

import com.example.demo.model.Quiz;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuizMapper {
    @Select("SELECT * FROM quiz WHERE ID = #{ID}")
    @Results(id="quiz",value = {
            @Result(column = "TIMELIMIT",property = "timeLimit"),
            @Result(column = "FULLMARK",property = "fullMark")
            //, @Result()
    })
    Quiz selectQuizById(@Param("ID") Integer quizId);

    @Select("SELECT * FROM quiz")
    List<Quiz> selectAllQuizzes();

    @Insert("INSERT INTO quiz(TIMELIMIT,FULLMARK,questions) " +
            "VALUES (#{timeLimit},#{fullMark},#{questions,jdbcType=VARCHAR," +
            "typeHandler=com.example.demo.util.ListTypeHandler" )
    void saveQuiz(Quiz quiz);
}
