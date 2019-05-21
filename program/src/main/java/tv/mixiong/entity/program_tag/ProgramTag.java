package tv.mixiong.entity.program_tag;

import lombok.Data;

@Data
public class ProgramTag {

    private Long id;
    private Long programId;
    private Long tagId;
    private Long createTime;
    private Integer createType;

}
