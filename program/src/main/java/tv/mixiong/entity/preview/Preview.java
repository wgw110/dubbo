package tv.mixiong.entity.preview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Preview {

    private Long id;
    private String previewUrl;
    private String cover;
    private String passport;
    private Long programId;
    private String convertedUrl;

}
