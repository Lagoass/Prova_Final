package com.examplo.prova_zambom.Feedbacks.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "feedbacks")
@Getter
@Setter
public class Feedback {
    @MongoId
    private String id;
    private String titulo;
    private String conteudo;
    private String autor;
}