package com.ww.springbootcommunity.service;

import com.ww.springbootcommunity.dto.PageDTO;
import com.ww.springbootcommunity.dto.QuestionDTO;
import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.mapper.QuestionMapper;
import com.ww.springbootcommunity.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            question.setUpdateTime(question.getCreateTime());
            questionMapper.update(question);
        }
    }
}
