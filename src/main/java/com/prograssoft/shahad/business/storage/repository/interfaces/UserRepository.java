package com.prograssoft.shahad.business.storage.repository.interfaces;

import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.storage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
