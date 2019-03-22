package com.telangel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 返回结果类枚举
 * @author： lid
 * @date： 2019/3/21 17:23
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  Result {

    /**
     * 判断文件在系统的状态
     */
    IS_HAVE(100, "文件已存在！"),

    NO_HAVE(101, "该文件没有上传过。"),

    ING_HAVE(102, "该文件上传了一部分。");

    int code;
    String desc;

    Result(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
