package tv.mixiong.saas.transfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResult {


    int result;
    int status;// look for transferStatus
    int amount;
    String passport;
    String description;
    String transfer_method;


}
