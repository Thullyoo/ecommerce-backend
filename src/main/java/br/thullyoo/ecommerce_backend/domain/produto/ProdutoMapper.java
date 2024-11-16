package br.thullyoo.ecommerce_backend.domain.produto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toProduto(ProdutoRequest produtoRequest);

}
