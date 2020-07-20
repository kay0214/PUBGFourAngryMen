/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sunpeikai
 * @version IndexController, v0.1 2020/7/10 13:44
 * @description
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 对局详情
     */
    @GetMapping("/match")
    public String match(String matchId,String playerName,Model model) {
        model.addAttribute("matchId",matchId);
        model.addAttribute("playerName",playerName);
        return "match";
    }

    /**
     * 追踪页面
     */
    @GetMapping("/track")
    public String track(String matchId,Model model) {
        model.addAttribute("matchId",matchId);
        return "track";
    }
}
