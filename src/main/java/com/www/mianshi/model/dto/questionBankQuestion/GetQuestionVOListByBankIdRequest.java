package com.www.mianshi.model.dto.questionBankQuestion;

import com.www.mianshi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetQuestionVOListByBankIdRequest extends PageRequest {

    private Long bankId;

    private String title;

    private Integer needVIP;

    private List<String> tags;
}
