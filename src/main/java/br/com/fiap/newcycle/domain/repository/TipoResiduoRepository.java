package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.TipoResiduo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoResiduoRepository extends JpaRepository<TipoResiduo, Long> {

    Optional<TipoResiduo> findByNome(String nome);
}
