package com.example.BSS.repository;

import com.example.BSS.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//     Optional<Role> findByName(String name);
}