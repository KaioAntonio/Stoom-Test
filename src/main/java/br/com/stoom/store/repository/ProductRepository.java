package br.com.stoom.store.repository;

import br.com.stoom.store.model.enums.Status;
import br.com.stoom.store.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    Page<Product> findAllByStatus(@Param("status") Status status, Pageable pageable);


}