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
    public String index(Model model){
        model.addAttribute("userName","admin");
        return "index";
    }
}
