package tv.mixiong.entity.program_image;

import lombok.Data;

/**
 * 场次课件bean
 *
 */
@Data
public class ProgramImage {

    private Long id;
    private Long programId; // 场次id
    private Integer idx; // 顺序
    private Long createTime;
    private Long updateTime;
    private String url;

}
