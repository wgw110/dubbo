package tv.mixiong.xinge;

/**
 * event 常量类
 *
 */
public class EventConstants {
     /*
    * push相关
    * */
    public static final String ALL_USER_TAG = "all";
    public static final String PREVIEW_PUSH_TAG = "preview_";
    public static final String EMPTY = "";

    public static final int SUCCESS = 0;

    public static final String SUB_CONTENT = "您关注的主播%s正在直播,小伙伴们速来围观";
    public static final String SUB_TITLE = "通知";

    //预告之后,提前多长时间提醒播主开播, 单位分钟
    public static final int NOTICE_TIME = 15;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String CLOSE_LIVE_URL = "interior/liveStop";

    /**
     * 腾讯云关闭群组url
     */
    public static final String DESTORY_ROOM_URL = "https://console.tim.qq.com/v4/group_open_http_svc/destroy_group";

    /**
     * 发送消息
     */
    public static final String SEND_MSG_URL = "https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg";

    public static final String SUCCESS_RESULT_STATUS = "OK";

    public static final String LEAVE_LIVE_USER_ACTION = "10000";
    public static final String BACK_LIVE_USER_ACTION = "10001";

    public static final String LEAVE_LIVE_USER_ACTION_PARAM = "主播离开一下，精彩不中断，不要走开哦";
    public static final String BACK_LIVE_USER_ACTION_PARAM = "主播回来啦，视频即将恢复";

    public static final String UNUSUAL_CLOSE_USER_ACTION = "10002";
    public static final String MANAGER_CLOSE_USER_ACTION = "10003";

    public static final String UNUSUAL_CLOSE_USER_ACTION_PARAM = "网络差，直播已结束";
    public static final String MANAGER_CLOSE_USER_ACTION_PARAM = "内容违规，直播被关闭";

    public static final Long LIVE_SCORE = 1000000000000L;
    public static final Long PREVIEW_SCORE = 500000000000L;

}
