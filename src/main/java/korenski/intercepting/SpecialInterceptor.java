package korenski.intercepting;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import korenski.repository.autorizacija.RoleRepository;

public class SpecialInterceptor implements HandlerInterceptor  {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public boolean preHandle(@Context HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("Special interceptor, prehandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		Cookie myCookie =new Cookie("XSRF-TOKEN", "val22");
//		response.addCookie(myCookie);
//		
//		System.out.println("XSRF-TOKEN vrednost je "+ "val22");
	}

}
