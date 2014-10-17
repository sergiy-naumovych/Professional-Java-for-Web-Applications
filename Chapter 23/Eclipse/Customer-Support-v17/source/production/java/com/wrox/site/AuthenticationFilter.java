package com.wrox.site;

import com.wrox.site.entities.UserPrincipal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

public class AuthenticationFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException
    {
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        final Principal principal = UserPrincipal.getPrincipal(session);
        if(principal == null)
        {
            ((HttpServletResponse)response).sendRedirect(
                    ((HttpServletRequest) request).getContextPath() + "/login"
            );
        }
        else
        {
            chain.doFilter(
                    new HttpServletRequestWrapper((HttpServletRequest)request)
                    {
                        @Override
                        public Principal getUserPrincipal() {
                            return principal;
                        }
                    },
                    response
            );
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException { }

    @Override
    public void destroy() { }
}
