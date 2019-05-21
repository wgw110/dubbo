package tv.mixiong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"tv.mixiong.api","tv.mixiong.web"})
public class ApiLauncher {
    public static void main(String[] args) {
        System.setProperty("fastjson.parser.autoTypeSupport", "true");
        SpringApplication.run(ApiLauncher.class, args);
    }
}
