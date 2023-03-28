package br.edu.ifsul.tads.aulas_tads.api.produtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> selectAll() {
        return ResponseEntity.ok(service.getProdutos());
    }
    @GetMapping("{id}")
    public ResponseEntity<ProdutoDTO> selectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getProdutoById(id));
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoDTO>> selectByNome(@PathVariable("nome") String nome) {
        List<ProdutoDTO> produtos = service.getProdutosByNome(nome);
        return produtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(produtos);
    }
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<String> insert(@RequestBody Produto produto){
        ProdutoDTO p = service.insert(produto);
        URI location = getUri(p.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("id") Long id, @RequestBody Produto produto){
        produto.setId(id);
        ProdutoDTO p = service.update(produto, id);
        return p != null ?
                ResponseEntity.ok(p) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
