package com.example.jwtdemo.jwt;

import com.example.jwtdemo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Value("${jwt.header}")
  private String tokenHeader;
  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader(this.tokenHeader);
    if (authHeader != null && authHeader.startsWith(tokenHead)) {
      String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
      String username = jwtTokenUtil.getUsernameFromToken(authToken);

      logger.info("checking authentication " + username);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // 下面这一步可以做一下缓存，因为每次请求都会走去数据库里查一下，做缓存对系统性能会有所提升
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          logger.info("authenticated user " + username + ", setting security context");
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }
}