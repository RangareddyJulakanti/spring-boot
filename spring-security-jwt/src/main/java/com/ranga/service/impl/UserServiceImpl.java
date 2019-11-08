package com.ranga.service.impl;

import com.ranga.repository.EmployeeRepository;
import com.ranga.domain.entity.Employee;
import com.ranga.domain.model.UserDto;
import com.ranga.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username);
		if(employee == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(), getAuthority(employee));
	}

	private Set<SimpleGrantedAuthority> getAuthority(Employee employee) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		employee.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	public List<Employee> findAll() {
		List<Employee> list = new ArrayList<>();
		employeeRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee findOne(String username) {
		return employeeRepository.findByUsername(username);
	}

	@Override
	public Employee findById(Long id) {
		return employeeRepository.findById(id).get();
	}

	@Override
    public Employee save(UserDto user) {
	    Employee newEmployee = new Employee();
	    newEmployee.setUsername(user.getUsername());
	    newEmployee.setPassword(bcryptEncoder.encode(user.getPassword()));
		newEmployee.setAge(user.getAge());
		newEmployee.setSalary(user.getSalary());
        return employeeRepository.save(newEmployee);
    }
}
