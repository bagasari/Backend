package com.bagasari.sacbagaji.exception;

import com.bagasari.sacbagaji.exception.security.EmptyJwtTokenException;
import com.bagasari.sacbagaji.exception.security.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException | EmptyJwtTokenException e) {
            setErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    request, response, e.getMessage()
            );
        }
    }

    private void setErrorResponse(
            HttpStatus status,
            HttpServletRequest request,
            HttpServletResponse response,
            String exceptionMessage
            ) {
        objectMapper.registerModule(new JavaTimeModule());
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.toString(),
                exceptionMessage, request.getRequestURI()
        );
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
