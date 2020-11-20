package com.neo.queuing_system_front.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.queuing_system_front.vo.Result;
import com.neo.queuing_system_front.model.User;
import com.neo.queuing_system_front.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description Author neo
 * Date 2020/11/14 19:18
 */

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.provider.url}")
    private String url;

    @Override
    public String list(Model model) {
        //已挂号病人列表需要展示出已叫号病人和未叫号病人列表
        Result mapResult = restTemplate.getForObject(url + "/list", Result.class);
        List<User> userList2 = new ArrayList<>();     //用来存放未叫号病人
        List<User> calledUserList2 = new ArrayList<>();   //存放已叫号病人

        Map<String, List<User>> data = (Map<String, List<User>>) mapResult.getData();
        /*这里的转换过的userList和calledUserList里存放的仍然是LinkedHashMap类型的而用户，因此还需要再进行一次转换*/
        ObjectMapper objectMapper = new ObjectMapper();
        List userList = objectMapper.convertValue(data.get("userList"), List.class);
        List calledUserList = objectMapper.convertValue(data.get("calledUserList"), List.class);

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


    //叫号
    @Override
    public String jiaohao(Model model) {
        Result mapResult = restTemplate.getForObject(url + "/jiaohao", Result.class);
        if (mapResult.getCode() != 200) {    //未叫号病人列表为空
            Object msg = mapResult.getMsg();
            model.addAttribute("msg", msg);
            return "doctorIndex";
        } else {  //未叫号病人列表不为空
            Map<String, List<User>> user_LinkedHashMap = (Map<String, List<User>>) mapResult.getData();
            /*这里的user是LinkedHashMap类型的，因此需要进行类型转换*/
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.convertValue(user_LinkedHashMap.get("userInfo"), User.class);
            model.addAttribute("user", user);
            return "jiaohao";
        }
    }

    //下一位
    @Override
    public String nextUser(Map<String, Object> map) {
        Result userMap = restTemplate.getForObject(url + "/nextUser", Result.class);
        List data = (List) userMap.getData();
        if (data.size() != 0) {
            return "redirect:/jiaohao";
        } else {
            map.put("msg", "当前没有挂号病人");
            return "doctorIndex";
        }
    }


}
