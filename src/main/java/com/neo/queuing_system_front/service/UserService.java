package com.neo.queuing_system_front.service;

import org.springframework.ui.Model;

import java.util.Map;

/**
 * @Description Author neo
 * Date 2020/11/14 16:50
 */

public interface UserService {

    //挂号
    public String guahao(String username, Integer userAge, String userSex, String userPhone, Integer r, Integer r1,
                         Integer r2, Integer r3, Integer r4, Integer r5,Integer r6,
                         Integer r7,Integer r8,Integer r9,Integer r10,Integer r11,Integer sco,
                         Map<String, Object> map, Model model);

    //查询号码
    public String select(String username,String userPhone,Model model);
}
