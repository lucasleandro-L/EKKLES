package com.ekkles.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "membros")
@Getter
@Setter
@NoArgsConstructor
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusMembro status = StatusMembro.ATIVO;

    @Column(name = "receber_notificacoes", nullable = false)
    private boolean receberNotificacoes = true;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
}