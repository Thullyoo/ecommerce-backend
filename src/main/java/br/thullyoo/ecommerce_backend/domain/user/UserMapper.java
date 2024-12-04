package br.thullyoo.ecommerce_backend.domain.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "date_of_birth", target = "date_of_birth")
    @Mapping(source = "document", target = "document")
    User toUser(UserRequest userRequest);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "date_of_birth", target = "date_of_birth")
    @Mapping(source = "document", target = "document")
    UserGetResponse toUserGetResponse(User user);

}
