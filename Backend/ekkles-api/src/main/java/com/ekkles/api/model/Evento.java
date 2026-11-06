package com.ekkles.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(length = 150)
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEvento tipo;

    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEvento status = StatusEvento.AGENDADO;
}