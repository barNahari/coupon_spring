package com.example.coupondb.utils;


import com.example.coupondb.beans.UserDetails;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JWTutil {
    /**
     * Signature algorithm field - type of encryption
     */
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    /**
     * Encoded secret key field - our private key
     */
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    /**
     * Decoded secret key field - creates our private key
     */
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), signatureAlgorithm);

    /**
     * Generate token
     * this method generates our key
     * @param userDetails - the user's details
     * @return Token in String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("password", userDetails.password);
        claims.put("clientType", userDetails.getClientType());
        claims.put("userID", userDetails.getUserID());
        String myToken = createToken(claims, userDetails.getEmail());
        System.out.println("New token was created : " + myToken );
        return myToken;
    }

    /**
     * Create token
     * this method creates our token
     * @param claims - contains the fields which we are basing the token on
     * @param subject - ?????????????
     * @return Token in String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    /**
     * Extract all claims
     * this method extracts all the claims in json
     * @param token - the user's token
     * @return Claims
     * @throws ExpiredJwtException throws error if something went wrong
     */
    public Claims extractAllClaims(String token) throws Exception {
        //if (token.split(" ").length<2) throw new Exception("Invalid token");
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
        System.out.println(jwtParser.parseClaimsJws(cleanToken(token)).getSignature());
        return jwtParser.parseClaimsJws(cleanToken(token)).getBody();
    }

    /**
     * Extract email
     * this method extracts the user's email
     * @param token- the user's token
     * @return String - the user's email
     */
    public String extractEmail(String token) {
        // System.out.println("email:"+extractAllClaims(token).getSubject());
        try {
            return extractAllClaims(cleanToken(token)).getSubject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String extractPassword (String token){
        // System.out.println("password:"+extractAllClaims(token).getId());
        try {
            return extractAllClaims(cleanToken(token)).getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }

    }



    /**
     * Extract expiration date
     * this method extracts the expiration date of the token
     * @param token -the user's token
     * @return the token's expiration date
     */
    public Date extractExpirationDate(String token) {
        try {
            return extractAllClaims(token).getExpiration();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Is token expired
     * this method checks if the token is expired
     * @param token - the user's token
     * @return boolean- if it's expired
     */
    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(cleanToken(token));
            return false;
        } catch (ExpiredJwtException | SignatureException err) {
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Validate token
     * this method checks the validation of the user's details with the token
     * @param token - the user's token
     * @return boolean - if the token is valid
     */
    public boolean validateToken(String token) {
        token = token.split(" ")[1];
        //final String username = extractEmail(token);
        return !isTokenExpired(cleanToken(token)) ; //validate signutare
    }

    public HttpHeaders getHeadres(String token) throws Exception {
        //create new userDetails and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(extractEmail(cleanToken(token)));
        userDetails.setClientType((String)extractAllClaims(cleanToken(token)).get("clientType"));
        Claims test = extractAllClaims(cleanToken(token));
        int userID = (int)(extractAllClaims(cleanToken(token)).get("userID"));
        userDetails.setUserID(userID);
        //send ok with header of new token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Autorization","Bearer "+generateToken(userDetails));
        return httpHeaders;
    }

    private String cleanToken(String token){
        String[] cleanedToken = token.split(" ");
        if (cleanedToken.length>1){
            return cleanedToken[1];
        }
        return token;
    }
}
