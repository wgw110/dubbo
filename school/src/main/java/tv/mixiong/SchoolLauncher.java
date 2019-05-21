package tv.mixiong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "tv.mixiong")
@Slf4j
public class SchoolLauncher {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SchoolLauncher.class, args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        log.info("{} profile is active", Arrays.toString(activeProfiles));
    }
}
