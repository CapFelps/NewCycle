package br.com.fiap.newcycle.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "residuo_seq")
    @SequenceGenerator(name = "residuo_seq",
            sequenceName = "SEQ_RESIDUO_DEPOSITADO",
            allocationSize = 1)
    private Long id;

    @Column(length = 120)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal pesoKg;

    @Column(nullable = false)
    private LocalDate dataDeposito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_residuo_id", nullable = false)
    private TipoResiduo tipoResiduo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    private PontoColeta pontoColeta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coleta_id")
    private Coleta coleta;

    public Residuo() {}

    public Residuo(String descricao,
                   BigDecimal pesoKg,
                   TipoResiduo tipoResiduo,
                   PontoColeta pontoColeta) {
        this.descricao = descricao;
        this.pesoKg = pesoKg;
        this.tipoResiduo = tipoResiduo;
        this.pontoColeta = pontoColeta;
        this.dataDeposito = LocalDate.now();
    }

    public Long getId() { return id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public LocalDate getDataDeposito() { return dataDeposito; }
    public void setDataDeposito(LocalDate dataDeposito) { this.dataDeposito = dataDeposito; }

    public TipoResiduo getTipoResiduo() { return tipoResiduo; }
    public void setTipoResiduo(TipoResiduo tipoResiduo) { this.tipoResiduo = tipoResiduo; }

    public PontoColeta getPontoColeta() { return pontoColeta; }
    public void setPontoColeta(PontoColeta pontoColeta) { this.pontoColeta = pontoColeta; }

    public Coleta getColeta() { return coleta; }
    public void setColeta(Coleta coleta) { this.coleta = coleta; }
}
