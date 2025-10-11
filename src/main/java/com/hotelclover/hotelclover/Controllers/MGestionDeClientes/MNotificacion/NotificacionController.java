package com.hotelclover.hotelclover.Controllers.MGestionDeClientes.MNotificacion;


import com.hotelclover.hotelclover.Models.MGestionDeClientes.MNotificacion.NotificacionCliente;
import com.hotelclover.hotelclover.Services.MGestionDeClientes.MNotificacion.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Get all notifications for a client
    @GetMapping("/client/{clientId}")
    public List<NotificacionCliente> getNotificationsByClient(@PathVariable Long clientId) {
        return notificacionService.getNotificationsByClient(clientId);
    }

    // Get unread notifications for a client
    @GetMapping("/client/{clientId}/unread")
    public List<NotificacionCliente> getUnreadNotifications(@PathVariable Long clientId) {
        return notificacionService.getUnreadNotificationsByClient(clientId);
    }

    // Create a new notification
    @PostMapping
    public NotificacionCliente createNotification(@RequestBody NotificacionCliente notification) {
        return notificacionService.saveNotification(notification);
    }

    // Mark a notification as read
    @PutMapping("/{id}/read")
    public NotificacionCliente markNotificationAsRead(@PathVariable Long id) {
        Optional<NotificacionCliente> notificationOpt = notificacionService.findNotificationById(id);
        if (notificationOpt.isPresent()) {
            NotificacionCliente notification = notificationOpt.get();
            notification.setLeido(true);
            return notificacionService.saveNotification(notification);
        } else {
            throw new RuntimeException("Notification not found");
        }
    }

    // Delete a notification
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificacionService.deleteNotification(id);
    }
}