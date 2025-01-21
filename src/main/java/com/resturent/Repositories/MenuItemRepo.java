package com.resturent.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resturent.Modules.MenuItem;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Long>{

    Optional<MenuItem> findByName(String name);
	
}
