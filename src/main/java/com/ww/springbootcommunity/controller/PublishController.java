package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("publish")
    public String dopublish(Question question, HttpServletRequest request , Model model) {
        //用来回显
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }
        if(question.getCommentCount() == null){
            question.setCommentCount(0);
        }
        if(question.getViewCount() == null){
            question.setViewCount(0);
        }
        if(question.getLikeCount() == null){
            question.setLikeCount(0);
        }
        question.setCreator(user.getId());
        question.setCreateTime(System.currentTimeMillis());
        question.setUpdateTime(question.getCreateTime());
        questionMapper.insert(question);
        return "redirect:/";
    }

}
