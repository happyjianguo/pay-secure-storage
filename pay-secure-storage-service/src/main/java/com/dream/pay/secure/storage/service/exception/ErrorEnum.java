package com.dream.pay.secure.storage.service.exception;

/**
 * 错误码枚举
 * Created by mengzhenbin on 16/6/27.
 */

public enum ErrorEnum {

    SUCCESS("S0000000", "受理成功"),

    /**
     * 基础部分错误码
     */
    SYSTEM_ERROR("E1000000", "系统内部错误"),
    UN_KNOW_EXCEPTION("E1000002", "未知异常"),
    ARGUMENT_INVALID("E1000003", "参数异常"),

    ;

    private final String code;
    private final String description;

    /**
     * 私有构造函数。
     *
     * @param code        code
     * @param description 描述
     */
    ErrorEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return Returns the name.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code code
     * @return 返回错误枚举
     */
    public static ErrorEnum getByCode(String code) {
        for (ErrorEnum errorEnum : values()) {
            if (errorEnum.getCode().equals(code)) {
                return errorEnum;
            }
        }
        return null;
    }

}
