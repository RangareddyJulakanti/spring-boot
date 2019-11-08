package com.ranga.service;

import com.ranga.domain.entity.Employee;
import com.ranga.domain.model.UserDto;

import java.util.List;

public interface UserService {

    Employee save(UserDto user);
    List<Employee> findAll();
    void delete(long id);
    Employee findOne(String username);

    Employee findById(Long id);
}
