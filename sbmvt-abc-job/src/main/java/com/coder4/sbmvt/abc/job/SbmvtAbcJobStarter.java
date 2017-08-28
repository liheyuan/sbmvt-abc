package com.coder4.sbmvt.abc.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author coder4
 */
@SpringBootApplication
public class SbmvtAbcJobStarter {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SbmvtAbcJobStarter.class);
        application.setWebEnvironment(false);
        application.run(args);
    }

}
