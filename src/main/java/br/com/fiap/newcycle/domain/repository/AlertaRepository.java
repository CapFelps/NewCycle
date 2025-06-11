package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByPontoColeta_Id(Long pontoColetaId);
}
