package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Question Mapper
 */
@Mapper
public interface QuestionMapper {
    /**
     * Query the question by questionId
     * @param questionId
     * @return Question
     */
    @Select("SELECT * FROM question WHERE questionid = #{ID}")
    Question selectQuestionById(@Param("ID") Integer questionId);

    /**
     * Query all questions
     * @return List<Question>
     */
    @Select("SELECT * FROM question")
    List<Question> selectAllQuestions();

    /**
     * Insert the question to the designated quiz
     * @param question
     */
    @SelectKey(statement = "SELECT MAX(questionid) FROM question",keyProperty = "questionId",
            resultType = int.class,keyColumn = "questionid",before = true)
    @Insert("INSERT INTO question(description,answer,level,quizid) VALUES (#{description},#{answer},#{level},#{quizId})")
    void saveQuestion(Question question);

    /**
     * Update the details of question by questionId
     * @param question
     */
    @Update("UPDATE question SET description=#{description},answer=#{answer},level=#{level},quizid=#{quizId} " +
            "WHERE questionid=#{questionId}")
    void update(Question question);

    /**
     * Delete the question by questionId
     * @param questionId
     */
    @Delete("DELETE FROM question WHERE questionid = #{ID}")
    void delete(@Param("ID") Integer questionId);
}
