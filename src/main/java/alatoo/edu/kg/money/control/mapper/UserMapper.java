package alatoo.edu.kg.money.control.mapper;


import alatoo.edu.kg.money.control.entity.User;
import alatoo.edu.kg.money.control.model.UserDTO;
import alatoo.edu.kg.money.control.model.UserRegistrationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    User toEntity(UserRegistrationDTO userRegistrationDTO);

}
