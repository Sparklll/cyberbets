package by.epam.jwd.cyberbets.filter;

import by.epam.jwd.cyberbets.domain.Account;
import by.epam.jwd.cyberbets.domain.Role;
import by.epam.jwd.cyberbets.service.AccountService;
import by.epam.jwd.cyberbets.service.impl.ServiceProvider;
import by.epam.jwd.cyberbets.service.exception.ServiceException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public class AuthorizationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    private static final AccountService accountService = ServiceProvider.INSTANCE.getAccountService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession httpSession = request.getSession(false);
        request.setAttribute(ROLE_ATTR, GUEST_ROLE);

        if(httpSession != null) {
            String accountEmail = (String) httpSession.getAttribute(ACCOUNT_EMAIL_ATTR);
            if(accountEmail != null) {
                try {
                    Optional<Account> optionalAccount = accountService.findAccountByEmail(accountEmail);
                    if(optionalAccount.isPresent()) {
                        Account foundAccount = optionalAccount.get();
                        BigDecimal balance = foundAccount.getBalance();
                        Role role = foundAccount.getRole();
                        request.setAttribute(AUTH_ATTR, true);
                        request.setAttribute(ACCOUNT_ID_ATTR, foundAccount.getId());
                        request.setAttribute(ROLE_ATTR, role.getName());
                        request.setAttribute(BALANCE_ATTR, balance);
                    }
                } catch (ServiceException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
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
