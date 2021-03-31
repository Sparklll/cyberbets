package by.epam.jwd.cyberbets.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UrlFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UrlFilter.class);

    private static final String SLASH = "/";
    private static final String RESOURCES_URI = "/resources";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        if(StringUtils.startsWith(requestURI, RESOURCES_URI) || StringUtils.endsWith(requestURI, SLASH)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(requestURI + SLASH);
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
