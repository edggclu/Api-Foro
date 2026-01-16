package com.api.foro.domain.topico;

import com.api.foro.domain.curso.Curso;
import com.api.foro.domain.respuesta.Respuesta;
import com.api.foro.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @Column(name = "fecha_creacion")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Respuesta> respuesta = new ArrayList<>();

    public List<Respuesta> getRespuesta() {
        if (respuesta == null) {
            respuesta = new ArrayList<>();
        }
        return respuesta;
    }

    public void cambiarEstatusTopico(StatusTopico statusTopico) {
        this.status = statusTopico;
    }
}
