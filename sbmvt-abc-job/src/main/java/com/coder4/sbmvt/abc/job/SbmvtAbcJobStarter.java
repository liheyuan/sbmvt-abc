package com.coder4.sbmvt.abc.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author coder4
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SbmvtAbcJobStarter {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SbmvtAbcJobStarter.class);
        application.setWebEnvironment(false);
        application.run(args);
    }

}
