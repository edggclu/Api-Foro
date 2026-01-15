create table topicos
(
    id            bigint       not null auto_increment,
    titulo        varchar(255) not null,
    mensaje       text         not null,
    fecha_creacion datetime     not null,
    status        varchar(100),

    autor_id      bigint       not null,
    curso_id      bigint       not null,

    primary key (id),

    constraint fk_topicos_autor_id foreign key (autor_id) references usuarios(id),
    constraint fk_topicos_curso_id foreign key (curso_id) references cursos(id)
)