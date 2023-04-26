package br.edu.ifsul.tads.aulas_tads;


import br.edu.ifsul.tads.aulas_tads.AulasTadsApplication;
import br.edu.ifsul.tads.aulas_tads.api.entregadores.Entregador;
import br.edu.ifsul.tads.aulas_tads.api.entregadores.EntregadorDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AulasTadsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntregadorControllerTest extends BaseAPITest {

    //Métodos utilitários
    private ResponseEntity<EntregadorDTO> getEntregador(String url) {
        return get(url, EntregadorDTO.class);
    }

    private ResponseEntity<List<EntregadorDTO>> getEntregadores(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<EntregadorDTO>>() {
                });
    }

    @Test
    public void selectAll() {
        List<EntregadorDTO> entregadores = getEntregadores("/api/v1/entregadores").getBody();
        assertNotNull(entregadores);
        assertEquals(5, entregadores.size());

        entregadores = getEntregadores("/api/v1/entregadores?page=0&size=5").getBody();
        assertNotNull(entregadores);
        assertEquals(5, entregadores.size());
    }

    @Test
    public void selectByNome() {

        assertEquals(1, getEntregadores("/api/v1/entregadores/nome/Rubens").getBody().size());
        assertEquals(1, getEntregadores("/api/v1/entregadores/nome/Ayrton").getBody().size());
        assertEquals(1, getEntregadores("/api/v1/entregadores/nome/Felipe Massa").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getEntregadores("/api/v1/entregadores/nome/xxx").getStatusCode());
    }

    @Test
    public void selectById() {

        assertNotNull(getEntregador("/api/v1/entregadores/1"));
        assertNotNull(getEntregador("/api/v1/entregadores/2"));
        assertNotNull(getEntregador("/api/v1/entregadores/3"));

        assertEquals(HttpStatus.NOT_FOUND, getEntregador("/api/v1/entregadores/1000").getStatusCode());
    }

    @Test
    public void testInsert() {

        Entregador entregador = new Entregador();
        entregador.setNome("Piquet");
        entregador.setData_nasc("1960-04-26");
        entregador.setEmail("piquet@email.com");
        entregador.setSenha("123456");
        entregador.setStatus(true);

        // Insert
        ResponseEntity response = post("/api/v1/entregadores", entregador, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        EntregadorDTO ent = getEntregador(location).getBody();

        assertNotNull(ent);
        assertEquals("Piquet", ent.getNome());
        assertEquals("123456", ent.getSenha());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getEntregador(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        //primeiro insere o objeto
        Entregador entregador = new Entregador();
        entregador.setNome("Piquet");
        entregador.setData_nasc("1960-04-26");
        entregador.setEmail("piquet@email.com");
        entregador.setSenha("123456");
        entregador.setStatus(true);

        // Insert
        ResponseEntity response = post("/api/v1/entregadores", entregador, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        EntregadorDTO ent = getEntregador(location).getBody();

        assertNotNull(ent);
        assertEquals("Piquet", ent.getNome());
        assertEquals("123456", ent.getSenha());

        //depois altera seu valor
        Entregador enta = Entregador.create(ent);
        enta.setSenha("123456789");

        // Update
        response = put("/api/v1/entregadores/" + ent.getId(), enta, null);
        System.out.println(response);
        assertEquals("123456789", enta.getSenha());

        // Deletar o objeto
        delete(location, null);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getEntregador(location).getStatusCode());

    }

    @Test
    public void testDelete() {
        this.testInsert();
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getEntregador("/api/v1/entregadores/1100");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}