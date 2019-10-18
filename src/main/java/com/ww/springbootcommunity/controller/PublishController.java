package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.cache.TagCache;
import com.ww.springbootcommunity.dto.QuestionDTO;
import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;



@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("publish")
    public String dopublish(Question question, HttpServletRequest request , Model model) {
        //用来回显
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());

        model.addAttribute("tags", TagCache.get());

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }
        if(question.getTitle() == null || question.getTitle() == ""){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(question.getDescription() == null || question.getDescription() == ""){
            model.addAttribute("error","内容不能为空！");
            return "publish";
        }
        if(question.getTag() == null || question.getTag() == ""){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }

        String invalid = TagCache.filterInValid(question.getTag());
        if(StringUtils.isNoneBlank(invalid)){
            model.addAttribute("error","存在非法标签:"+invalid);
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

        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {

        QuestionDTO question = questionService.getById(id);
        //用来回显
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

}
