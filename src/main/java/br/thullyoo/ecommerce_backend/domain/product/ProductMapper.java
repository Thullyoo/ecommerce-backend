package br.thullyoo.ecommerce_backend.domain.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "value", target = "value")
    @Mapping(source = "url_image", target = "url_image")
    @Mapping(source = "quantity", target = "quantity")
    Product toProduct(ProductRequest productRequest);

}
