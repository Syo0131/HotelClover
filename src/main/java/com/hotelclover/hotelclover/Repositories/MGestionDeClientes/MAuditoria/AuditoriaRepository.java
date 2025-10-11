package com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MAuditoria;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MAuditoria.AuditoriaCliente;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<AuditoriaCliente, Long> {
    
    List<AuditoriaCliente> findByClienteId(Long clienteId);

    List<AuditoriaCliente> findByAccionContaining(String accion);

    List<AuditoriaCliente> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}