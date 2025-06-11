package br.com.fiap.newcycle.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ponto_coleta_seq")
    @SequenceGenerator(name = "ponto_coleta_seq",
            sequenceName = "SEQ_PONTO_COLETA",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    @Column(nullable = false, length = 150)
    private String localizacao;

    @Column(name = "LIMITE_PESO_KG", precision = 10, scale = 2, nullable = false)
    private BigDecimal limitePesoKg;

    @Column(name = "TOTAL_RESIDUO_KG", precision = 10, scale = 2)
    private BigDecimal totalResiduoKg = BigDecimal.ZERO;

    @Column(name = "TOTAL_COLETADO_KG", precision = 10, scale = 2)
    private BigDecimal totalColetadoKg = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pontoColeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Residuo> residuos = new ArrayList<>();

    @OneToMany(mappedBy = "pontoColeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerta> alertas = new ArrayList<>();

    @OneToMany(mappedBy = "pontoColeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coleta> coletas = new ArrayList<>();

    public PontoColeta() {}

    public PontoColeta(String nome, String localizacao, BigDecimal limitePesoKg) {
        this.nome          = nome;
        this.localizacao   = localizacao;
        this.limitePesoKg  = limitePesoKg;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public BigDecimal getLimitePesoKg() { return limitePesoKg; }
    public void setLimitePesoKg(BigDecimal limitePesoKg) { this.limitePesoKg = limitePesoKg; }

    public BigDecimal getTotalResiduoKg() { return totalResiduoKg; }
    public void setTotalResiduoKg(BigDecimal totalResiduoKg) { this.totalResiduoKg = totalResiduoKg; }

    public BigDecimal getTotalColetadoKg() { return totalColetadoKg; }
    public void setTotalColetadoKg(BigDecimal totalColetadoKg) { this.totalColetadoKg = totalColetadoKg; }

    public List<Residuo> getResiduos() { return residuos; }
    public void setResiduos(List<Residuo> residuos) { this.residuos = residuos; }

    public List<Alerta> getAlertas() { return alertas; }
    public void setAlertas(List<Alerta> alertas) { this.alertas = alertas; }

    public List<Coleta> getColetas() { return coletas; }
    public void setColetas(List<Coleta> coletas) { this.coletas = coletas; }

    public void setId(Long id) {
        this.id = id;
    }

}
