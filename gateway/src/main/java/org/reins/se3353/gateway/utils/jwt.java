package org.reins.se3353.gateway.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.reins.se3353.gateway.vo.UserInfo;

import java.io.UnsupportedEncodingException;

public class jwt {
    public static UserInfo parseToken(String token){
        try {
            String tokenSignKey = "secret123456";
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSignKey))
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String userName = jwt.getClaim("user_name").asString();
            String user_role = jwt.getClaim("user_role").asString();
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(userName);
            userInfo.setUserRole(user_role);
            return userInfo;
        } catch (Exception e){
            System.out.println("Jwt decode error");
            return null;
        }
    }
}
