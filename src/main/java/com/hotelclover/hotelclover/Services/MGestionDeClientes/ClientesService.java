package com.hotelclover.hotelclover.Services.MGestionDeClientes;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Cliente;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepository;

    public Cliente createClient(Cliente client) {
        return clientesRepository.save(client);
    }

    public Optional<Cliente> getClientById(Long id) {
        return clientesRepository.findById(id);
    }

    public Iterable<Cliente> getAllClients() {
        return clientesRepository.findAll();
    }

    public void deleteClient(Long id) {
        clientesRepository.deleteById(id);
    }

    public Cliente updateClient(Long id, Cliente updatedClient) {
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
