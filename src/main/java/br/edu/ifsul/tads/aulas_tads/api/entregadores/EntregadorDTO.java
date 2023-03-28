package br.edu.ifsul.tads.aulas_tads.api.entregadores;

import lombok.Data;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
public class EntregadorDTO {
    private Long id;
    private String nome;
    private String data_nasc;
    private String email;
    private Boolean status;

    public static EntregadorDTO create(Entregador p){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(p, EntregadorDTO.class);
    }
}
