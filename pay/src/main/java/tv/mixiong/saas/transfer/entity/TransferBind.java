package tv.mixiong.saas.transfer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferBind {
    int id;
    String passport;
    String transferMethod;
    String thirdId;
    String thirdName;
    Date updateTime;

}
