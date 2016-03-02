package com.womai.platform.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wlb on 2015/10/23.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/index", method = { RequestMethod.GET,RequestMethod.POST})
    public String index() {
        return "index";
    }
}
