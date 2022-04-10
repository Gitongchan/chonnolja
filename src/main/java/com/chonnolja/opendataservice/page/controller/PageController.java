package com.chonnolja.opendataservice.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index(){
        return "testindex";
    }
    @GetMapping("/joinPage")
    public String join(){
        return "join";
    }
    @GetMapping("/loginPage")
    public String login(){
        return "login";
    }
    @GetMapping("/test/index")
    public String testindex(){
        return "pages/index";
    }


}