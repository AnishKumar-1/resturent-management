package com.resturent.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resturent.Modules.AdminModule;

@Repository
public interface AdminModuleRepo extends JpaRepository<AdminModule,Long> {

     Optional<AdminModule> findByUsername(String username);
}
