package com.bagasari.sacbagaji.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    //대상이 되는 파라미터를 정의
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class)&& parameter.getParameterType() == AuthInfo.class;
    }

    //Auth 어노테이션을 사용하면 jwt토큰의 email값이 AuthInfo의 email로 바인딩되도록 해야 한다.
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        //SecurityContextHolder에서 인증객체의 username(email) 반환되게끔
        UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new AuthInfo(authentication.getUsername());
    }
}
