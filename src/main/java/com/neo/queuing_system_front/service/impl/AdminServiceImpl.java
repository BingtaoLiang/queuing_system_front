package com.neo.queuing_system_front.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.queuing_system_front.vo.Result;
import com.neo.queuing_system_front.model.User;
import com.neo.queuing_system_front.service.AdminService;
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
 * Date 2020/11/14 20:43
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.provider.url}")
    private String url;

    @Override
    public String calledNumber(Model model) {
        List<User> calledList = new ArrayList<>();
        Result calledUsersMap = restTemplate.getForObject(url + "/calledNumber", Result.class);
        Map<String,Object> data = (Map<String, Object>) calledUsersMap.getData();

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> calledUsers = (List<User>) data.get("calledUsers");
        //calledUsers中存放的也是LinkedHashMap类型的user，需要转换
        for (int i = 0; i < calledUsers.size(); i++) {
            User user = objectMapper.convertValue(calledUsers.get(i), User.class);
            calledList.add(user);
        }
        model.addAttribute("users", calledList);
        return "calledUsers";
    }

    @Override
    public String restart(Model model) {
        Result mapResult = restTemplate.getForObject(url + "/restart", Result.class);
        Map<String,Object> resultData = (Map<String, Object>) mapResult.getData();
        model.addAttribute("msg",  resultData.get("msg"));
        return "guanli";
    }
}
