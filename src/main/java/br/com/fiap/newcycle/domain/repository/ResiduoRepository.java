package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

    List<Residuo> findByPontoColeta_Id(Long pontoColetaId);

    List<Residuo> findByTipoResiduo_Nome(String nomeTipo);

    List<Residuo> findByColetaIsNull();
}
