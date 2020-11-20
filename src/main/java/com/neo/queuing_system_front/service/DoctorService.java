package com.neo.queuing_system_front.service;

import org.springframework.ui.Model;

import java.util.Map;

/**
 * @Description Author neo
 * Date 2020/11/14 19:16
 */
public interface DoctorService {

    //已挂号用户列表
    public String list(Model model);

    //叫号
    public String jiaohao(Model model);

    //下一位
    public String nextUser(Map<String, Object> map);
}
