package com.wallanaq.rest.api.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import net.minidev.json.JSONObject;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(401);
		Map<String, Object> map = new HashMap<>();
		map.put("timestamp", new Date());
		map.put("status", 401);
		map.put("message", authException.getMessage());
		response.getWriter().write(new JSONObject(map).toJSONString());
    }
    
}