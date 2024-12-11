/*
* Bu Dosyanın Amacı:
* Veritabanı ile İletişim: UserRepository, Spring Data JPA'yı kullanarak User tablosuyla etkileşim kurar.
* Kullanıcı Adı ile Arama: findByUsername metodu, bir kullanıcıyı kullanıcı adı üzerinden sorgulamak için tanımlanmıştır. Bu, giriş işlemlerinde ve kayıt doğrulamalarında kullanılacak
 */

package com.crm-project.repository;

import com.crm-project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
