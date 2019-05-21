package tv.mixiong.dao.preview;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tv.mixiong.dao.CustomizedMapper;
import tv.mixiong.entity.preview.Preview;

@Repository
public interface PreviewDao extends CustomizedMapper<Preview> {

    @Insert("")
//    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="id")
    void savePreview (Preview preview);

    @Update("")
    void updatePreview();

    @Select("")
    Preview selectPreview();

}
