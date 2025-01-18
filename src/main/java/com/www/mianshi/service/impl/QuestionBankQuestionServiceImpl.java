package com.www.mianshi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.mianshi.common.ErrorCode;
import com.www.mianshi.constant.CommonConstant;
import com.www.mianshi.exception.ThrowUtils;
import com.www.mianshi.mapper.QuestionBankQuestionMapper;
import com.www.mianshi.model.dto.questionBankQuestion.GetQuestionVOListByBankIdRequest;
import com.www.mianshi.model.dto.questionBankQuestion.QuestionBankQuestionQueryRequest;
import com.www.mianshi.model.entity.Question;
import com.www.mianshi.model.entity.QuestionBank;
import com.www.mianshi.model.entity.QuestionBankQuestion;
import com.www.mianshi.model.entity.User;
import com.www.mianshi.model.vo.QuestionBankQuestionVO;
import com.www.mianshi.model.vo.QuestionListVO;
import com.www.mianshi.model.vo.QuestionVO;
import com.www.mianshi.model.vo.UserVO;
import com.www.mianshi.service.QuestionBankQuestionService;
import com.www.mianshi.service.QuestionBankService;
import com.www.mianshi.service.QuestionService;
import com.www.mianshi.service.UserService;
import com.www.mianshi.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 题目题库关联服务实现
 *
 * @author <a href="https://github.com/motalww">www</a>
 */
