package com.example.parapharma.service;

import com.example.parapharma.domain.Client;
import com.example.parapharma.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<Client> fetchClients(){
        return this.clientRepository.findAll();
    }

    // get client
    public Client getClient(Long clientId){
        return this.clientRepository.getReferenceById(clientId);
    }

    // save client
    public Client saveClient(Client client){
        return this.clientRepository.save(client);
    }

    // delete client
    public void deleteClient(Client client){
        this.clientRepository.delete(client);
    }

}
