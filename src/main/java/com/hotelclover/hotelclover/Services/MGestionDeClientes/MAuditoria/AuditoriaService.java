package com.hotelclover.hotelclover.Services.MGestionDeClientes.MAuditoria;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.MAuditoria.AuditoriaCliente;
import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.MAuditoria.AuditoriaRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepo;

    public AuditoriaService(AuditoriaRepository auditoriaRepo) {
        this.auditoriaRepo = auditoriaRepo;
    }

    public void registerAction(Cliente cliente, String accion, String resultado) {
        AuditoriaCliente auditoria = new AuditoriaCliente();
        auditoria.setCliente(cliente);
        auditoria.setAccion(accion);
        auditoria.setResultado(resultado);
        auditoria.setFechaHora(LocalDateTime.now());
        auditoriaRepo.save(auditoria);
    }
}