package tv.mixixiong.entity;

import lombok.Data;

@Data
public class ProgramFormBean {

    private Long programId;
//    @FormParam("subject")
    private String subject;
//    @FormParam("summary")
    private String summary;
//    @FormParam("cover")
    private String cover;
//    @FormParam("market_price_type")
    private Integer marketPriceType;
//    @FormParam("price")
    private Integer price;
//    @FormParam("market_max_price")
    private Integer marketMaxPrice;
//    @FormParam("desc_json")
    private String descJson;
//    @FormParam("tags_json")
    private String tagsJson;
//    @FormParam("type")
    private Integer type;
//    @FormParam("preview_url")
    private String previewUrl;
//    @FormParam("preview_width")
    private Integer previewWidth;
//    @FormParam("preview_height")
    private Integer previewHeight;
//    @FormParam("preview_cover")
    private String previewCover;
//    @FormParam("preview_duration")
    private Integer previewDuration;
//    @FormParam("episode_count")
    private Integer episodeCount;
//    @FormParam("episode_info_json")
    private String episodeInfoJson;
//    @FormParam("rebate_ratio")
    private Integer rebateRatio;
//    @FormParam("discount_type")
    private Integer discountType;
//    @FormParam("discount_ratio")
    private Integer discountRatio;
//    @FormParam("discount_amount")
    private Integer discountAmount;
//    @FormParam("discount_end_time")
    private Long discountEndTime;
//    @FormParam("images_json")
    private String imagesJson;
//    @FormParam("end_sale_time")
    private Long endSaleTime;
//    @FormParam("min_user_num")
    private Integer minUserNum;
//    @FormParam("max_user_num")
    private Integer maxUserNum;
//    @FormParam("has_certification")
    private Integer hasCertification;
//    @FormParam("allow_coupon")
    private Integer allowCoupon;
//    @FormParam("video_equipment")
    private Integer videoEquipment;
//    @FormParam("teach_language")
    private Integer teachLanguage;
//    @FormParam("teach_ground")
    private Integer teachGround;
//    @FormParam("time_length_per_lesson")
    private Integer timeLengthPerLesson;
//    @FormParam("student_level")
    private Integer studentLevel;
//    @FormParam("classification_json")
    private String classificationJson;
//    @FormParam("apply_to")
    private Integer applyTo;
//    @FormParam("vip_allowed")
    private Integer vipAllowed;
//    @FormParam("manual_publish")
    private Integer manualPublish;
//    @FormParam("paid_msg")
    private String paidMsg;
//    @FormParam("discount_end_in_hours")
    private Integer discountEndInHours;
//    @FormParam("vip_discount_rate")
    private Integer vipDiscountRate;
//    @FormParam("ui_json")
    private String uiJson;
//    @FormParam("show_contact")
    private Integer showContact;
//    @FormParam("tutor_status")
    private Integer tutorStatus;
//    @FormParam( "tutor_passports_json")
    private String tutors;
//    @FormParam("auto_wechat")
    private Integer autoWechat;
//    @FormParam("auto_validate_wechat")
    private Integer autoValidateWechat;
//
//    public Program toProgram (ProgramFormBean param){
//        Program program =new Program();
//        BeanUtils.copyProperties(param,program);
//        return program;
//    }
//
//    public Preview toPreview (ProgramFormBean param){
//        Preview preview = new Preview();
//        BeanUtils.copyProperties(param, preview);
//        return preview;
//    }


}
