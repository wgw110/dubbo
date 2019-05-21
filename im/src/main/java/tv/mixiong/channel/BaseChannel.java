package tv.mixiong.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.response.CommonResponse;

import javax.annotation.PostConstruct;
import java.util.List;



@Component
public abstract class BaseChannel {

    @Autowired
    private ChannelFactory channelFactory;

    private boolean supportTemplate;

    @PostConstruct
    private void initialize() {
        Channel channel = this.getClass().getAnnotation(Channel.class);
        if (channel == null) {
            throw new RuntimeException("必须设定消息通道类型");
        }
        this.setSupportTemplate(channel.supportTemplate());
        channelFactory.put(channel.name(), this);
    }

    public void setSupportTemplate(boolean supportTemplate) {
        this.supportTemplate = supportTemplate;
    }

    public boolean supportTemplate() {
        return supportTemplate;
    }

    /**
     * 发送消息.
     *
     * @param targets        消息接受者：用户为passport，群组为groupId
     * @param messageRequest
     */
    public abstract void send(int productId, List<String> targets, MessageServiceRequest messageRequest);

    public abstract void sendSingle(int productId, String receiver, MessageServiceRequest messageRequest);

    public abstract void sendByTemplate(int productId, List<String> targets, TemplateServiceRequest templateServiceRequest);

    public abstract CommonResponse sendTemplateToAll(TemplateServiceRequest templateServiceRequest);

    public abstract void sendToAll(MessageServiceRequest messageServiceRequest);

}
