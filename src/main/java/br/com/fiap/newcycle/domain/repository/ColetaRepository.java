package br.com.fiap.newcycle.domain.repository;

import br.com.fiap.newcycle.domain.model.Coleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ColetaRepository extends JpaRepository<Coleta, Long> {

    List<Coleta> findByPontoColeta_IdAndStatus(Long pontoColetaId, String status);

    List<Coleta> findByDataAgendadaBeforeAndDataRealizadaIsNull(LocalDate hoje);
}
