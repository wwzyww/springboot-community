package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.dto.CommentDTO;
import com.ww.springbootcommunity.dto.ResultDTO;
import com.ww.springbootcommunity.entity.Comment;
import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.service.CommentService;
import com.ww.springbootcommunity.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @RequestMapping("/insertComment")
    public ResultDTO insertComment(@RequestBody Comment comment , HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute("user");

        Question question = questionService.queryQuestionById(comment.getParentId());

        if(comment == null || StringUtils.isBlank(comment.getContent())){
            return new ResultDTO(400,"输入内容不能为空！！！");
        }else if(user == null){
            return new ResultDTO(300,"用户未登录，请先登录！！！");
        }else if(question == null){
            return new ResultDTO(200,"此问题不存在！！！");
        }else {
            comment.setCreateTime(System.currentTimeMillis());
            comment.setUpdateTime(comment.getCreateTime());
            comment.setCommentator(user.getId());

            commentService.insertComment(comment,user);

            return new ResultDTO(100,"回复成功！！！");
        }
    }

    /**
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryById/{id}")
    public List queryById(@PathVariable(name="id") Integer id, HttpSession session) {
        Comment comment = new Comment();
        List<CommentDTO> comments = commentService.queryCommentList(comment);
        List list = new ArrayList();
        for(int i = comments.size()-1 ; i >= 0 ; i--) {
           if (comments.get(i).getParentId() == id) {
               list.add(comments.get(i));
           }
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/insertComments")
    public ResultDTO insertComments(@RequestBody Comment comment , HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return new ResultDTO(300,"用户未登录，请先登录！！！");
        }
        comment.setCreateTime(System.currentTimeMillis());
        comment.setUpdateTime(comment.getCreateTime());
        comment.setCommentator(user.getId());
        commentService.insertComment(comment,user);
        return new ResultDTO(100,"回复成功！！！");
    }


}


