package br.edu.ifsul.tads.aulas_tads.api.entregadores;

import br.edu.ifsul.tads.aulas_tads.api.infra.security.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository rep;

    public List<EntregadorDTO> getEntregadores() {
        return rep.findAll().stream().map(EntregadorDTO::create).collect(Collectors.toList());
    }

    public EntregadorDTO getEntregadorById(Long id) {
        Optional<Entregador> entregador = rep.findById(id);
        return entregador.map(EntregadorDTO::create).orElseThrow(() -> new ObjectNotFoundException("Entregador não encontrado"));
    }

    public List<EntregadorDTO> getEntregadoresByNome(String nome) {
        return rep.findByNome(nome).stream().map(EntregadorDTO::create).collect(Collectors.toList());
    }

    public EntregadorDTO insert(Entregador entregador) {
        Assert.isNull(entregador.getId(),"Não foi possível inserir o registro");

        return EntregadorDTO.create(rep.save(entregador));
    }

    public EntregadorDTO update(Entregador entregador, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o entregador no banco de dados
        Optional<Entregador> optional = rep.findById(id);
        if(optional.isPresent()) {
            Entregador db = optional.get();
            // Copiar as propriedades
            db.setNome(entregador.getNome());
            db.setData_nasc(entregador.getData_nasc());
            db.setEmail(entregador .getEmail());
            db.setSenha(entregador.getSenha());
            db.setStatus(entregador.getStatus());
            System.out.println("Entregador id " + db.getId());

            // Atualiza o entregador
            rep.save(db);

            return EntregadorDTO.create(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}

