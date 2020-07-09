package com.zoltwagner.myPage.Contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value={"/robots.txt", "/robot.txt"})
@ResponseBody
public class RobotsController {
    public String getRobotsTxt() {
        return "User-agent: *\n" +
                "Disallow: /\n";
    }
}

