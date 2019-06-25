package com.liaozl.demo.storm.service;

import com.liaozl.demo.storm.dto.UserViewPageMsg;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Random;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/6/25 10:35
 */
@Service
public class GetMsgService  implements Serializable {

    public UserViewPageMsg getMsg() {
        UserViewPageMsg msg = new UserViewPageMsg();

        msg.setUserId(new Random().nextInt(5));
        msg.setPageId(new Random().nextInt(5));

        String[] ipArr = {"192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4", "192.168.1.5"};
        msg.setIp(ipArr[new Random().nextInt(5)]);

        return msg;
    }
}
