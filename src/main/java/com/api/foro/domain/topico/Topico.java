package com.api.foro.domain.topico;

import com.api.foro.domain.curso.Curso;
import com.api.foro.domain.respuesta.Respuesta;
import com.api.foro.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @Column(name = "fecha_creacion")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDate fecha_creacion;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne
    @JoinColumn(name="autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Respuesta> respuesta = new ArrayList<>();

}
