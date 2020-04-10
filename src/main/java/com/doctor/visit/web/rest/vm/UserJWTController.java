package com.doctor.visit.web.rest.vm;

import com.doctor.visit.config.Constants;
import com.doctor.visit.security.AuthoritiesConstants;
import com.doctor.visit.security.jwt.TokenProvider;
import com.doctor.visit.web.rest.util.ComResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@RequestMapping(Constants.API_BASE)
public class UserJWTController {

    private final TokenProvider tokenProvider;

    public UserJWTController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }


    /**
     * 登录接口
     *
     * @param account
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/authenticate")
    public ComResponse<JWTToken> authorize(HttpServletRequest request, String account, String password, boolean rememberMe) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            account,
            password,
            Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        return ComResponse.OK(new JWTToken(jwt));
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

}
