package com.www.mianshi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.mianshi.model.entity.Question;
import com.www.mianshi.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 23167
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-12-12 21:24:43
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




