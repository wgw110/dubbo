package tv.mixiong.xinge;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 */
public class Payload {
    @JSONField(name = "room_id")
    private long roomId;

    @JSONField(name = "preview_id")
    private long previewId;

//    @JSONField(name = "alert")
//    private String alert;

    @JSONField(name = "passport")
    private String passport;

    @JSONField(name = "avatar")
    private String avatar;

//    @JSONField(name = "title")
//    private String title;

    @JSONField(name = "type")
    private int type;

    @JSONField(name = "program_id")
    private Long programId;

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public Payload() {
    }

    public long getPreviewId() {
        return previewId;
    }

    public void setPreviewId(long previewId) {
        this.previewId = previewId;
    }

    public Payload(long roomId) {
        this.roomId = roomId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }


}
