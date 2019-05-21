package tv.mixiong.channel.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.mixiong.channel.BaseChannel;
import tv.mixiong.channel.Channel;
import tv.mixiong.ds.dao.entity.UserPush;
import tv.mixiong.ds.dao.push.UserPushDao;
import tv.mixiong.enums.Platform;
import tv.mixiong.properties.XingeProperties;
import tv.mixiong.xinge.PushParam;
import tv.mixiong.xinge.PushSender;
import tv.mixiong.xinge.XingeApp;
import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.template.CommonMsg;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.enums.ChannelEnum;
import tv.mixixiong.im.response.CommonResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Channel(name = ChannelEnum.XG_PUSH)
@Component
public class XgPushMsgChannel extends BaseChannel {

    @Autowired
    private XingeApp xingeApp;

    @Autowired
    private UserPushDao userPushDao;

    @Autowired
    private XingeProperties xingeProperties;

    @Override
    public void send(int productId, List<String> passports, MessageServiceRequest messageServiceRequest) {
        PushParam pushParam = new PushParam();
        pushParam.setEnv(xingeProperties.getEnv());
        CommonMsg commonMsg = messageServiceRequest.getCommonMsg();
        pushParam.setContent(commonMsg.getContent());
        pushParam.setTitle(commonMsg.getTitle());
        pushParam.setActionUrl(commonMsg.getActionUrl());

        List<UserPush> userPushVoList = userPushDao.findByProductIdAndPassportInAndStatus(productId, passports, 1);
        Map<Integer, List<UserPush>> tokenMaps = userPushVoList.stream().collect(Collectors.groupingBy(UserPush::getPlatform));

        List<UserPush> iosUsers = tokenMaps.get(Platform.IPHONE.getType());
        if (iosUsers != null && !iosUsers.isEmpty()) {
            List<String> iosTokens = tokenMaps.get(Platform.IPHONE.getType()).stream().map(UserPush::getToken).collect(Collectors.toList());
            PushSender.batchPushToIOS(xingeApp, iosTokens, pushParam);
        }

        List<UserPush> androidUsers = tokenMaps.get(Platform.ANDROID_PHONE.getType());
        if (androidUsers != null && !androidUsers.isEmpty()) {
            List<String> androidTokens = tokenMaps.get(Platform.ANDROID_PHONE.getType()).stream().map(UserPush::getToken).collect(Collectors.toList());
            PushSender.batchPushToAndroid(xingeApp, androidTokens, pushParam);
        }
    }

    @Override
    public void sendByTemplate(int productId, List<String> targets, TemplateServiceRequest templateServiceRequest) {

    }

    @Override
    public void sendSingle(int productId, String receiver, MessageServiceRequest messageRequest) {

    }

    @Override
    public CommonResponse sendTemplateToAll(TemplateServiceRequest templateServiceRequest) {
        return null;
    }

    @Override
    public void sendToAll(MessageServiceRequest messageServiceRequest) {

    }
}

