package tv.mixixiong.im.service;


import tv.mixixiong.im.bean.MessageServiceRequest;
import tv.mixixiong.im.bean.template.TemplateServiceRequest;
import tv.mixixiong.im.response.CommonResponse;

/**
 * push service
 *
 */
public interface IPushService {

    /**
     * 发送消息
     *
     * @param request
     * @return
     * @throws
     */
    CommonResponse sendPush(MessageServiceRequest request);


    CommonResponse sendSingle(MessageServiceRequest request);


    /**
     * 发送模板
     *
     * @param request
     * @return
     * @throws
     */
    CommonResponse sendByTemplate(TemplateServiceRequest request);

    /**
     * 向所有用户发送模板消息
     *
     * @return
     * @throws
     */
    CommonResponse sendTemplateToAll(TemplateServiceRequest request);

    /**
     * 向全部用户发送消息
     *
     * @param request
     * @return
     * @throws
     */
    CommonResponse sendToAll(MessageServiceRequest request);

    void saveIm(int i) throws Exception;
}
