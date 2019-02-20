package com.telangel.springbootredis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @version 1.0.0
 * @项目名称： springboot-redis
 * @类名称： SessionController
 * @类描述：
 * @author： lid
 * @date： 2019/2/15 17:43
 */
@RestController
public class SessionController {

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

}
