package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.Destinacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinacaoRepository extends JpaRepository<Destinacao, Long> {

    List<Destinacao> findByPontoDestino_Id(Long pontoDestinoId);
}
