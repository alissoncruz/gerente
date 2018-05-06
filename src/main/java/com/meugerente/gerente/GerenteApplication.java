package com.meugerente.gerente;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class GerenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenteApplication.class, args);
	}
	
	
}


interface CustomerRepository extends JpaRepository<Customer, Long>{}

@RestController
class CustomerRestController{
	
	private  CustomerRepository customerRepository;
	
	CustomerRestController (CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@GetMapping("/customers")
	Collection<Customer> findAll(){
		return this.customerRepository.findAll();
	}
	
	@Bean
	ApplicationRunner run(CustomerRepository cr) {
		
		//args -> Stream.of("Alisson", "Ely").forEach(x -> cr.save(new Customer(null, x) ) );
		Customer c1 = new Customer();
		c1.setId(null);
		c1.setName("Alisson");
		cr.save(c1);
		
		Customer c2 = new Customer();
		c2.setId(null);
		c2.setName("Alisson");
		cr.save(c2);
		
		return null;
		
	}
	
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
class Customer{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}

@Component
class CustomHealthIncator implements HealthIndicator{

	@Override
	public Health health() {
		
		return Health.status("Production!").build();	
				
	}
	
}

/*@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	UserDetailsService users() {
		return new InMemoryUserDetailsManager(Collections.singleton(User.withUsername("alisson").roles("ADMIN").password("123").build()));
	}
}*/
