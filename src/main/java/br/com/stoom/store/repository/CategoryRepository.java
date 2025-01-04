package br.com.stoom.store.repository;

import br.com.stoom.store.model.category.Category;
import br.com.stoom.store.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Page<Category> findAllByStatus(Status status, Pageable pageable);
}
