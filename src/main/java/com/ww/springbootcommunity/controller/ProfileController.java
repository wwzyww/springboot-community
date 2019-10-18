package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.dto.PageDTO;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.service.NotificationService;
import com.ww.springbootcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size) {

        User user = (User)request.getSession().getAttribute("user");

        if(user == null) {
            return "redirect:/";
        }
        if("questions".equals(action)) {

            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");

            PageDTO pageDTO = questionService.queryUserByIdlist(user.getId(), page, size);
            model.addAttribute("pageDTO",pageDTO);

        }else if ("replies".equals(action)) {

            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

            PageDTO pageDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("pageDTO",pageDTO);

            System.out.println(user.getId());
            Integer unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("unreadCount",unreadCount);

        }

        return "profile";
    }


}
