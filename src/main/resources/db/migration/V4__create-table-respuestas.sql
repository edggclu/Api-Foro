create table respuestas
(
    id bigint not null auto_increment,
    mensaje longtext not null,
    topico_id bigint not null,
    fecha_creacion datetime not null,

    autor_id bigint not null,
    solucion boolean,

    primary key (id),

    constraint fk_respuestas_topico_id foreign key (topico_id) references topicos(id)
);