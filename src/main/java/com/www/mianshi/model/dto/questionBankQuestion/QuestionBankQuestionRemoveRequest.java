package com.www.mianshi.model.dto.questionBankQuestion;

import lombok.Data;

@Data
public class QuestionBankQuestionRemoveRequest {

    /**
     * 题库 id
     */
    private Long questionBankId;

    /**
     * 题目 id
     */
    private Long questionId;
}
