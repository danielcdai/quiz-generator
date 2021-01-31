package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Select("SELECT * FROM question WHERE questionid = #{ID}")
    Question selectQuestionById(@Param("ID") Integer questionId);

    @Select("SELECT * FROM question")
    List<Question> selectAllQuestions();

    @SelectKey(statement = "SELECT MAX(questionid) FROM question",keyProperty = "questionId",
            resultType = int.class,keyColumn = "questionid",before = true)
    @Insert("INSERT INTO question(description,answer,level,quizid) VALUES (#{description},#{answer},#{level},#{quizId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void saveQuestion(Question question);

    @Update("UPDATE question SET description=#{description},answer=#{answer},level=#{level} " +
            "WHERE questionid=#{ID}")
    void update(Question question);

    @Delete("DELETE FROM question WHERE questionid = #{ID}")
    void delete(@Param("ID") Integer questionId);
}
