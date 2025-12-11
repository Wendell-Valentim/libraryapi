package io.github.wendellvalentim.libraryapi.Service;

import io.github.wendellvalentim.libraryapi.model.Client;
import io.github.wendellvalentim.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Client salvar (Client client) {
        return repository.save(client);
    }

    public Client obterPorClientID(String clientId) {
        return repository.findByClientId(clientId);

    }
}
