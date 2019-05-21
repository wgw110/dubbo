package tv.mixiong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class ProviderLauncher {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProviderLauncher.class, args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        log.info("{} profile is active", Arrays.toString(activeProfiles));
    }
}
