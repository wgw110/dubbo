package tv.mixixiong.im.bean.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonMsg  implements Serializable {
    private String title;

    private String content;

    private String actionUrl;

    @Override
    public String toString() {
        return "CommonMsg{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", actionUrl='" + actionUrl + '\'' +
                '}';
    }

    public static CommonMsg from (String title, String content, String actionUrl) {
        return new CommonMsg(title, content, actionUrl);
    }
}
