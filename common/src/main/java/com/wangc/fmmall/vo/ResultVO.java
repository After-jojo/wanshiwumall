package com.wangc.fmmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "ResultVO对象", description = "封装接口返回给前端的数据")
public class ResultVO implements Serializable {
    @ApiModelProperty(value = "响应状态码" , dataType = "int")
    private int code;

    @ApiModelProperty(value = "响应提示信息")
    private String msg;

    @ApiModelProperty("响应数据")
    private Object Data;


    public ResultVO() {
    }

    public ResultVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.Data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return Data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
