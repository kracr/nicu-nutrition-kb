package com.inicu.postgres.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.postgres.utility.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Basic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.inicu.models.MessagePOJO;
import com.inicu.postgres.utility.BasicUtils;

import java.util.ArrayList;

/**
 *
 * @author Umesh Kumar
 * @Created on : 15 June,2019
 * @Updated on : 11 July,209
 * @purpose : interceptor implementation goes here which check for Token Verification
 */

@Component
public class JwtServiceInterceptor implements HandlerInterceptor {

    @Autowired
    JWTAuthenticationFilter JWTFilter;

    private static ArrayList<String> bypassUrl=new ArrayList<String>(){{
        add("getCameraUhid");
        add("/");
        add("/login");
        add("/reset-password");
        add("/forgot-password");
        add("/inicu/getInicuSettingPresference");
        add("/inicu/login");
        add("/inicu/forgotPassword");
        add("/inicu/getServerTime");
        add("/inicu/receiveMultipleData");
        add("/inicu/receiveData");
        add("/receiveData");
        add("/receiveDataBloodGas");
        add("/getCameraUhid");
        add("/getDeviceDetail/");
        add("/receiveCameraData/");
        add("/receiveMultipleData");
        add("/getLiveVideoCredentialForichr");
        add("/inicu/getTestData/");
        add("/inicu/getVentilatorData/");
        add("/inicu/demoNumericData/");
        add("/receiveMultipleDataBloodGas");
        add("/getvideoURL");
        add("/getvideoURL/");
        add("/inicu/getNurseConfirmation/");
        add("/inicu/saveQuestionnaireResult/");
    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        System.out.println("Inside the the pre-handler function");
        System.out.println(" URI :"+request.getRequestURI()+" && Authorization: "+request.getHeader("Authorization"));
//        System.out.println(bypassUrl);

        if(bypassUrl.contains(request.getRequestURI())){
            System.out.println("Condition Matched");
            return true;
        }

        if(request.getRequestURI()!=null) {
            String[] requestString = request.getRequestURI().split("/");

            if(requestString!=null && !BasicUtils.isEmpty(requestString)) {
                if ( requestString[1]!=null && !requestString[1].equalsIgnoreCase("inicu") && !requestString[1].equalsIgnoreCase("sys")) {
                    return true;
                }
            }else{
                return true;
            }
        }else
            {
            return true;
        }

        long now = System.currentTimeMillis();
        System.out.println("PreHandler Launched at time:"+now/(1000*60));
        String header = request.getHeader("Authorization");
        ObjectMapper mapper = new ObjectMapper();
        MessagePOJO msg = new MessagePOJO();// customised pojo for error json message

        if (header == null || !header.startsWith("Bearer ")) {
            msg.setSubject("Unauthorized");
            msg.setMessage("Bearer Token Required");
            msg.setStatus_code(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(msg));
            return false;
        }

        String authToken = header.substring(7);
        boolean res=JWTFilter.verifyJWTToken(authToken);
        if(res){
        	return true;
        }
         
        msg.setSubject("Forbidden");
        msg.setMessage("Permission Denied");
        msg.setStatus_code(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(mapper.writeValueAsString(msg));
        return false;
        
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long now = System.currentTimeMillis();
		System.out.println("Post Handler Launched at time:" + now / (1000 * 60));

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long now = System.currentTimeMillis();
		System.out.println("Process Completed at time :" + now / (1000 * 60));
	}
}
