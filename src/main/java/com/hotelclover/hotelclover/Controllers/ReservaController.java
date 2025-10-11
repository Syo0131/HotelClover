package com.hotelclover.hotelclover.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservaController {




    @GetMapping("/")
    public String home()
    {
        return "login";
    }
    
}
