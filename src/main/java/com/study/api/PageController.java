package com.study.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("message", "dynamic message");
        return "index";
    }
}
