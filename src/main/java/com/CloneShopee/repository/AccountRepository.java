package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT p.id FROM Account p WHERE p.email =:email AND p.password = :password", nativeQuery = true)
    Optional<Integer> getUserByEmailAndPasswrod(@Param("email") String email, @Param("password") String password);

    @Query("select new com.CloneShopee.models.Account(p.id,p.fullName,p.phoneNumber) from Account p where p.id in:ids")
    List<Account> getInfoAccountInIds(@Param("ids") Set<Integer> accountIds);

}
