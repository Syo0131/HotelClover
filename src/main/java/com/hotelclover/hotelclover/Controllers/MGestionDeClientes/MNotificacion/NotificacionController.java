package com.hotelclover.hotelclover.Controllers.MGestionDeClientes.MNotificacion;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MNotificacion.NotificacionCliente;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.MNotificacion.NotificacionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<NotificacionCliente>> obtenerNoLeidas(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerNoLeidas(id));
    }
}
