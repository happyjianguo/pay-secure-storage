package com.dream.pay.secure.storage.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mengzhenbin
 * @since 2018-01-01
 * 密文数据存储表
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EncryptData implements Serializable {
    private static final long serialVersionUID = -2272222363659626869L;
    //id号
    private Long id;
    //加密索引号
    private String encryptIndex;
    //数据密文
    private String encryptData;
    //工作密钥密文
    private String dataKeyEncrypt;
    //加密密钥对版本号
    private String version;
    //数据插入日期
    private Date createTime;
    //数据最后更新时间
    private Date updateTime;
}
