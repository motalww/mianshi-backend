package com.www.mianshi.model.dto.questionBankQuestion;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建题目题库关联请求
 *
 *  @author <a href="https://github.com/motalww">www</a>
 *
 */
@Data
public class QuestionBankQuestionAddRequest implements Serializable {
    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}