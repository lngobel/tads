package br.edu.ifsul.tads.aulas_tads.api.entregadores;

import br.edu.ifsul.tads.aulas_tads.api.produtos.Produto;
import br.edu.ifsul.tads.aulas_tads.api.produtos.ProdutoDTO;
import lombok.Data;
import org.modelmapper.ModelMapper;

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

    public static Entregador create(EntregadorDTO e){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(e, Entregador.class);
    }
}
