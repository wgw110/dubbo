package tv.mixiong.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import tv.mixiong.channel.BaseChannel;
import tv.mixiong.channel.ChannelFactory;
import tv.mixiong.channel.v2.IMMsgChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tv.mixiong.ds.service.im.ImAdminAccountDs;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.response.CommonResponse;
import tv.mixixiong.im.service.IPushService;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * 信鸽push服务
 *
 */
@Service(interfaceClass = IPushService.class)
@Slf4j
public class PushService implements IPushService {

    @Autowired
    ImAdminAccountDs imAdminAccountDs;

    @Autowired
    private ChannelFactory channelFactory;

    private Logger logger = LoggerFactory.getLogger(PushService.class);

    private ExecutorService executors = Executors.newFixedThreadPool(10);

    /**
     * @param request push 请求
     * @return push
     * @throws
     */
    @Override
    public CommonResponse sendPush(MessageServiceRequest request)  {
        System.out.println("service:"+request);
        Integer productId = request.getProductId() == null ? 1 : request.getProductId();
        executors.submit(() -> {
            try {
                checkParam(request);
                List<String> passportList = Lists.newArrayList(request.getTargets().split(","));
                List<BaseChannel> channels = channelFactory.getChannels(request.getChannel());
                channels.stream().forEach(channel -> {
                    try {
                        channel.send(productId, passportList, request);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                });
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });


        return CommonResponse.DEFAULT_SUCCESS;
    }

    @Override
    public CommonResponse sendSingle(MessageServiceRequest request) {
        Integer productId = request.getProductId() == null ? 1 : request.getProductId();
        executors.submit(() -> {
            try {
                checkParam(request);
                String receiver = request.getTargets();
                List<BaseChannel> channels = channelFactory.getChannels(request.getChannel());
                channels.stream().forEach(channel -> {
                    try {
                        channel.sendSingle(productId, receiver, request);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                });
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });


        return CommonResponse.DEFAULT_SUCCESS;
    }

    @Override
    public CommonResponse sendByTemplate(TemplateServiceRequest request) {
        Integer productId = request.getProductId() == null ? 1 : request.getProductId();

            executors.submit(() -> {
                try {
                    List<String> passportList = Lists.newArrayList(request.getTargets().split(","));
                    List<BaseChannel> channels = channelFactory.getChannels(request.getChannel());
                    channels.stream().filter(BaseChannel::supportTemplate).forEach(channel -> {
                        try {
                            channel.sendByTemplate(productId, passportList, request);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    });
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            });
        return CommonResponse.DEFAULT_SUCCESS;
    }

    @Override
    public CommonResponse sendTemplateToAll(TemplateServiceRequest request) {
        List<BaseChannel> channels = channelFactory.getChannels(request.getChannel());
        CommonResponse commonResponse=new CommonResponse();
        CommonResponse commonResponse1= execute(request, o -> {
            channels.stream().filter(BaseChannel::supportTemplate).forEach(channel -> {
                try {
                    CommonResponse commonResponse2=channel.sendTemplateToAll(request);
                    if(channel.getClass().getName().equals(IMMsgChannel.class.getName())){
                        commonResponse.setData(commonResponse2.getData());
                        logger.info(String.format("push message is:%s, %s, %s",commonResponse2.getData(),request.getPushInfo().toString(),commonResponse2.getStatus()));
                    }
                } catch (Exception e) {
                    logger.error("sendTemplateToAll-", e);
                }
            });
        },null);
        if(commonResponse.getData()!=null){
            commonResponse1.setData(commonResponse.getData());
        }
        return commonResponse1;
    }


    @Override
    public CommonResponse sendToAll(MessageServiceRequest request) {
        List<BaseChannel> channels = channelFactory.getChannels(request.getChannel());
        return execute(request, o -> {
            channels.stream().forEach(channel -> {
                try {
                    channel.sendToAll(request);
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
        },null);
    }


    private void checkParam(MessageServiceRequest request) {
        try {
            Preconditions.checkNotNull(request.getTargets(), "passports不能为空");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private CommonResponse execute(Object request, Consumer<Object> consumer, CountDownLatch countDownLatch) {
            executors.submit(() -> {
                try {
                    consumer.accept(request);
                    if(countDownLatch!=null){
                        countDownLatch.countDown();
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
        return CommonResponse.DEFAULT_SUCCESS;
    }

    @Override
    public void saveIm(int i) throws Exception{
        imAdminAccountDs.im_transaction(i);
    }
}
