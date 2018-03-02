package com.study.cancer.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 权限拦截器
 * @author Leo
 *
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// nothing
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// nothing
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		HttpSession session = request.getSession();
		String name = ((HandlerMethod) arg2).getMethod().getName();
		if (request.getRequestURI().endsWith("/checkValidateCode") || request.getRequestURI().endsWith("/sendSMS") || request.getRequestURI().endsWith("/checkPhone") || request.getRequestURI().endsWith("/assign") || request.getRequestURI().endsWith("/register") || request.getRequestURI().endsWith("/login") || request.getRequestURI().endsWith("/loginPage") || request.getRequestURI().endsWith("/registerPage")) {
			return true;
		}

		if (session.getAttribute("loginUser") == null) {

			response.sendRedirect(request.getContextPath() + "/loginPage");
			return false;
		}
		return true;
	}

}
