package br.itb.projeto.pizzaria3b.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MensagensChat")
public class MensagensChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String mensagem;
    private boolean lida;
    private boolean mensagemBot;
    private LocalDateTime iniciadoEm;
    
    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Cliente remetente;
    
    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Cliente destinatario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public boolean isMensagemBot() {
        return mensagemBot;
    }

    public void setMensagemBot(boolean mensagemBot) {
        this.mensagemBot = mensagemBot;
    }

    public LocalDateTime getIniciadoEm() {
        return iniciadoEm;
    }

    public void setIniciadoEm(LocalDateTime iniciadoEm) {
        this.iniciadoEm = iniciadoEm;
    }

    public Cliente getRemetente() {
        return remetente;
    }

    public void setRemetente(Cliente remetente) {
        this.remetente = remetente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }
}