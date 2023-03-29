package br.edu.ifsul.tads.aulas_tads.api.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByLogin(String login);
}
