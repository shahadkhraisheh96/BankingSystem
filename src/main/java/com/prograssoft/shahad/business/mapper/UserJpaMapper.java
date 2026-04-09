package com.prograssoft.shahad.business.mapper;

import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.storage.entity.UserEntity;

public class UserJpaMapper {
    public static UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setUserName(user.getUserName());
        return userEntity;
    }
    public static User toUser(UserEntity userEntity) {
        return User.builder().id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .userName(userEntity.getUserName())
                .build();
    }
}
