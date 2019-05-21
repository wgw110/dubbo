package tv.mixiong.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tv.mixiong.api.module.program.service.TransactionService;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.service.IPushService;

@RestController
@RequestMapping("/v1/im")
@Slf4j
public class ImApi {

    @Reference
    private IPushService pushService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/push")
    public String ss(@RequestParam(value = "input", defaultValue = "0") String input){
        String json = "{\"channel\":2,\"imCommonParam\":{\"account\":\"homework\",\"user_action\":\"20018\"},\"needPush\":true,\"noticeTemplate\":{\"base\":{\"action_url\":\"mxl://action.cmd?%7B%22payload%22%3A%7B%22id%22%3A%22305647%22%7D%2C%22event%22%3A%22openPostDetailPage%22%7D\",\"conversation_desc\":\"【缘起缘灭】向您提交了个作业\",\"redirect_desc\":\"查看详情\",\"send_time\":1550221183515,\"title\":\"作业提交通知\"},\"notice\":{\"data\":[{\"color\":\"#000000\",\"key\":\"课程名称\",\"type\":1,\"value\":\"一口就爱上的 抹茶朵朵软欧\"},{\"color\":\"#000000\",\"key\":\"作业标题\",\"type\":1,\"value\":\"交作业啦！抹茶朵朵\uD83D\uDE1C\"},{\"color\":\"#000000\",\"key\":\"作业详情\",\"type\":1,\"value\":\"交作业啦！抹茶朵朵\uD83D\uDE1C\"}],\"first\":\"【缘起缘灭】向您提交了个作业\",\"remark\":\"您可以针对学员提交作业进行打分与点评。\"},\"type\":3},\"productId\":1,\"pushInfo\":{\"action_url\":\"mxl://action.cmd?%7B%22payload%22%3A%7B%7D%2C%22event%22%3A%22openMessageTabPage%22%7D\",\"content\":\"【缘起缘灭】向您提交了个作业\",\"title\":\"作业提交通知\"},\"targets\":\"214807\",\"type\":3}";
        TemplateServiceRequest request = JSON.parseObject(json,TemplateServiceRequest.class);
        pushService.sendByTemplate(request);
        return "success";
    }

    @GetMapping("/transaction")
    public String transaction(){
        try{
            transactionService.testTransaction();
        }catch (Exception e){
            log.error("e",e);
        }
        return "success";
    }
}
