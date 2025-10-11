package com.hotelclover.hotelclover.Services.MGestionDeClientes.MNotificacion;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MNotificacion.NotificacionCliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MNotificacion.NotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

        @Autowired
    private NotificacionRepository notificacionRepository;

    public List<NotificacionCliente> getNotificationsByClient(Long clientId) {
        return notificacionRepository.findByClientId(clientId);
    }

    public List<NotificacionCliente> getUnreadNotificationsByClient(Long clientId) {
        return notificacionRepository.findByClientIdAndReadFalse(clientId);
    }

    public NotificacionCliente saveNotification(NotificacionCliente notification) {
        return notificacionRepository.save(notification);
    }

    public Optional<NotificacionCliente> findNotificationById(Long id) {
        return notificacionRepository.findById(id);
    }

    public void deleteNotification(Long id) {
        notificacionRepository.deleteById(id);
    }
}