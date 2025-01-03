package com.resturent.Modules;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="admin_credentials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminModule {
   
	@Id
	private Long id;
    private String username;
    private String password;
}
