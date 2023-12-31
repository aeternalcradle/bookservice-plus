package org.reins.se3353.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.reins.se3353.auth.bean.Book;

import java.util.Calendar;
import java.util.Date;


public class JWTUtils {

    public static String createToken(Book userinfo, Boolean refresh){
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        if(refresh){
            //refreshToken 10天过期
            nowTime.add(Calendar.DATE, 10);
        }else {
            //非refreshToken 1小时过期
            nowTime.add(Calendar.HOUR, 1);
        }
        Date expiresDate = nowTime.getTime();
        String token ="";
        String tokenSignKey = "secret123456";
        token = JWT.create().withAudience(userinfo.getName())
                .withClaim("user_role", userinfo.getName())
                .withClaim("user_name", userinfo.getName())
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(tokenSignKey));
        return token;
    }

}
