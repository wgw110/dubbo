package tv.mixiong.service.program;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tv.mixiong.dubbo.service.IProgramService;
import tv.mixiong.dao.preview.PreviewDao;
import tv.mixiong.extend.handler.HandlerFactory;
import tv.mixixiong.entity.ProgramFormBean;

import java.util.Map;

@Slf4j
@Service(interfaceClass = IProgramService.class)
public class ProgramServiceImpl implements IProgramService {

    @Autowired
    PreviewDao previewDao;
    @Autowired
    HandlerFactory handlerFactory;
    @Override
    public String sayHello(String input){
        handlerFactory.getHandler(1).save();
        log.info("input : {}", input);
        return "hello" + input;
    }

    @Override
    public void saveProgram(ProgramFormBean program) {

    }

    @Override
    public void updateProgram(Map<String, Object> sets, Map<String, Object> wheres) {

    }

}
