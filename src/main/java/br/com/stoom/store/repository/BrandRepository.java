package br.com.stoom.store.repository;

import br.com.stoom.store.model.brand.Brand;
import br.com.stoom.store.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String name);

    Page<Brand> findAllByStatus(Status status, Pageable pageable);
}
