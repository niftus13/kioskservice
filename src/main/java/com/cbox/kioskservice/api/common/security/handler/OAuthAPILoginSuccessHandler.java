package com.cbox.kioskservice.api.common.security.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cbox.kioskservice.api.admin.dto.MemberDTO;
import com.cbox.kioskservice.api.common.util.JWTUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Log4j2
public class OAuthAPILoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        log.info("OAuth onAuthenticationSuccess.................");


        String id = authentication.getName();

        log.info("SEND TO APPLICATION: " + id);

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateToken(claims, 10);

        String refreshToken = JWTUtil.generateToken(claims, 60 * 24);

        Gson gson = new Gson();

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.putAll(claims);

        dataMap.put("accessToken", accessToken);
        dataMap.put("refreshToken", refreshToken);

        String jsonStr = gson.toJson(dataMap);

        String encodeStr = URLEncoder.encode(jsonStr, "UTF-8");

        response.sendRedirect("http://localhost:3000/member/oauthResult?data="+encodeStr);

    }

}


