package com.hotelclover.hotelclover.Models.MGestionDeClientes.MNotificacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
public class NotificacionCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion") 
    private Long idNotificacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "mensaje", nullable = false, length = 1000)
    private String mensaje;

    @Column(name = "leido", nullable = false)
    private boolean leido = false;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}