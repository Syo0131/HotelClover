package com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MNotificacion;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MNotificacion.NotificacionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<NotificacionCliente, Long> {
    
    List<NotificacionCliente> findByClienteId(Long clienteId);

    List<NotificacionCliente> findByClienteIdAndLeidoFalse(Long clienteId);
}