@Service
@Slf4j
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion> implements QuestionBankQuestionService {

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private QuestionService questionService;

    @Resource
    @Lazy
    private QuestionBankService questionBankService;

    /**
     * 校验数据
     *
     * @param questionBankQuestion
     * @param add                  对创建的数据进行校验
     */
    @Override
    public void validQuestionBankQuestion(QuestionBankQuestion questionBankQuestion, boolean add) {
        ThrowUtils.throwIf(questionBankQuestion == null, ErrorCode.PARAMS_ERROR);
        //题目和题库必须存在
        Long questionBankId = questionBankQuestion.getQuestionBankId();
        if (questionBankId != null) {
            ThrowUtils.throwIf(questionBankId <= 0, ErrorCode.PARAMS_ERROR, "题库参数异常");
            QuestionBank questionBank = questionBankService.getById(questionBankId);
            ThrowUtils.throwIf(questionBank == null, ErrorCode.NOT_FOUND_ERROR, "题库不存在");
        }
        Long questionId = questionBankQuestion.getQuestionId();
        if (questionId != null) {
            ThrowUtils.throwIf(questionId <= 0, ErrorCode.PARAMS_ERROR, "题目参数异常");
            Question question = questionService.getById(questionId);
            ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }


        //无需校验
        // todo 从对象中取值
        // 创建数据时，参数不能为空
//        if (add) {
//            // todo 补充校验规则
//            ThrowUtils.throwIf(StringUtils.isBlank(title), ErrorCode.PARAMS_ERROR);
//        }
//        // 修改数据时，有参数则校验
//        // todo 补充校验规则
//        if (StringUtils.isNotBlank(title)) {
//            ThrowUtils.throwIf(title.length() > 80, ErrorCode.PARAMS_ERROR, "标题过长");
//        }
    }

    /**
     * 获取查询条件
     *
     * @param questionBankQuestionQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest questionBankQuestionQueryRequest) {
        QueryWrapper<QuestionBankQuestion> queryWrapper = new QueryWrapper<>();
        if (questionBankQuestionQueryRequest == null) {
            return queryWrapper;
        }
        // todo 从对象中取值
        Long id = questionBankQuestionQueryRequest.getId();
        Long notId = questionBankQuestionQueryRequest.getNotId();
        String sortField = questionBankQuestionQueryRequest.getSortField();
        String sortOrder = questionBankQuestionQueryRequest.getSortOrder();
        Long userId = questionBankQuestionQueryRequest.getUserId();

        Long questionBankId = questionBankQuestionQueryRequest.getQuestionBankId();
        Long questionId = questionBankQuestionQueryRequest.getQuestionId();
        // todo 补充需要的查询条件
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionBankId), "questionBankId", questionBankId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionIdv", questionId);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取题目题库关联封装
     *
     * @param questionBankQuestion
     * @param request
     * @return
     */
    @Override
    public QuestionBankQuestionVO getQuestionBankQuestionVO(QuestionBankQuestion questionBankQuestion, HttpServletRequest request) {
        // 对象转封装类
        QuestionBankQuestionVO questionBankQuestionVO = QuestionBankQuestionVO.objToVo(questionBankQuestion);

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = questionBankQuestion.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        questionBankQuestionVO.setUser(userVO);
        // 2. 已登录，获取用户点赞、收藏状态
//        long questionBankQuestionId = questionBankQuestion.getId();
//        User loginUser = userService.getLoginUserPermitNull(request);
//        if (loginUser != null) {
//            // 获取点赞
//            QueryWrapper<QuestionBankQuestionThumb> questionBankQuestionThumbQueryWrapper = new QueryWrapper<>();
//            questionBankQuestionThumbQueryWrapper.in("questionBankQuestionId", questionBankQuestionId);
//            questionBankQuestionThumbQueryWrapper.eq("userId", loginUser.getId());
//            QuestionBankQuestionThumb questionBankQuestionThumb = questionBankQuestionThumbMapper.selectOne(questionBankQuestionThumbQueryWrapper);
//            questionBankQuestionVO.setHasThumb(questionBankQuestionThumb != null);
//            // 获取收藏
//            QueryWrapper<QuestionBankQuestionFavour> questionBankQuestionFavourQueryWrapper = new QueryWrapper<>();
//            questionBankQuestionFavourQueryWrapper.in("questionBankQuestionId", questionBankQuestionId);
//            questionBankQuestionFavourQueryWrapper.eq("userId", loginUser.getId());
//            QuestionBankQuestionFavour questionBankQuestionFavour = questionBankQuestionFavourMapper.selectOne(questionBankQuestionFavourQueryWrapper);
//            questionBankQuestionVO.setHasFavour(questionBankQuestionFavour != null);
//        }
        // endregion

        return questionBankQuestionVO;
    }

    /**
     * 分页获取题目题库关联封装
     *
     * @param questionBankQuestionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionBankQuestionVO> getQuestionBankQuestionVOPage(Page<QuestionBankQuestion> questionBankQuestionPage, HttpServletRequest request) {
        List<QuestionBankQuestion> questionBankQuestionList = questionBankQuestionPage.getRecords();
        Page<QuestionBankQuestionVO> questionBankQuestionVOPage = new Page<>(questionBankQuestionPage.getCurrent(), questionBankQuestionPage.getSize(), questionBankQuestionPage.getTotal());
        if (CollUtil.isEmpty(questionBankQuestionList)) {
            return questionBankQuestionVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionBankQuestionVO> questionBankQuestionVOList = questionBankQuestionList.stream().map(questionBankQuestion -> {
            return QuestionBankQuestionVO.objToVo(questionBankQuestion);
        }).collect(Collectors.toList());

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionBankQuestionList.stream().map(QuestionBankQuestion::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));

        Set<Long> questionIdSet = questionBankQuestionList.stream().map(QuestionBankQuestion::getQuestionId).collect(Collectors.toSet());
        Map<Long, List<Question>> questionIdQuestionMap = questionService.listByIds(questionIdSet).stream().collect(Collectors.groupingBy(Question::getId));
        // 2. 已登录，获取用户点赞、收藏状态
//        Map<Long, Boolean> questionBankQuestionIdHasThumbMap = new HashMap<>();
//        Map<Long, Boolean> questionBankQuestionIdHasFavourMap = new HashMap<>();
//        User loginUser = userService.getLoginUserPermitNull(request);
//        if (loginUser != null) {
//            Set<Long> questionBankQuestionIdSet = questionBankQuestionList.stream().map(QuestionBankQuestion::getId).collect(Collectors.toSet());
//            loginUser = userService.getLoginUser(request);
//            // 获取点赞
//            QueryWrapper<QuestionBankQuestionThumb> questionBankQuestionThumbQueryWrapper = new QueryWrapper<>();
//            questionBankQuestionThumbQueryWrapper.in("questionBankQuestionId", questionBankQuestionIdSet);
//            questionBankQuestionThumbQueryWrapper.eq("userId", loginUser.getId());
//            List<QuestionBankQuestionThumb> questionBankQuestionQuestionBankQuestionThumbList = questionBankQuestionThumbMapper.selectList(questionBankQuestionThumbQueryWrapper);
//            questionBankQuestionQuestionBankQuestionThumbList.forEach(questionBankQuestionQuestionBankQuestionThumb -> questionBankQuestionIdHasThumbMap.put(questionBankQuestionQuestionBankQuestionThumb.getQuestionBankQuestionId(), true));
//            // 获取收藏
//            QueryWrapper<QuestionBankQuestionFavour> questionBankQuestionFavourQueryWrapper = new QueryWrapper<>();
//            questionBankQuestionFavourQueryWrapper.in("questionBankQuestionId", questionBankQuestionIdSet);
//            questionBankQuestionFavourQueryWrapper.eq("userId", loginUser.getId());
//            List<QuestionBankQuestionFavour> questionBankQuestionFavourList = questionBankQuestionFavourMapper.selectList(questionBankQuestionFavourQueryWrapper);
//            questionBankQuestionFavourList.forEach(questionBankQuestionFavour -> questionBankQuestionIdHasFavourMap.put(questionBankQuestionFavour.getQuestionBankQuestionId(), true));
//        }
        // 填充信息
        questionBankQuestionVOList.forEach(questionBankQuestionVO -> {
            Long userId = questionBankQuestionVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionBankQuestionVO.setUser(userService.getUserVO(user));
            Long questionId = questionBankQuestionVO.getQuestionId();
            Question question = null;
            if (questionIdQuestionMap.containsKey(questionId)) {
                question = questionIdQuestionMap.get(questionId).get(0);
            }
            questionBankQuestionVO.setQuestion(QuestionVO.objToVo(question));
//            questionBankQuestionVO.setHasThumb(questionBankQuestionIdHasThumbMap.getOrDefault(questionBankQuestionVO.getId(), false));
//            questionBankQuestionVO.setHasFavour(questionBankQuestionIdHasFavourMap.getOrDefault(questionBankQuestionVO.getId(), false));
        });
        // endregion

        questionBankQuestionVOPage.setRecords(questionBankQuestionVOList);
        return questionBankQuestionVOPage;
    }

    @Override
    public Page<QuestionListVO> GetQuestionVOListByBankIdRequest(GetQuestionVOListByBankIdRequest questionQueryList,HttpServletRequest request) {
        Long bankId = questionQueryList.getBankId();
        String title = questionQueryList.getTitle();
        Integer needVIP = questionQueryList.getNeedVIP();
        List<String> tagList = questionQueryList.getTags();
        int current = questionQueryList.getCurrent();
        int pageSize = questionQueryList.getPageSize();
        String sortField = questionQueryList.getSortField();
        String sortOrder = questionQueryList.getSortOrder();
        List<QuestionBankQuestion> questionIdList = this.list(Wrappers.lambdaQuery(QuestionBankQuestion.class).
                eq(QuestionBankQuestion::getQuestionBankId, bankId)
                .select(QuestionBankQuestion::getQuestionId));
        List<Long> questionIds = new ArrayList<>();
        questionIdList.forEach(questionBankQuestion -> {
            if (questionBankQuestion.getQuestionId() != null) {
                questionIds.add(questionBankQuestion.getQuestionId());
            }
        });
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = Wrappers.lambdaQuery(Question.class).eq(needVIP != null, Question::getNeedVip, needVIP)
                .like(StrUtil.isNotBlank(title), Question::getTitle, title)
                .in(Question::getId,questionIds);
        // JSON 数组查询
        if (CollUtil.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                questionLambdaQueryWrapper.like(Question::getTags, "\"" + tag + "\"");
            }
        }

        Page<Question> questionPage = questionService.page(new Page<>(current, pageSize), questionLambdaQueryWrapper);
        List<Question> questionListRecord = questionPage.getRecords();
        Page<QuestionListVO> questionListVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollUtil.isEmpty(questionListRecord)) {
            return questionListVOPage;
        }
        // 对象列表 => 封装对象列表
        List<QuestionListVO> questionVOList = questionListRecord.stream().
                map(question -> BeanUtil.copyProperties(question, QuestionListVO.class)).collect(Collectors.toList());
        questionListVOPage.setRecords(questionVOList);
        return questionListVOPage;
    }

}
