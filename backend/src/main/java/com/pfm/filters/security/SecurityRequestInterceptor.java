package com.pfm.filters.security;

import com.google.common.net.HttpHeaders;
import com.pfm.auth.TokenService;
import com.pfm.auth.UserProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@AllArgsConstructor
@Component
@CrossOrigin
public class SecurityRequestInterceptor extends HandlerInterceptorAdapter {

  //TODO this should be done be spring security
  private static final String pattern = "(/users/.*|.*swagger.*|/error|/)";
  private final Logger logger = LoggerFactory.getLogger(SecurityRequestInterceptor.class.getName());

  private TokenService tokenService;
  private UserProvider userProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

    if ("OPTIONS".equals(request.getMethod())) {
      return true;
    }

    if (request.getRequestURI().matches(pattern)) {
      return true;
    }

    String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (accessToken == null || accessToken.isEmpty()) {
      logger.error("Authorization header value is empty");
      response.setContentType("text/plain");
      response.getWriter().write("Authorization header value is empty");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setHeader("Access-Control-Allow-Origin", "*");
      return false;
    }

    if (isTokenCorrect(accessToken)) {
      long userIdFromToken = tokenService.getUserIdBasedOnAccessToken(accessToken);
      userProvider.setUser(userIdFromToken);
      return true;
    } else {
      logger.error("Request accessToken \"" + accessToken + "\" is incorrect");
      response.setContentType("text/plain");
      response.getWriter().write("Request accessToken \"" + accessToken + "\" is incorrect");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setHeader("Access-Control-Allow-Origin", "*");
      return false;
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    super.postHandle(request, response, handler, modelAndView);
    userProvider.removeUser();
  }

  private boolean isTokenCorrect(String token) {
    return tokenService.validateAccessToken(token);
  }
}
