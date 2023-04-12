package br.edu.ifsul.tads.aulas_tads;

import br.edu.ifsul.tads.aulas_tads.api.infra.exceptions.ObjectNotFoundException;
import br.edu.ifsul.tads.aulas_tads.api.entregadores.Entregador;
import br.edu.ifsul.tads.aulas_tads.api.entregadores.EntregadorDTO;
import br.edu.ifsul.tads.aulas_tads.api.entregadores.EntregadorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntregadorServiceTest {

    @Autowired
    private EntregadorService service;

    @Test
    public void testGetEntregadores() {
        List<EntregadorDTO> entregadores = service.getEntregadores();
        assertEquals(5, entregadores.size());
    }

    @Test
    public void testGetEntregadorById(){
        EntregadorDTO ent = service.getEntregadorById(1L);
        assertNotNull(ent);
        assertEquals("Valentino Rossi", ent.getNome());
    }

    @Test
    public void getEntregadoresByNome(){
        assertEquals(1, service.getEntregadoresByNome("Felipe Massa").size());
        assertEquals(1, service.getEntregadoresByNome("Rubens").size());
        assertEquals(1, service.getEntregadoresByNome("Ayrton").size());
    }

    @Test
    public void testInsert() {

        //cria o produto para teste
        Entregador entregador = new Entregador();
        entregador.setNome("Teste");
        entregador.setData_nasc("2000-01-01");
        entregador.setEmail("teste@email.com");
        entregador.setSenha("123");
        entregador.setStatus(true);

        //insere o produto na base da dados
        EntregadorDTO ent = service.insert(entregador);

        //se inseriu
        assertNotNull(ent);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = ent.getId();
        assertNotNull(id);
        ent = service.getEntregadorById(id);
        assertNotNull(ent);

        //compara os valores inseridos com os valores pesquisados para confirmar
        assertEquals("Teste", ent.getNome());
        assertEquals("2000-01-01", ent.getData_nasc());
        assertEquals("teste@email.com", ent.getEmail());
        assertEquals("123", ent.getSenha());
        assertEquals(Boolean.TRUE, ent.getStatus());

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        try {
            service.getEntregadorById(id);
            fail("O entregador não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }

    @Test
    public void TestUpdate(){
        EntregadorDTO entDTO = service.getEntregadorById(1L);
        String nome = entDTO.getNome(); //armazena o valor original para voltar na base
        entDTO.setNome("Rossi modificado");
        Entregador ent = Entregador.create(entDTO);

        entDTO = service.update(ent, ent.getId());
        assertNotNull(entDTO);
        assertEquals("Rossi modificado", entDTO.getNome());

        //volta ao valor original
        ent.setNome(nome);
        entDTO = service.update(ent, ent.getId());
        assertNotNull(entDTO);
    }

    @Test
    public void testDelete(){
        //cria o produto para teste
        Entregador entregador = new Entregador();
        entregador.setNome("Teste");
        entregador.setData_nasc("2000-01-01");
        entregador.setEmail("teste@email.com");
        entregador.setSenha("123");
        entregador.setStatus(true);

        //insere o produto na base da dados
        EntregadorDTO ent = service.insert(entregador);

        //se inseriu
        assertNotNull(ent);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = ent.getId();
        assertNotNull(id);
        ent = service.getEntregadorById(id);
        assertNotNull(ent);

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        try {
            service.getEntregadorById(id);
            fail("O entregador não foi excluído");
        } catch (ObjectNotFoundException error) {
            // OK
        }
    }
}
