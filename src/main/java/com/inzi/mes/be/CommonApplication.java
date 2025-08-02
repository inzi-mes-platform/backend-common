package com.inzi.mes.be;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
		basePackages={ "org.camunda", "com.inzi.mes.be" }
)
public class CommonApplication implements CommandLineRunner {
	
    public static void main( String[] args ) {
    	SpringApplication.run(CommonApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	}

}
