package com.Rychlewski.CarteiraDigital.repository;

import com.Rychlewski.CarteiraDigital.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAgenciaAndNumero(String agencia, String numero);

    List<AccountEntity> findByOwnerId(Long ownerId);



}
