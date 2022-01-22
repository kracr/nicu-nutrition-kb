package com.inicu.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/** 
 * configuration of controllers...
 * @author iNICU 
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.oxyent"})
public class MvcConfig {
}
