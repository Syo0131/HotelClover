package com.hotelclover.hotelclover.Models.MGestionDeClientes.MAuditoria;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

//Entidad para trazabilidad del cliente, solo para clientes
@Entity
@Table(name = "auditoria")
@Data
@NoArgsConstructor
public class AuditoriaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Long idAuditoria;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "accion", nullable = false, length = 255)
    private String accion;

    @Column(name = "resultado", nullable = false, length = 255)
    private String resultado;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
}