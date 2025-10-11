package com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MAuditoria;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MAuditoria.AuditoriaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<AuditoriaCliente, Long> {
}