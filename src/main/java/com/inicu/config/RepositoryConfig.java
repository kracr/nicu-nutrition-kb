package com.inicu.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/** 
 * 
 * @author iNICU 
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.oxyent")
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.oxyent"})
public class RepositoryConfig {
}
