package com.wallanaq.rest.api.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import net.minidev.json.JSONObject;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(403);
		Map<String, Object> map = new HashMap<>();
		map.put("timestamp", new Date());
		map.put("status", 403);
		map.put("message", accessDeniedException.getMessage());
		response.getWriter().write(new JSONObject(map).toJSONString());
	}

}