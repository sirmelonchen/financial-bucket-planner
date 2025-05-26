package de.sirmelonchen.interceptor;

import de.sirmelonchen.status.SetupStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SetupInterceptor implements HandlerInterceptor {

    private final SetupStatus setupStatus;

    public SetupInterceptor(SetupStatus setupStatus) {
        this.setupStatus = setupStatus;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().startsWith("/setup") && setupStatus.isSetupCompleted()) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Setup already completed");
            return false;
        }
        return true;
    }
}
