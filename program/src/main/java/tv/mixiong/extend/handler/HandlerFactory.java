package tv.mixiong.extend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.dubbo.exception.ServiceException;
import tv.mixiong.enums.PidEnum;

@Component
public class HandlerFactory {
    @Autowired
    MXProgramHandler mxProgramHandler;

    public Handler getHandler(int pid){
        if(pid== PidEnum.MX.getType()){
            return mxProgramHandler;
        }
        throw new ServiceException("pid 参数错误");
    }

}
