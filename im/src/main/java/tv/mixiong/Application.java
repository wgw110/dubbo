package tv.mixiong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;


@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"tv.mixiong"})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        log.info("{} profile is active", Arrays.toString(activeProfiles));
    }
}
