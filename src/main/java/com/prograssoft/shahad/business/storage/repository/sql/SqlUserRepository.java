package com.prograssoft.shahad.business.storage.repository.sql;

import com.prograssoft.shahad.business.mapper.UserJpaMapper;
import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.storage.entity.UserEntity;
import com.prograssoft.shahad.business.storage.repository.interfaces.UserRepository;
import com.prograssoft.shahad.business.storage.repository.jpa.interfaces.UserJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class SqlUserRepository implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    @PersistenceContext
    private EntityManager em;

    public SqlUserRepository(UserJpaRepository userRepository) {
        this.userJpaRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userJpaRepository.save(UserJpaMapper.toUserEntity(user));
        return UserJpaMapper.toUser(userEntity);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        UserEntity userEntity = userJpaRepository.findByEmailAndPassword(email, password);

        return Optional.ofNullable(UserJpaMapper.toUser(userEntity));
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = userJpaRepository.findByEmail(email);
        return UserJpaMapper.toUser(userEntity);
    }

    @Override
    public boolean existsByUsername(String username) {

        return userJpaRepository.existsByUserName(username);
    }

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userJpaRepository.findByUserName(username);
        return UserJpaMapper.toUser(userEntity);
    }
}
