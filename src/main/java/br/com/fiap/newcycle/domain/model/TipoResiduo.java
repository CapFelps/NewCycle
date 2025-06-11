package br.com.fiap.newcycle.domain.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TipoResiduo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_residuo_seq")
    @SequenceGenerator(name = "tipo_residuo_seq",
            sequenceName = "SEQ_TIPO_RESIDUO",
            allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(length = 150)
    private String descricao;

    @Column(nullable = false)
    private Boolean perigoso = false;

    @OneToMany(mappedBy = "tipoResiduo",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Residuo> residuos = new ArrayList<>();

    public TipoResiduo() {}

    public TipoResiduo(String nome, String descricao, Boolean perigoso) {
        this.nome = nome;
        this.descricao = descricao;
        this.perigoso = perigoso;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean getPerigoso() { return perigoso; }
    public void setPerigoso(Boolean perigoso) { this.perigoso = perigoso; }

    public List<Residuo> getResiduos() { return residuos; }
    public void setResiduos(List<Residuo> residuos) { this.residuos = residuos; }

    public void setId(Long id) {
        this.id = id;
    }

}
