package tv.mixiong.saas.pay.service;

import com.alibaba.dubbo.config.annotation.Service;
import tv.mixixiong.pay.IPayService;

@Service(interfaceClass = IPayService.class)
public class PayService implements IPayService {
    @Override
    public String pay() {
        return "pay";
    }
}
