package com.ww.springbootcommunity.controller;


import com.ww.springbootcommunity.dao.AccessTokenDao;
import com.ww.springbootcommunity.dao.GithubUser;
import com.ww.springbootcommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code , @RequestParam(name = "state") String state) {

        AccessTokenDao accessToken = new AccessTokenDao();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setRedirect_url(redirectUrl);
        accessToken.setState(state);

        String aTk = githubProvider.getAccessToken(accessToken);
        GithubUser user = githubProvider.getUser(aTk);
        System.out.println(user.getName());
        return "index";
    }
}
