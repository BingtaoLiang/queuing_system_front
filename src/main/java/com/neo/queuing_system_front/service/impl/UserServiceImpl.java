package com.neo.queuing_system_front.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.queuing_system_front.vo.Result;
import com.neo.queuing_system_front.model.User;
import com.neo.queuing_system_front.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description Author neo
 * Date 2020/11/14 16:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.provider.url}")
    private String url;

    //挂号
    @Override
    public String guahao(String username, Integer userAge, String userSex, String userPhone, Integer r, Integer r1, Integer r2, Integer r3, Integer r4, Integer r5, Integer r6, Integer r7, Integer r8, Integer r9, Integer r10, Integer r11, Integer sco, Map<String, Object> map, Model model) {
        if (r == null || r1 == null || r2 == null || r3 == null ||
                r4 == null || r5 == null || r6 == null || r7 == null || r8 == null || r9 == null || r10 == null
                || r11 == null) {
            map.put("msg", "测评表信息填写不完整，请重新填写完整的挂号信息");
            return "register";
        } else {
            Integer score = r + r1 + r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9 + r10 + r11 + sco;
            Result mapResult = restTemplate.getForObject(url + "/guahao?username=" + username + "&userAge=" + userAge + "&userSex=" + userSex + "&userPhone=" + userPhone + "&score=" + score, Result.class);
            if (mapResult.getCode() != 200) {
                //未成功挂号，今天已挂过
                Object msg = mapResult.getMsg();
                model.addAttribute("msg", msg);
                return "index";
            } else {
                //因为mapResult是LinkedHashMap类型，不能直接转为User类，因此需要以下两行代码进行转换
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                Map<String,Object> linkedUser = (Map<String, Object>) mapResult.getData();
                User user = objectMapper.convertValue(linkedUser.get("user"), User.class);
                model.addAttribute("user", user);
                return "result";
            }

        }
    }

    //查号
    @Override
    public String select(String username, String userPhone, Model model) {
        Result mapResult = restTemplate.getForObject(url + "/selectUser?username=" + username + "&userPhone=" + userPhone, Result.class);
        if (mapResult.getCode() != 200) { //说明有误(还未挂号或手机号与姓名不匹配)
            Object msg = mapResult.getMsg();
            model.addAttribute("msg", msg);
            return "index";
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            Map<String,Object> linkedUser= (Map<String, Object>) mapResult.getData();
            User user = objectMapper.convertValue(linkedUser.get("userInfo"), User.class);
            model.addAttribute("user", user);
            return "result";
        }
    }


}
