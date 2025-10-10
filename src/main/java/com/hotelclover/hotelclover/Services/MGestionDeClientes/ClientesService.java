package com.hotelclover.hotelclover.Services.MGestionDeClientes;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Clientes;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    public Clientes createClient(Clientes client) {
        return clientesRepository.save(client);
    }

    public Optional<Clientes> getClientById(Long id) {
        return clientesRepository.findById(id);
    }

    public Iterable<Clientes> getAllClients() {
        return clientesRepository.findAll();
    }

    public void deleteClient(Long id) {
        clientesRepository.deleteById(id);
    }

    public Clientes updateClient(Long id, Clientes updatedClient) {
        return clientesRepository.findById(id).map(client -> {
            client.setNombre(updatedClient.getNombre());
            client.setApellido(updatedClient.getApellido());
            client.setEmail(updatedClient.getEmail());
            client.setContrasena(updatedClient.getContrasena());
            client.setTelefono(updatedClient.getTelefono());
            client.setFechaNacimiento(updatedClient.getFechaNacimiento());
            return clientesRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Client not found"));
    }
}
