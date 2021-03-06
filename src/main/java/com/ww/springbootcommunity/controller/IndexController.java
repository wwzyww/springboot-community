package com.ww.springbootcommunity.controller;

import com.ww.springbootcommunity.dto.PageDTO;
import com.ww.springbootcommunity.mapper.UserMapper;
import com.ww.springbootcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 首页
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request , Model model ,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size) {


        PageDTO pageDTO = questionService.querylist(page, size);
        model.addAttribute("pageDTO",pageDTO);
        return "index";
    }

}
