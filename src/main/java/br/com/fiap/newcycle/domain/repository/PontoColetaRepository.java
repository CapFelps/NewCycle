package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.PontoColeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long> {

    Optional<PontoColeta> findByNome(String nome);
}
