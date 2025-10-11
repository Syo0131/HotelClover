package com.hotelclover.hotelclover.Services.MGestionDeClientes.MNotificacion;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MNotificacion.NotificacionCliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MNotificacion.NotificacionRepository;
import org.springframework.stereotype.Service;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionCliente enviarNotificacion(Cliente cliente, String titulo, String mensaje) {
        NotificacionCliente notificacion = new NotificacionCliente();
        notificacion.setCliente(cliente);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setFechaEnvio(LocalDateTime.now());
        return notificacionRepository.save(notificacion);
    }

    public List<NotificacionCliente> obtenerNoLeidas(Long clienteId) {
        return notificacionRepository.findByClienteIdAndLeidoFalse(clienteId);
    }
}