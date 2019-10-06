package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.dto.AccessTokenDTO;
import com.ww.springbootcommunity.dto.GithubUser;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.provider.GithubProvider;
import com.ww.springbootcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *  Github登录调用，获取信息
 */
@Controller
public class AuthorizeControlle {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String clientId;

    @Value("${github.client_secret}")
    private String clientSecret;

    @Value("${github.redirect_url}")
    private String redirectUrl;

    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code ,
                           @RequestParam(name = "state") String state ,
                           // 通过response写入一个cookie
                           HttpServletResponse response
                           ) {
        AccessTokenDTO accessToken = new AccessTokenDTO();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setRedirect_url(redirectUrl);
        accessToken.setState(state);

        String aTk = githubProvider.getAccessToken(accessToken);
        //获取到用github登录的用户信息
        GithubUser githubUser = githubProvider.getUser(aTk);
        //System.out.println(user.getName());

        if(githubUser != null && githubUser.getId() != null) {
            //登录成功，写 cookie 和 session
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
           /* user.setCreateTime(System.currentTimeMillis());//System.currentTimeMillis产生一个当前的毫秒时间
            user.setUpdateTime(user.getCreateTime());*/
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));//自己写一个cookie用来保存用户登录，再登录首页是判断cookie是否存在

            return "redirect:/";//redirect是重定向，redirect跳转的是路径，不是页面
        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response,HttpServletRequest request) {

        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
