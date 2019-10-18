package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.dto.NotificationDTO;
import com.ww.springbootcommunity.dto.PageDTO;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.enums.NotificationTypeEnum;
import com.ww.springbootcommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          Model model,
                          HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute("user");

        if(user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);
        System.out.println(notificationDTO);

        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
        || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }

    }

}
