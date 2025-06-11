package br.com.fiap.newcycle.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Destinacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destinacao_seq")
    @SequenceGenerator(name = "destinacao_seq",
            sequenceName = "SEQ_DESTINACAO",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataEnvio;

    private LocalDate dataRecebimento;

    @Column(name = "PESO_KG", precision = 10, scale = 2, nullable = false)
    private BigDecimal pesoKg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_destino_id", nullable = false)
    private PontoDestino pontoDestino;

    public Destinacao() {}

    public Destinacao(BigDecimal pesoKg, PontoDestino pontoDestino) {
        this.dataEnvio    = LocalDate.now();      // trigger também garante
        this.pesoKg       = pesoKg;
        this.pontoDestino = pontoDestino;
    }

    public Long getId() { return id; }

    public LocalDate getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDate dataEnvio) { this.dataEnvio = dataEnvio; }

    public LocalDate getDataRecebimento() { return dataRecebimento; }
    public void setDataRecebimento(LocalDate dataRecebimento) { this.dataRecebimento = dataRecebimento; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }   // ✅ direto

    public PontoDestino getPontoDestino() { return pontoDestino; }
    public void setPontoDestino(PontoDestino pontoDestino) { this.pontoDestino = pontoDestino; }
}
