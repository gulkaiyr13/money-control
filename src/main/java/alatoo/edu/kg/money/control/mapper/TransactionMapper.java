package alatoo.edu.kg.money.control.mapper;

import alatoo.edu.kg.money.control.entity.Transaction;
import alatoo.edu.kg.money.control.model.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDTO  toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
}
