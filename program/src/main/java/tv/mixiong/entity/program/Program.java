package tv.mixiong.entity.program;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    private Long id;
    private String summary;
    private String verticalCover;
    private String horizontalCover;
    private String subject;
    private String passport;
    private int status;
    private int testType;
    private Long updateTime;
    private Long createTime;
    private Long startTime;
    private Long endTime;
    private Integer programCount;
    private String discussionGroupId;
    private int scope;
    private String descJson;
    private int type;
    private Integer maxUserNum;
    private Integer hasCertificate;
    private Integer timeLengthPerLesson;
    private Integer studentLevel;
    private Integer videoEquipment;
    private Integer teachGround;
    private Integer teachLanguage;
    private Integer applyTo;
    private Integer vodType;
    private String paidMessage;
    private Integer showContact = 0;
    private Integer tutorStatus = 0;
    private Integer autoWechat = 0;
    private Integer autoValidateWechat = 1;

}

