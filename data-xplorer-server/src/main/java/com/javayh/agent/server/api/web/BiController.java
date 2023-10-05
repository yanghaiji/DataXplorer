package com.javayh.agent.server.api.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author haiji
 */
@Controller
public class BiController {


    @GetMapping("/index")
    public String topUrlsPage() {
        return "index";
    }

}

