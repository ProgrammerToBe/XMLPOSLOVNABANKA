package korenski.intercepting;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import korenski.model.autorizacija.Permission;
import korenski.model.autorizacija.Role;
import korenski.model.autorizacija.User;
import korenski.repository.autorizacija.RoleRepository;
import korenski.repository.autorizacija.UserRepository;
import korenski.service.autorizacija.UserServiceClass;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor  {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public boolean preHandle(@Context HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("========================Regular interceptor=================================");
		String value = request.getHeader("X-XSRF-TOKEN");
		
		System.out.println("X-XSRF-TOKEN VREDNOST JE "+ value);
		
		String tokenValue = request.getHeader("X-XSRF-TOKEN");
		
		String sessionTokenValue = (String) request.getSession().getAttribute("tokenValue");
		
		System.out.println("Vrednost tokena u sesiji je "+sessionTokenValue);
		
		if(tokenValue == null){
			return false;
		}
		
		System.out.println("Ima token");
		
		
		if(!tokenValue.equals(sessionTokenValue)){
			return false;
		}
		
		System.out.println("Poklapa se token");
		
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user == null){
			System.out.println("Nema ulogovanog korisnika");
			return false;
		}
		
		
		if(userRepository != null){

			User userFromDB = userRepository.findOne(user.getId());
			
			System.out.println("Ima ulogovanog korisnika");
			
			if(userFromDB == null){
				System.out.println("Ulogovani korisnik ne postoji u bazi");
				return false;
			}
			
			System.out.println("Ulogovani korisnik postoji u bazi");
			
			
		}
		
		
		Role role = user.getRole();
		
		
		Collection<Permission> permissions = role.getPermissions();
		
		HandlerMethod hm=(HandlerMethod)handler; 
		Method method=hm.getMethod();
		String annotationValue = null;
		if(method.getDeclaringClass().isAnnotationPresent(Controller.class)){
			if(method.isAnnotationPresent(CustomAnnotation.class)){
				CustomAnnotation ano = method.getAnnotation(CustomAnnotation.class);
				annotationValue = ano.value();
				System.out.println("IMA I ANOTACIJU. Njena vrednost je "+annotationValue);
			}else{
				return false;
			}
		}
		
		if(permissions == null){
			System.out.println("Permisije su null");
			return false;
		}
		
		for(Permission permission : permissions){
			
			if(permission.getName().equals(annotationValue)){
				System.out.println("Ima permisiju");
				System.out.println("Vrednost permisije "+permission.getName());
				return true;
			}
		}
		
		System.out.println("NEMA PERMISIJE");
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
