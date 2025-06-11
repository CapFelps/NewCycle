package br.com.fiap.newcycle.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PontoDestino {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ponto_destino_seq")
    @SequenceGenerator(name = "ponto_destino_seq",
            sequenceName = "SEQ_PONTO_DESTINO",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    @Column(nullable = false, length = 150)
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoDestino tipo;

    private BigDecimal capacidadeMaxKg;

    @OneToMany(mappedBy = "pontoDestino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destinacao> destinacoes = new ArrayList<>();

    public PontoDestino() {}

    public PontoDestino(String nome, String endereco, TipoDestino tipo, BigDecimal capacidadeMaxKg) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.capacidadeMaxKg = capacidadeMaxKg;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public TipoDestino getTipo() { return tipo; }
    public void setTipo(TipoDestino tipo) { this.tipo = tipo; }

    public BigDecimal getCapacidadeMaxKg() { return capacidadeMaxKg; }
    public void setCapacidadeMaxKg(BigDecimal capacidadeMaxKg) { this.capacidadeMaxKg = capacidadeMaxKg; }

    public List<Destinacao> getDestinacoes() { return destinacoes; }
    public void setDestinacoes(List<Destinacao> destinacoes) { this.destinacoes = destinacoes; }

    public enum TipoDestino {
        RECICLAGEM,
        ATERRO,
        INCINERACAO,
        COMPOSTAGEM,
        OUTRO
    }
}
