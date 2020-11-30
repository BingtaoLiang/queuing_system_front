package com.neo.queuing_system_front.controller;

import com.neo.queuing_system_front.service.AdminService;
import com.neo.queuing_system_front.service.DoctorService;
import com.neo.queuing_system_front.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description Author neo
 * Date 2020/11/6 10:00
 */
@Controller
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.provider.url}")
    private String url;

    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AdminService adminService;


    /*用户模块首页*/
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /*挂号页面*/
    @RequestMapping("/register")
    public String userInfo() {
        return "register";
    }

    @RequestMapping(value = "/guahao", method = RequestMethod.POST)
    public String guahao(@RequestParam("username") String username,
                         @RequestParam("userAge") Integer userAge,
                         @RequestParam("userSex") String userSex,
                         @RequestParam("userPhone") String userPhone,
                         @RequestParam(value = "r", required = false) Integer r,
                         @RequestParam(value = "r1", required = false) Integer r1,
                         @RequestParam(value = "r2", required = false) Integer r2,
                         @RequestParam(value = "r3", required = false) Integer r3,
                         @RequestParam(value = "r4", required = false) Integer r4,
                         @RequestParam(value = "r5", required = false) Integer r5,
                         @RequestParam(value = "r6", required = false) Integer r6,
                         @RequestParam(value = "r7", required = false) Integer r7,
                         @RequestParam(value = "r8", required = false) Integer r8,
                         @RequestParam(value = "r9", required = false) Integer r9,
                         @RequestParam(value = "r10", required = false) Integer r10,
                         @RequestParam(value = "r11", required = false) Integer r11,
                         @RequestParam(value = "sco", required = false) Integer sco,
                         Map<String, Object> map,
                         Model model) {
        String result = userService.guahao(username, userAge, userSex, userPhone, r, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, sco, map, model);
        return result;

    }

    /*查询号码*/
    @RequestMapping("/selectUser")
    public String selectUser(@RequestParam("username") String username,
                             @RequestParam("userPhone") String userpPhone,
                             Model model) {
        String result = userService.select(username, userpPhone, model);
        return result;

    }




    /*查询页面*/
    @RequestMapping("/select")
    public String select() {
        return "select";
    }


    /*医生模块首页*/
    @RequestMapping("/doctor")
    public String admin() {
        return "doctorIndex";
    }


    /*已挂号病人列表*/
    @RequestMapping("/list")
    public String list(Model model) {
        String list = doctorService.list(model);
        return list;
    }

    /*医生模块--叫号*/
    @RequestMapping("/jiaohao")
    public String jiaohao(Model model, Map<String, Object> map) {
        String jiaohao = doctorService.jiaohao(model);
        return jiaohao;
    }

    /*医生模块--下一位*/
    @RequestMapping("/nextUser")
    public String nextUser(Map<String, Object> map) {
        String result = doctorService.nextUser(map);
        return result;
    }


    /*管理模块*/
    @RequestMapping("/guanli")
    public String guanli() {
        return "guanli";
    }

    /*管理模块--已就诊病人*/
    @RequestMapping("/calledNumber")
    public String calledNumber(Model model) {
        String result = adminService.calledNumber(model);
        return result;
    }

    @RequestMapping("restart")
    public String restart(Model model) {
        String restart = adminService.restart(model);
        return restart;
    }

}
