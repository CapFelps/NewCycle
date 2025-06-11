package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.PontoDestino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PontoDestinoRepository extends JpaRepository<PontoDestino, Long> {

    Optional<PontoDestino> findByNome(String nome);
}
