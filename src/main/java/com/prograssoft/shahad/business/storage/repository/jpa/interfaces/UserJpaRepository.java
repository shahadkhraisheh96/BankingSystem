package com.prograssoft.shahad.business.storage.repository.jpa.interfaces;

import com.prograssoft.shahad.business.storage.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);
    boolean existsByUserName(String username);

    }
