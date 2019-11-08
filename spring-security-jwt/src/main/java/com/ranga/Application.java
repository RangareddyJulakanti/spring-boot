package com.ranga;

import com.ranga.domain.entity.Employee;
import com.ranga.domain.entity.Role;
import com.ranga.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(EmployeeRepository employeeRepository) {
        return args -> {

            Employee emp1 = new Employee();
            emp1.setFirstName("Ranga");
            emp1.setUsername("Ranga");
            emp1.setLastName("Ranga");
            emp1.setSalary(12345);
            emp1.setAge(23);
            emp1.setUsername("ranga");
            emp1.setPassword(passwordEncoder.encode("ranga@123"));

            Role role1=new Role();
            role1.setName("ADMIN");
            role1.setDescription("Admin role");
            Role role2=new Role();
            role2.setName("USER");
            role2.setDescription("User role");
            Set<Role> roles=new HashSet<>();
            roles.add(role1);
            roles.add(role2);
            emp1.setRoles(roles);

            employeeRepository.save(emp1);





        };
    }
}
