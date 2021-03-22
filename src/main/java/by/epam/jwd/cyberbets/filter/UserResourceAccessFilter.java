package by.epam.jwd.cyberbets.filter;

import by.epam.jwd.cyberbets.domain.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static by.epam.jwd.cyberbets.controller.Parameters.ERROR_PAGE;
import static by.epam.jwd.cyberbets.controller.Parameters.ROLE_ATTR;

public class UserResourceAccessFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserResourceAccessFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Role requestRole = Role.getRoleByName((String) request.getAttribute(ROLE_ATTR));

        if(requestRole.getId() < Role.USER.getId()) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(ERROR_PAGE);
            requestDispatcher.forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
