package br.com.fiap.newcycle.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coleta_seq")
    @SequenceGenerator(name = "coleta_seq", sequenceName = "SEQ_COLETA", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataAgendada;

    private LocalDate dataRealizada;

    @Column(nullable = false)
    private String status; // Agendada, Realizada, Atrasada

    @Column(nullable = false)
    private String frequencia; // Semanal, Quinzenal, Mensal, ou "Nenhuma"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    private PontoColeta pontoColeta;

    @OneToMany(mappedBy = "coleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Residuo> residuosColetados;

    public Coleta() {
    }

    public Coleta(LocalDate dataAgendada, String status, String frequencia, PontoColeta pontoColeta) {
        this.dataAgendada = dataAgendada;
        this.status = status;
        this.frequencia = frequencia;
        this.pontoColeta = pontoColeta;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public LocalDate getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(LocalDate dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public PontoColeta getPontoColeta() {
        return pontoColeta;
    }

    public void setPontoColeta(PontoColeta pontoColeta) {
        this.pontoColeta = pontoColeta;
    }

    public List<Residuo> getResiduosColetados() {
        return residuosColetados;
    }

    public void setResiduosColetados(List<Residuo> residuosColetados) {
        this.residuosColetados = residuosColetados;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
