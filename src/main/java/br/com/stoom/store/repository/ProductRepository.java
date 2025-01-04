package br.com.stoom.store.repository;

import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Page<Product> findAllByStatus(Status status, Pageable pageable);


}