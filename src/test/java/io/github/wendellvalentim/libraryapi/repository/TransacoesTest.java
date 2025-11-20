package io.github.wendellvalentim.libraryapi.repository;

import io.github.wendellvalentim.libraryapi.Service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacaoSimples(){
        transacaoService.executar();
    }
}
