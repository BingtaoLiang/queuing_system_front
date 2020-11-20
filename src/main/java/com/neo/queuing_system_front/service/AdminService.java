package com.neo.queuing_system_front.service;

import org.springframework.ui.Model;

import java.util.Map;

/**
 * @Description Author neo
 * Date 2020/11/14 20:42
 */
public interface AdminService {
    //已就诊病人
    public String calledNumber(Model model);

    //重新排号
    public String restart(Model model);
}
