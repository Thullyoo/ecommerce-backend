package br.thullyoo.ecommerce_backend.repositories;

import br.thullyoo.ecommerce_backend.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
