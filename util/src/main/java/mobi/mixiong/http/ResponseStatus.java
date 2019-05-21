package mobi.mixiong.http;

/**
 * User: zhangbinwu
 * Date: 15-4-22
 * Time: 上午10:38
 */
public enum ResponseStatus {
    //200是成功的代码
    SUCCESS_STATUS(200, "OK"),
    COMMON_ERROR_STATUS(500, "服务器错误"),
    NOT_FOUND_PASSPORT(40001, "用户名passport不存在"),
    NEED_LOGIN(40006, "需要登录"),
    INVALID_PASSPORT(40017, "无效的帐号"),
    BLACKLIST_PASSPORT(40018, "您已经被管理员封禁,无法发起直播或预告"),
    PARAM_REQUIRED(40025, "缺少请求参数"),
    PARAM_ERROR(40026, "参数格式错误"),
    MOBILE_ERROR(40100, "即将购买虚拟商品，为保护财产安全建议您完成手机号绑定。"),
    INVALID_ROOM_ID(41219, "无效的房间号"),
    BLANK_FEEDBACK_CONTENT(41220, "反馈内容为空"),
    INVALID_SHARE_TYPE(41221, "无效分享类型"),
    INVALID_PLATFORM(41222, "无效的平台代码"),
    FOLLOWING_COUNT_OVERHEAD(41223, "关注人数超过上限"),
    SYSTEM_RESTRICTION(41224, "系统限制"),
    CANT_FOLLOW_SELF(41225, "不允许订阅自己"),
    PASSPORT_INCONSISTENT(41226, "删除回看时,passport与回看的passport不一致"),
    PASSPORT_IN_BLOCK(41227, "在黑名单中"),
    UNIQUE_PREVIEW(41228, "预告同时创建一个"),
    CANT_GET_PAY_INFO(41229, "无法获取商品信息"),
    LIVE_NO_PAY(41230, "直播未付费"),
    PREVIEW_CANCELED(41231, "直播已取消"),
    H5_NOT_PLAY_PAY_LIVE(41232, "h5不能播放付费直播"),
    INVALID_SVER(41233, "无效的版本号"),
    UNIQUE_VOTE(41234, "该直播已存在一个为完成的投票"),
    HAS_VOTED(41235, "已投票"),
    PREVIEW_DELETED(41232, "预告已被删除"),
    CANT_CREATE_QUESTION(41233, "无法创建问题"),
    INVALID_QUESTION_ID(41234, "无效的问题"),
    CANT_PRAISE_QUESTION(41235, "不能点赞"),
    VOTE_OPTION_ERROR(41236, "无效的投票选项"),
    CANT_CREATE_GROUP(41237, "无法创建群"),
    INVALID_GROUP_ID(41238, "无效的群组id"),
    INVALID_GROUP_OWNER(41239, "不能设置群主讲人"),
    INVALID_GROUP_OPERATION(41240, "不能踢出群主"),
    INVALID_GROUP_MEMBER(41241, "不是群成员"),
    INVALID_INVITE_KEY(41242, "无效的邀请KEY"),
    INVALID_GROUP_PAY_ID(41243, "无效的支付方式"),
    INVALID_CREATE_PREVIEW_TIME(41244, "无效的创建预告时间"),
    INVALID_CREATE_PREVIEW_TIME_RANGE(41245, "时间间隔内有未开播预告"),
    INVALID_CREATE_PREVIEW_COUNT(41246, "时间间隔内已有足够数量的未开播预告"),
    CANT_DELETE_RECORD(41247, "付费预告无法删除"),
    CANT_LIVE_FROM_PREVIEW(41248, "不可开播"),
    INVALID_PREVIEW(41249, "无效的预告"),
    CANT_UPDATE_GROUP(41250, "无法更新群"),
    DUPLICATE_OPT(41251, "重复操作"),
    ERROR_INVITE_KEY(41252, "错误的邀请KEY"),
    CANT_INVITE_SELF(41253, "不能邀请自己"),
    INVITE_KEY_USED(41254, "邀请码已使用"),
    INVITE_KEY_EXPIRED(41255, "邀请码已过期"),
    GROUP_INVITED(41256, "已经是群主讲人"),
    GUEST_COUNT_OVER_HEAD(41257, "只能邀请10位主讲人"),
    INVALID_PROGRAM_ID(41258, "无效的课程id"),
    UNSHELEVE_UNFINISH_LIVE(41259, "有未完成的直播,不能下架"),
    INVALID_QUIT_GROUP(41260, "有未完成的直播,不能退群"),
    QUIT_GROUP_ERROR(41261, "退群失败,稍后重试"),
    GROUP_HAS_DISBANDED(41262, "该群已经解散"),
    GROUP_NOT_FOUND(41263, "群组不存在"),
    CANT_COMMENT(41264, "不能回复"),
    COMMENT_CANT_BLANK(41265, "评论内容不能为空"),
    CANT_DEL_COMMENT(41266, "不能删除评论"),
    INVALID_UPLOAD_KEY(41267, "不合法的uploadKey"),
    UPLOAD_KEY_UNSCAN(41268, "UPLOADKEY还未被扫码"),
    INVALID_TIME_RULE(41269, "时间规则设置有误"),
    INVALID_PRICE(41269, "价格不能小于0"),
    USER_TIME_RESERVED_ERROR(41270, "用户时间已经被预约"),
    SET_USER_TIME_PRICE_ERROR(41271, "设置用户时间价格失败"),
    INVALID_RESERVE_TIME_LENGTH(41272, "无效的时间段"),
    ANCHOR_ONLY(41273, "只有主播才能操作"),
    QUESTION_OWNER_ONLY(41274, "只有问题提问者才能删除"),
    ACCESS_LIMIT_STATUS(41275, "访问次数受限"),
    GROUP_TYPE_ERROR(41276, "群组类型错误"),
    GROUP_BLACKBOARD_ERROR(41277, "小黑板参数错误"),
    REFUND_ERROR(41278, "退款失败"),
    CONTACT_NOT_FOUND(41279, "联系人不存在"),
    CANT_DEL_APPRAISE(41280, "不能删除评价"),
    CANT_APPRAISE(41281, "不能发表评价"),
    UNMATCHED_HOMEWORK_TYPE(41282, "不匹配的作业类型"),
    HOMEWORK_DELETED(41283, "该作业已被删除"),
    NOT_FOUND_ORGANIZATION(41284, "学校organization为空"),
    RECEIVE_GIFT_PACKAGE_ERROR(41285, "领取礼包失败，请稍后重试"),
    VERIFIED_ONLY(41286, "只有达人才有此权限"),
    NOT_SERVICE(50001, "该服务已下线"),
    SERVICE_UNAVAILABLE(50000, "系统内部错误")
    ;
    private int status;
    private String desc;

    ResponseStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

