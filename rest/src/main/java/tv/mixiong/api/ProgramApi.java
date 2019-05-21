package tv.mixiong.api;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tv.mixiong.dubbo.service.IProgramService;

@RestController
@RequestMapping("/v1/program")
@Component
@Slf4j
public class ProgramApi {

    @Reference()
    private IProgramService programService;

    @GetMapping("list")
    public String test(@RequestParam(value = "input", defaultValue = "0") String input) {
        return programService.sayHello(input);
    }


}
