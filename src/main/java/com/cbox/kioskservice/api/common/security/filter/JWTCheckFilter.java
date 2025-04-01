package com.cbox.kioskservice.api.common.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cbox.kioskservice.api.admin.dto.MemberDTO;
import com.cbox.kioskservice.api.common.util.JWTUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {


//Preflight
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }


// /api/member/login
        String path = request.getRequestURI();

        log.info("check filter for path: {}", path);


        return path.startsWith("/api/member/") ||
               path.startsWith("/login") ||
               path.startsWith("/oauth2") ||
               path.endsWith(".ico")||
               path.startsWith("/api/product/list");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("----------------------doFilterInternal -----------------------");
        log.info("-----Executing JWTCheckFilter------");

        String authHeaderStr = request.getHeader("Authorization");
        // json 토큰 검증
        if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header");
            sendErrorResponse(response, "MISSING_OR_INVALID_AUTH_HEADER");
            return;
        }

        try {
            // Bearer 토큰 제거
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: {}", claims);

            String id = (String) claims.get("id");
            String pw = (String) claims.get("pw");
            Boolean mdelFlag = (Boolean) claims.get("mdelFlag");
            Boolean social = (Boolean) claims.get("social");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO(id, pw, social, mdelFlag, roleNames);
            log.info("Authenticated User: {}", memberDTO);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT Validation Error: {}", e.getMessage(), e);
            sendErrorResponse(response, "INVALID_ACCESS_TOKEN");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.println(new Gson().toJson(Map.of("error", errorMessage)));
        writer.close();
    }
        // try {
        //     //Bearer accestoken...
        //     String accessToken = authHeaderStr.substring(7);
        //     Map<String, Object> claims = JWTUtil.validateToken(accessToken);

        //     log.info("JWT claims: " + claims);

        //     String id = (String) claims.get("id");
        //     String pw = (String) claims.get("pw");
        //     Boolean mdelFlag = (Boolean) claims.get("mdelFlag");
        //     Boolean social = (Boolean) claims.get("social");
        //     List<String> roleNames = (List<String>) claims.get("roleNames");

        //     MemberDTO memberDTO = new MemberDTO(id, pw, social.booleanValue(), mdelFlag.booleanValue(), roleNames);

        //     log.info("-----------------------------------");
        //     log.info(memberDTO);
        //     log.info(memberDTO.getAuthorities());
        //     // 중요 : -
        //     UsernamePasswordAuthenticationToken authenticationToken
        //             = new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());
        //     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //     // -
        //     filterChain.doFilter(request, response);

        // } catch (Exception e) {

        //     log.error("JWT Check Error..............");
        //     log.error(e.getMessage());

        //     Gson gson = new Gson();
        //     String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

        //     response.setContentType("application/json");
        //     PrintWriter printWriter = response.getWriter();
        //     printWriter.println(msg);
        //     printWriter.close();
        // }
    }

