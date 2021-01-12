package com.cmgeorges.examen.mvc.employees.service;

import com.cmgeorges.examen.mvc.employees.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface EmployeeService

{
    Iterable<Employee> getAllEmployees();

    void createEmployee(Employee employee);

    Employee getEmployeeById(long id);

    void deleteEmployee(long id);

    Page<Employee> findPages(int pageNo, int pageSize,String sortField , String sortDirection);
}
