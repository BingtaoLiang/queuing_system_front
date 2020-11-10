package com.neo.queuing_system_front.model;

import lombok.Data;

import java.io.Serializable;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String username;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 电话
     */
    private String userPhone;

    /**
     * 是否被叫过号，0为未叫过，-1为已叫过
     */
    private Integer flag;

    /**
     * 病人的号码
     */
    private Integer xuhao;

    /**
     * 病人的自评得分
     */
    private Integer score;

}