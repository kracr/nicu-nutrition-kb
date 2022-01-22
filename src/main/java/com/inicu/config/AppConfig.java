package com.inicu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
/** 
 * 
 * @author iNICU 
 *
 */
@Configuration
@ComponentScan
@EnableWebSocketMessageBroker
public class AppConfig extends AbstractWebSocketMessageBrokerConfigurer{

	 public void configureMessageBroker(MessageBrokerRegistry config) {
	        config.enableSimpleBroker("/topic");
	        config.setApplicationDestinationPrefixes("/app");
	    }
	 
	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		// TODO Auto-generated method stub
		stompEndpointRegistry.addEndpoint("/websocket").withSockJS();
	}
	
}
