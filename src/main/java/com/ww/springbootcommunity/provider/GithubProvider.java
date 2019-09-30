package com.ww.springbootcommunity.provider;

import com.alibaba.fastjson.JSON;
import com.ww.springbootcommunity.dto.AccessTokenDTO;
import com.ww.springbootcommunity.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 实现用Github登录 需要的信息字段
 * 运用的okHttp
 */
@Component
public class GithubProvider {

    /**
     * @param accessToken
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessToken) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            String token = s.split("&")[0].split("=")[1];
            //System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token="+ accessToken)
            .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            //自动把s的JSON对象转换解析成GithubUser类对象
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
