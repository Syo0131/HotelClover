package com.hotelclover.hotelclover.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    @GetMapping("/api/clientes/login")
    public String mostrarLogin() {
        return "login";
    }
}