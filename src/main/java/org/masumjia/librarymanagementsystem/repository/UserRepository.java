package org.masumjia.librarymanagementsystem.repository;

import org.masumjia.librarymanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
