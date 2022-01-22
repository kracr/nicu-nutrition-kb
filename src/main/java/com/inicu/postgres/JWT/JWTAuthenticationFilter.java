package com.inicu.postgres.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inicu.models.NurseShiftDetail;
import com.inicu.postgres.dao.LoginDao;
import com.inicu.postgres.serviceImpl.SettingsServiceImp;
import com.inicu.postgres.utility.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;


/**
 * 
 * @author Umesh Kumar 
 * 
 */
@Repository
public class JWTAuthenticationFilter{


    @Autowired
    LoginDao loginDao;

    @Autowired
    SettingsServiceImp settingsServiceImp;

    // private Variable
    long expireAt;

    public static String SECRET_KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";

    // get today shift for this particular nurse
    public int getNurseShiftTimings(Integer nurse_id,String branch){
        expireAt=0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        NurseShiftDetail nurseShiftDetails= (NurseShiftDetail) settingsServiceImp.getNurseDetail(branch,date.toString(),nurse_id);
        System.out.println(dateFormat.format(date) + " :: " + nurseShiftDetails);

        long now = System.currentTimeMillis();
        SimpleDateFormat formatHours = new SimpleDateFormat("HH");
        String getHours = formatHours.format(new Date());
        int hour=Integer.parseInt(getHours);

        if(!BasicUtils.isEmpty(nurseShiftDetails)){

            if(nurseShiftDetails.getShift1()!=null && nurseShiftDetails.getShift1()==true){
                int to=nurseShiftDetails.getShift1To();
                int from=nurseShiftDetails.getShift1From();
                if(hour>=from && hour<=to)
                {
                    int workingShiftTime=(to-hour)+1;
                    expireAt=now+(workingShiftTime*3600000);
                }
            }

            if(nurseShiftDetails.getShift2()!=null && nurseShiftDetails.getShift2()==true){
                int to=nurseShiftDetails.getShift2To();
                int from=nurseShiftDetails.getShift2From();
                if(hour>=from && hour<=to)
                {
                    int workingShiftTime=(to-hour)+1;
                    expireAt=now+(workingShiftTime*3600000);
                }
            }

            if(nurseShiftDetails.getShift3()!=null && nurseShiftDetails.getShift3()==true){
                int to=nurseShiftDetails.getShift3To();
                int from=nurseShiftDetails.getShift3From();
                if(hour>=from && hour<=to)
                {
                    int workingShiftTime=(to-hour)+1;
                    expireAt=now+(workingShiftTime*3600000);
                }
            }

            if(nurseShiftDetails.getShift4()!=null && nurseShiftDetails.getShift4()==true){
                int to=nurseShiftDetails.getShift4To();
                int from=nurseShiftDetails.getShift4From();
                if(hour>=from && hour<=to)
                {
                    int workingShiftTime=(to-hour)+1;
                    expireAt=now+(workingShiftTime*3600000);
                }
            }
        }
        if(expireAt!=0)
        {
            return 1;
        }
        // check the shift timings if its not here shift and she want to login then generate a READ only token for such user
        return 0;
    }

    // get the token for different user type /doctor/nurse/admin/tester
    public String getJWTtoken(Integer userId,String username,String userType,String branchName){
        try {
            String token=null;

            // get the Current User Shift timings for nursing only
            if(Integer.parseInt(userType)==5){

                int res=getNurseShiftTimings(userId,branchName);
                if(res==1){
                    token=getGeneralToken(username,userId);
                }else{
                	expireAt=12*7200000;   // 24 Hr
                    token=getReadOnlyToken(username,userId);
                }
                return token;
            }
            expireAt=12*7200000;   // 24 Hr
            token=getGeneralToken(username,userId);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            System.out.println("Handling the Error message" +exception);
        }
        return null;
    }

    public String getGeneralToken(String username,Integer userId){
        // Current Time
        long now = System.currentTimeMillis();
        System.out.println("JWT: current Time :: " + now);

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withClaim("name",username)
                .withClaim("userId",userId)
                .withClaim("ReadOnly",false)
                .withExpiresAt( new Date( now + expireAt ))
                .sign(algorithm);
        System.out.println("Token is:"+token);
        return token;
    }

    public String getReadOnlyToken(String username,Integer userId){
        // Current Time
        long now = System.currentTimeMillis();
        System.out.println("JWT: current Time :: "+ now);

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withClaim("name",username)
                .withClaim("userId",userId)
                .withClaim("ReadOnly",true)
                .withExpiresAt( new Date( now + expireAt ))
                .sign(algorithm);
        System.out.println("Token is:"+token);
        return token;
    }


    public DecodedJWT decodeJWTToken(String tokenValue){
        // Decode the token
        try {
            DecodedJWT jwt = JWT.decode(tokenValue);
            return jwt;
        } catch (JWTDecodeException exception) {
            //Invalid token
            exception.printStackTrace();
        }
        return null;
    }

    public Boolean verifyJWTToken(String tokenValue){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            DecodedJWT mytoken=decodeJWTToken(tokenValue);
            if(mytoken!=null && !BasicUtils.isEmpty(mytoken) && checkExpiryStatus(mytoken)==true) {

                String tempUsername = mytoken.getClaim("name").asString();
                String tempUserId = mytoken.getClaim("userId").asString();

                JWTVerifier verifier = JWT.require(algorithm)
                        .withClaim("name", tempUsername)
                        .withClaim("userId", tempUserId)
                        .build(); //Reusable verifier instance

                    DecodedJWT jwt = verifier.verify(tokenValue);
                    // Fields related JWT token
                    System.out.print("JWT: uid: " + jwt.getClaim("name"));
                    System.out
                            .println("JWT: exp: " + jwt.getExpiresAt() + ", algorithm: " + jwt.getAlgorithm());
                return true;
            }else{
                return false;
            }
            } catch(JWTVerificationException exception){
                handleException();
                return false;
            }
    }

    private Boolean isTokenExpired(DecodedJWT token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public  Claim getClaimFromToken(DecodedJWT token) {
        Claim claimName=token.getClaim("name");
        return claimName;
    }

    public void getNurseShiftTimings(){

    }

    public String getUsernameFromToken(DecodedJWT token) {
        return token.getIssuer();
    }

    public Boolean checkExpiryStatus(DecodedJWT token){
        Date expireDate=getExpirationDateFromToken(token);
        return true;
    }

    public Date getExpirationDateFromToken(DecodedJWT token) {
        return token.getExpiresAt();
    }

    public void handleException() {
        System.out.println("JWT Token not verified");
    }
}
