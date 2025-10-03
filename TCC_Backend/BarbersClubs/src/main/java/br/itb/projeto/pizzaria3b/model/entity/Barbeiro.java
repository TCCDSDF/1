package br.itb.projeto.pizzaria3b.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Barbeiro")
public class Barbeiro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "senha")
    private String senha;
    
    @Column(name = "biografia")
    private String biografia;
    
    @Column(name = "especialidades")
    private String especialidades;
    
    @Column(name = "tempoExperiencia")
    private Integer tempoExperiencia;
    
    @Column(name = "mediaAvaliacao")
    private Double mediaAvaliacao;
    
    @Column(name = "disponibilidade")
    private Boolean disponibilidade;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    
    public String getEspecialidades() { return especialidades; }
    public void setEspecialidades(String especialidades) { this.especialidades = especialidades; }
    
    public Integer getTempoExperiencia() { return tempoExperiencia; }
    public void setTempoExperiencia(Integer tempoExperiencia) { this.tempoExperiencia = tempoExperiencia; }
    
    public Double getMediaAvaliacao() { return mediaAvaliacao; }
    public void setMediaAvaliacao(Double mediaAvaliacao) { this.mediaAvaliacao = mediaAvaliacao; }
    
    public Boolean getDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(Boolean disponibilidade) { this.disponibilidade = disponibilidade; }
}