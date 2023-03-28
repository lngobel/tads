package br.edu.ifsul.tads.aulas_tads.api.entregadores;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Entregador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String data_nasc;
    private String email;
    private String senha;
    private Boolean status;
}
