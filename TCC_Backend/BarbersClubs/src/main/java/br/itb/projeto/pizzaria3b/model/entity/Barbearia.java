package br.itb.projeto.pizzaria3b.model.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Barbearia")
public class Barbearia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column
    private LocalTime horarioAbertura;

    @Column
    private LocalTime horarioFechamento;

    @Column(length = 100)
    private String diasFuncionamento;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String fotoBarbearia;

    @Column
    private Boolean ativo;

    @Column
    private Boolean parceira;

    @Column
    private LocalDateTime dataParceria;

    @Column(length = 20)
    private String planoAssinatura;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalTime getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(LocalTime horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public String getDiasFuncionamento() {
        return diasFuncionamento;
    }

    public void setDiasFuncionamento(String diasFuncionamento) {
        this.diasFuncionamento = diasFuncionamento;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFotoBarbearia() {
        return fotoBarbearia;
    }

    public void setFotoBarbearia(String fotoBarbearia) {
        this.fotoBarbearia = fotoBarbearia;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getParceira() {
        return parceira;
    }

    public void setParceira(Boolean parceira) {
        this.parceira = parceira;
    }

    public LocalDateTime getDataParceria() {
        return dataParceria;
    }

    public void setDataParceria(LocalDateTime dataParceria) {
        this.dataParceria = dataParceria;
    }

    public String getPlanoAssinatura() {
        return planoAssinatura;
    }

    public void setPlanoAssinatura(String planoAssinatura) {
        this.planoAssinatura = planoAssinatura;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}