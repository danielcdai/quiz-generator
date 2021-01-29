package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Select("SELECT * FROM question WHERE ID = #{ID}")
    Question selectQuestionById(@Param("ID") Integer questionId);

    @Select("SELECT * FROM question")
    List<Question> selectAllQuestions();

    @Insert("INSERT INTO question(description,answer,level) VALUES (#{description},#{answer},#{level})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void saveQuestion(Question question);

    @Update("UPDATE question SET description=#{description},answer=#{answer},level=#{level} WHERE ID=#{ID}")
    void update(Question question);

    @Delete("DELETE FROM question WHERE ID = #{ID}")
    void delete(@Param("ID") Integer questionId);
}
