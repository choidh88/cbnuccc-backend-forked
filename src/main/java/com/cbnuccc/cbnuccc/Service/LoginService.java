package com.cbnuccc.cbnuccc.Service;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.cbnuccc.cbnuccc.Dto.TokenDto;
import com.cbnuccc.cbnuccc.Model.MyUser;
import com.cbnuccc.cbnuccc.Repository.UserJpaRepository;
import com.cbnuccc.cbnuccc.Util.LogHeader;
import com.cbnuccc.cbnuccc.Util.LogUtil;
import com.cbnuccc.cbnuccc.Util.SecurityUtil;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserJpaRepository userJpaRepository;
    private final SecurityUtil securityUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // create a jwt token.
    private String createToken(Authentication auth, String email, boolean rememberMe) {
        MyUser user = userJpaRepository.findByEmail(email).get();

        // 1000 ms/s * 60 s/min * 60 min/h * 24 h/d * 7 d = 604800000 ms/d (7 days)
        // 1000 ms/s * 60 s/min * 60 min/h * 24 h/d * 1 d = 86400000 ms/d (1 day)
        int expirationMillis = rememberMe ? 604800000 : 86400000;
        String jwt = Jwts.builder()
                .claim("uuid", user.getUuid())
                .claim("name", user.getName())
                .claim("sex", user.getSex().toString())
                .claim("rank", user.getRank().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(securityUtil.getJwtKey())
                .compact();

        return jwt;
    }

    // process login
    public TokenDto login(String email, String password, boolean rememberMe) {
        // create user's token
        String pepperedPassword = securityUtil.addPepper(password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, pepperedPassword);
        Authentication auth = null;
        try {
            auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        } catch (AuthenticationException e) {
            // print warn log about failing to login
            LogUtil.printBasicWarnLog(LogHeader.LOGIN, LogUtil.makeEmailKV(email), LogUtil.makeExceptionKV(e));
            return null;
        }
        String token = createToken(auth, email, rememberMe);
        TokenDto tokenDto = new TokenDto(token);

        return tokenDto;
    }
}
