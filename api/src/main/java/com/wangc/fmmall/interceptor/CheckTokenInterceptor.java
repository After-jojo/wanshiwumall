package com.wangc.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangc.fmmall.vo.ResStatus;
import com.wangc.fmmall.vo.ResultVO;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getParameter("token");
        // 要放行预检请求
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token"); // 通过header传递token 此时注意浏览器的预检机制
        if (token == null) {
            ResultVO r = new ResultVO(ResStatus.LOGIN_NOT, "请先登录", null);
            doResponse(response, r);
            return false;
        }
        try {
            //验证token
            JwtParser parser = Jwts.parser();
            parser.setSigningKey("jojo123");
            //若正确正常执行， 否则抛出异常
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            ResultVO r = new ResultVO(ResStatus.LOGIN_OVERDUE, "您的登录已经过期", null);
            doResponse(response, r);
        }catch (UnsupportedJwtException e){
            ResultVO r = new ResultVO(ResStatus.NO, "错误", null);
            doResponse(response, r);
        }catch (Exception e){
            ResultVO r = new ResultVO(ResStatus.NO, "未知异常", null);
            doResponse(response, r);
        }
        return false;
    }
    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String str = new ObjectMapper().writeValueAsString(resultVO);
        writer.print(str);
        writer.flush();
        writer.close();
    }
}
