package org.abalong.controller.auth;

import org.abalong.controller.cdi.ProfileBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This web filter denies any attempts of unauthorized entering to pages indicated in the {@code WebFilter} url patters.
 */
@WebFilter(urlPatterns = {"/changePassword.xhtml", "/editProfile.xhtml", "/fields.xhtml", "/newForm.xhtml", "/responses.xhtml"})
public class LoginFilter implements Filter {

    /**
     * Injection of {@code ProfileBean} instance.<br>
     * It has not be null and contain correct email and password.
     */
    @Inject
    private ProfileBean profileBean;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Main method of the class. Checks email and password of the injected {@code ProfileBean} instance.
     * If {@code ProfileBean} instance has {@code true} value of {@code isLogged} field, attempt is accepted.
     * Otherwise, attempt is denied and profile will be redirected to the login page.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (profileBean.isLogged()) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String path = httpServletRequest.getContextPath();
        httpServletResponse.sendRedirect(path + "/login.xhtml");
    }

    @Override
    public void destroy() {

    }
}
