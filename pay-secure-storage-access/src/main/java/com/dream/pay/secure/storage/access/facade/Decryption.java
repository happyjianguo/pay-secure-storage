package com.dream.pay.secure.storage.access.facade;

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
public class Decryption implements Serializable {

    @NotNull(message = "解密索引不能为空")
    public String encryptIndex;

    public  static void main(String args[]){
        Decryption decryption = new Decryption();
        decryption.setEncryptIndex("111111111");
    }
}
