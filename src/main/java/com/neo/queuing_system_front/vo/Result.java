package com.neo.queuing_system_front.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description Author neo
 * Date 2020/9/15 14:45
 */
@Data
public class Result<T> implements Serializable {

    /*返回提示码*/
    private Integer code;

    /* 提示信息*/
    private String msg;

    /*  具体数据*/
    private T data;
}
