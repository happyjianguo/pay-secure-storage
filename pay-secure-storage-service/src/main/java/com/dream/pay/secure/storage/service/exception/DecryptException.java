package com.dream.pay.secure.storage.service.exception;

import lombok.Data;

/**
 * 解密处理异常类。
 * <p>
 * Created by mengzhenbin on 16/7/1.
 */
@Data
public class DecryptException extends BaseException {
    /**
     * 构造器
     */
    public DecryptException() {
    }

    /**
     * 构造器
     *
     * @param errorEnum 错误码枚举
     */
    public DecryptException(ErrorEnum errorEnum) {
        super(errorEnum);
    }

    /**
     * 构造器
     *
     * @param errorEnum    错误码枚举
     * @param errorMessage 错误描述
     */
    public DecryptException(ErrorEnum errorEnum, String errorMessage) {
        super(errorEnum, errorMessage);
    }

    /**
     * 构造器
     *
     * @param errorEnum 错误码枚举
     * @param cause     异常
     */
    public DecryptException(ErrorEnum errorEnum, Throwable cause) {
        super(errorEnum, cause);
    }

    /**
     * 构造器
     *
     * @param errorEnum    错误码枚举
     * @param errorMessage 错误描述
     * @param cause        异常
     */
    public DecryptException(ErrorEnum errorEnum, String errorMessage, Throwable cause) {
        super(errorEnum, errorMessage, cause);
    }
}
