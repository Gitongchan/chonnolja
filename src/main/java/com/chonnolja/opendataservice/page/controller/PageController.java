package com.chonnolja.opendataservice.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index(){
        return "pages/index";
    }
    @GetMapping("/index")
    public String testindex(){
        return "pages/index";
    }

    @GetMapping("/register")
    public String register(){
        return "pages/register";
    }

    @GetMapping("/pages/login")
    public String login(){
        return "pages/login";
    }

    @GetMapping("/villageCheck")
    public String villCheck(){
        return "pages/villageCheck";
    }

    @GetMapping("/villageinfo")
    public String villinfo(){
        return "pages/product-details";
    }


    @GetMapping("/villagelist")
    public String villlist(){
        return "pages/product-grids";
    }

    @GetMapping("/mapsearch")
    public String searchMap(){
        return "pages/mapSearch";
    }

    @GetMapping("/test/mainfunc")
    public String testmain(){
        return "pages/mainfunction";
    }


}
