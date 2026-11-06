package com.ekkles.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcoes")
@Getter
@Setter
@NoArgsConstructor
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;
}