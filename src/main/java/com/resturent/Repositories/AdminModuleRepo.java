package com.resturent.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resturent.Modules.Users;

@Repository
public interface AdminModuleRepo extends JpaRepository<Users,Long> {

     Optional<Users> findByUsername(String username);
}
