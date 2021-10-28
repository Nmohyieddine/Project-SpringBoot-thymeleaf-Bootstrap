package com.example.cmss_projet.Controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(value = "/login")
    public String Login(){

        return "Login";
    }

    @GetMapping(value = "/")
    public String  home(){
        return "redirect:/login";
    }

}
