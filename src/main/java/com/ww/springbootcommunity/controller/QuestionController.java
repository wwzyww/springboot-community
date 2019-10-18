package com.ww.springbootcommunity.controller;

import com.ww.springbootcommunity.dto.CommentDTO;
import com.ww.springbootcommunity.dto.QuestionDTO;
import com.ww.springbootcommunity.entity.Comment;
import com.ww.springbootcommunity.entity.Question;
import com.ww.springbootcommunity.service.CommentService;
import com.ww.springbootcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id , Model model) {

        QuestionDTO questionDTO = questionService.getById(id);

        List<QuestionDTO> relatedQuestions = questionService.queryQuestionTag(questionDTO);
        List<CommentDTO> commentDTO = commentService.listQuestionById(id);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("relatedQuestions",relatedQuestions);
        model.addAttribute("commentDTO",commentDTO);
        return "question";
    }





    /*-------------------------*/


    /**
     * 查询所有
     * @param question
     * @return
     */

    @ResponseBody
    @RequestMapping("/queryQuestionList")
    public List queryQuestionList(Question question) {
        return questionService.queryQuestionList(question);
    }

    @ResponseBody
    @RequestMapping("/queryQuestionById")
    public Question queryQuestionById(Integer id)  {
        return questionService.queryQuestionById(id);
    }
}
