//package tv.mixiong.dubbo.filter;
//
//import com.alibaba.dubbo.rpc.RpcContext;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Aspect
//@Component
//public class DubboServiceContextAop {
//
//    @Pointcut("execution(* tv.mixiong.dubbo.service.*(..))")
//    public void serviceApi() {
//    }
//
//    @Before("serviceApi()")
//    public void dubboContext(JoinPoint jp) {
//        Map<String, String> context = new HashMap<>();
//        RpcContext.getContext().setAttachments(context);
//    }
//}
