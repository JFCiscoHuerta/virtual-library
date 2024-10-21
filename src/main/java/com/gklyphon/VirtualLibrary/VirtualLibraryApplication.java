package com.gklyphon.VirtualLibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VirtualLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualLibraryApplication.class, args);
	}

}
