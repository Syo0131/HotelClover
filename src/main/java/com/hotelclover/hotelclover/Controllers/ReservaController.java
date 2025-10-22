package com.hotelclover.hotelclover.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.hotelclover.hotelclover.Dtos.ReservaDto;

@Controller
public class ReservaController {




    @GetMapping("/")
    public String home()
    {
        return "login";
    }

    
    @GetMapping("/reservas")
    public String agregarReserva(@ModelAttribute ReservaDto reservaDto)
    {
        return "reservas";
    }

    
}
