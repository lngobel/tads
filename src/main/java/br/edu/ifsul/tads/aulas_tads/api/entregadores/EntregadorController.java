package br.edu.ifsul.tads.aulas_tads.api.entregadores;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/entregadores")
@Api(value = "Entregadores")
public class EntregadorController {
    @Autowired
    private EntregadorService service;
    @GetMapping
    @ApiOperation(value = "Retorna todos os entregadores cadastrados.")
    public ResponseEntity<List<EntregadorDTO>> selectAll() {
        return ResponseEntity.ok(service.getEntregadores());
    }
    @GetMapping("{id}")
    @ApiOperation(value = "Retorna um entregador pelo campo identificador.")
    public ResponseEntity<EntregadorDTO> selectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getEntregadorById(id));
    }
    @GetMapping("/nome/{nome}")
    @ApiOperation(value = "Retorna uma lista de entregadores pelo nome.")
    public ResponseEntity<List<EntregadorDTO>> selectByNome(@PathVariable("nome") String nome) {
        List<EntregadorDTO> entregadores = service.getEntregadoresByNome(nome);
        return entregadores.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(entregadores);
    }
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    @ApiOperation(value = "Insere um novo entregador.")
    public ResponseEntity<String> insert(@RequestBody Entregador entregador){
        EntregadorDTO p = service.insert(entregador);
        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Altera um entregador existente.")
    public ResponseEntity<EntregadorDTO> update(@PathVariable("id") Long id, @RequestBody Entregador entregador){
        entregador.setId(id);
        EntregadorDTO p = service.update(entregador, id);
        return p != null ?
                ResponseEntity.ok(p) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deleta um entregador.")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
