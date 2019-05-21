package tv.mixiong.entity.tag;

import lombok.Data;

@Data
public class Tag {
    private Long id;
    private String tag;
    private int type;
    private String passport;
    private Long createTime;
    private Long updateTime;

}
