package com.ww.springbootcommunity.service;

import com.ww.springbootcommunity.dto.PageDTO;
import com.ww.springbootcommunity.dto.QuestionDTO;
import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.mapper.QuestionMapper;
import com.ww.springbootcommunity.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageDTO querylist(Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();

        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if (totalCount % size ==0){
            totalPage = totalCount /size;
        }else {
            totalPage = totalCount /size + 1;
        }
        if(page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageDTO.setPageDTO(totalPage,page);

        Integer offset = size * (page - 1);

        List<Question> querylist = questionMapper.querylist(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : querylist) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties快速的把querylist里面的复制到questionDTO里面
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;
    }

    public PageDTO queryUserByIdlist(Integer userId, Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();

        Integer totalPage;
        Integer totalCount = questionMapper.countUserById(userId);
        if (totalCount % size ==0){
            totalPage = totalCount /size;
        }else {
            totalPage = totalCount /size + 1;
        }
        if(page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageDTO.setPageDTO(totalPage,page);


        Integer offset = size * (page - 1);

        List<Question> querylist = questionMapper.queryUserByIdlist(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : querylist) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties快速的把querylist里面的复制到questionDTO里面
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        return pageDTO;

    }

    public QuestionDTO getById(Integer id) {

        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void insert(Question question){
        questionMapper.insert(question);
    };

    public void createOrUpdate(Question question) {
        if(question.getId() == null) {
            //创建
            question.setCreateTime(System.currentTimeMillis());
            question.setUpdateTime(question.getCreateTime());
            questionMapper.insert(question);
        }else {
            //更新
            question.setCreateTime(System.currentTimeMillis());
            question.setUpdateTime(question.getCreateTime());
            questionMapper.update(question);
        }
    }



    //------------------- 引入mybatis ----- xml文件后 写的sql ， 但是太懒了，前面的不想改，就这样  -----------------


    /**
     * 查询所有
     * @param question
     * @return
     */
    public List queryQuestionList(Question question) {
        return questionMapper.queryQuestionList(question);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Question queryQuestionById(Integer id) {
        return  questionMapper.queryQuestionById(id);
    }

    /**
     * 新增
     * @param question
     */
    public void insertQuestion(Question question) {
        questionMapper.insertQuestion(question);
    }
    /**
     * 修改
     * @param question
     */
    public void updateQuestion(Question question) {
        questionMapper.updateQuestion(question);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void deleteQuestionById(Integer id) {
        questionMapper.deleteQuestionById(id);
    }

    //累加阅读数
    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionMapper.incView(question);
    }

    public List<QuestionDTO> queryQuestionTag(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag());
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questionList = questionMapper.queryQuestionTag(question);
        //把questionList转成QuestionDTO  //遍历每一个然后放进去
        List<QuestionDTO> questionDTOList = questionList.stream().map(q -> {
            QuestionDTO questionDTOs = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTOs);
            return questionDTOs;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
}
