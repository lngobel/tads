package br.edu.ifsul.tads.aulas_tads.api.entregadores;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntregadorRepository extends JpaRepository<Entregador,Long> {

    List<Entregador> findByNome(String nome);
}
