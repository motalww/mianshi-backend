package com.www.mianshi.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建题目请求
 *
 *  @author <a href="https://github.com/motalww">www</a>
 *
 */
@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 推荐答案
     */
    private String answer;

    /**
     * 仅会员可见，0:否 1:是
     */
    private int needVip;


    private static final long serialVersionUID = 1L;
}