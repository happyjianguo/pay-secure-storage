package com.dream.pay.secure.storage.access.facade;

import com.dream.pay.utils.JsonUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author mengzhenbin
 * @Since 2018/1/29
 */
@Data
@Setter
@Getter
public class Encryption implements Serializable {

    @NotNull(message = "加密数据不能为空")
    public String origData;

    public static void main(String args[]) {
        Encryption encryption = new Encryption();
        encryption.setOrigData("19880602");
        System.out.print(JsonUtil.toJson(encryption));
    }
}
