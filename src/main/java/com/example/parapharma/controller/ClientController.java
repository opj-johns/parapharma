package com.example.parapharma.controller;

import com.example.parapharma.domain.Client;
import com.example.parapharma.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @RequestMapping("/all")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> clients = this.clientService.fetchClients();
        return new ResponseEntity<>(clients, HttpStatus.OK );
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long clientId){
        Client fetchedClient = this.clientService.getClient(clientId);
        return ResponseEntity.ok(fetchedClient);
    }

    @RequestMapping("/save")
    public ResponseEntity<Client> addNewClient(@RequestBody Client newClient){
        Client client = this.clientService.saveClient(newClient);
        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deleteClient(@RequestBody Client client){
        this.clientService.deleteClient(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
