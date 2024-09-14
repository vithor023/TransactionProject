package com.example.transactionbank.repository;

import com.example.transactionbank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select t from Users t where t.name like %:nome%")
    List<User> searchLikeByName(@Param("nome") String nome);

    @Query("select t from Users t where t.cpf = :doucument")
    Optional<User> searchByDoucument(@Param("doucument") String cpf);
}
