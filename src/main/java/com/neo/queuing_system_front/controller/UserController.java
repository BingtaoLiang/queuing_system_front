package com.neo.queuing_system_front.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.queuing_system_front.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        if (r == null || r1 == null || r2 == null || r3 == null ||
                r4 == null || r5 == null || r6 == null || r7 == null || r8 == null || r9 == null || r10 == null
                || r11 == null) {
            map.put("msg", "测评表信息填写不完整，请重新填写完整的挂号信息");
            return "register";
        } else {
            Integer score = r + r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9 + r10 + r11 + sco;
            Map mapResult = restTemplate.getForObject(url + "/guahao?username=" + username + "&userAge=" + userAge + "&userSex=" + userSex + "&userPhone=" + userPhone + "&score=" + score, Map.class);
            if (mapResult.containsKey("msg")) {
                //未成功挂号，今天已挂过
                Object msg = mapResult.get("msg");
                model.addAttribute("msg", msg);
                return "index";
            } else {
                //因为mapResult是LinkedHashMap类型，不能直接转为User类，因此需要以下两行代码进行转换
                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.convertValue(mapResult.get("user"), User.class);
                model.addAttribute("user", user);
                return "result";
            }

        }

    }

    /*查询号码*/
    @RequestMapping("/selectUser")
    public String selectUser(@RequestParam("username") String username,
                             @RequestParam("userPhone") String userphone,
                             Model model) {
        Map mapResult = restTemplate.getForObject(url + "/selectUser?username=" + username + "&userPhone=" + userphone, Map.class);
        if (mapResult.containsKey("msg")) { //说明有误(还未挂号或手机号与姓名不匹配)
            Object msg = mapResult.get("msg");
            model.addAttribute("msg", msg);
            return "index";
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.convertValue(mapResult.get("userInfo"), User.class);
            model.addAttribute("user", user);
            return "result";
        }
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
        //已挂号病人列表需要展示出已叫号病人和未叫号病人列表
        Map mapResult = restTemplate.getForObject(url + "/list", Map.class);
        List<User> userList2 = new ArrayList<>();     //用来存放未叫号病人
        List<User> calledUserList2 = new ArrayList<>();   //存放已叫号病人

        /*这里的转换过的userList和calledUserList里存放的仍然是LinkedHashMap类型的而用户，因此还需要再进行一次转换*/
        ObjectMapper objectMapper = new ObjectMapper();
        List userList = objectMapper.convertValue(mapResult.get("userList"), List.class);
        List calledUserList = objectMapper.convertValue(mapResult.get("calledUserList"), List.class);

        for (int i = 0; i < userList.size(); i++) {  //获取未叫号病人名单
            User user = objectMapper.convertValue(userList.get(i), User.class);
            userList2.add(user);
        }
        for (int i = 0; i < calledUserList.size(); i++) {  //获取未叫号病人名单
            User user = objectMapper.convertValue(calledUserList.get(i), User.class);
            calledUserList2.add(user);
        }

        model.addAttribute("userList", userList2);
        model.addAttribute("calledUserList", calledUserList2);
        return "list";
    }

    /*医生模块--叫号*/
    @RequestMapping("/jiaohao")
    public String jiaohao(Model model, Map<String, Object> map) {
        Map mapResult = restTemplate.getForObject(url + "/jiaohao", Map.class);
        if (mapResult.containsKey("msg")) {    //未叫号病人列表为空
            Object msg = mapResult.get("msg");
            model.addAttribute("msg", msg);
            return "doctorIndex";
        } else {  //未叫号病人列表不为空
            Object user_LinkedHashMap = mapResult.get("userInfo");
            /*这里的user是LinkedHashMap类型的，因此需要进行类型转换*/
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.convertValue(user_LinkedHashMap, User.class);
            model.addAttribute("user", user);
            return "jiaohao";
        }
    }

    /*医生模块--下一位*/
    @RequestMapping("/nextUser")
    public String nextUser(Map<String, Object> map) {
        Map userMap = restTemplate.getForObject(url + "/nextUser", Map.class);
        if (userMap.size() != 0) {
            return "redirect:/jiaohao";
        } else {
            map.put("msg", "当前没有挂号病人");
            return "doctorIndex";
        }
    }


    /*管理模块*/
    @RequestMapping("/guanli")
    public String guanli() {
        return "guanli";
    }

    /*管理模块--已就诊病人*/
    @RequestMapping("/calledNumber")
    public String calledNumber(Model model) {
        List<User> calledList = new ArrayList<>();
        Map calledUsersMap = restTemplate.getForObject(url + "/calledNumber", Map.class);
        List<User> calledUsers = (List<User>) calledUsersMap.get("calledUsers");
        //calledUsers中存放的也是LinkedHashMap类型的user，需要转换
        for (int i = 0; i < calledUsers.size(); i++) {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.convertValue(calledUsers.get(i), User.class);
            calledList.add(user);
        }
        model.addAttribute("users", calledList);
        return "calledUsers";
    }

    @RequestMapping("restart")
    public String restart(Model model) {
        Map mapResult = restTemplate.getForObject(url + "/restart", Map.class);
        Object msg = mapResult.get("msg");
        model.addAttribute("msg",msg);
        return "guanli";
    }


}
