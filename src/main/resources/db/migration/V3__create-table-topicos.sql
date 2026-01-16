create table topicos
(
    id            bigint       not null auto_increment,
    titulo        varchar(255) not null,
    mensaje       text         not null,
    fecha_creacion date     not null,
    status        varchar(100),

    autor_id      bigint       not null,
    curso_id      bigint       not null,

    primary key (id)
)