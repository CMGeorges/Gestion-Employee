package com.cmgeorges.examen.mvc.employees.service.Impl;

import com.cmgeorges.examen.mvc.employees.model.Employee;
import com.cmgeorges.examen.mvc.employees.repository.EmployeeRepository;
import com.cmgeorges.examen.mvc.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void createEmployee(Employee employee) {
         employeeRepository.save(employee);
    }

    /**
     * GET par identifiant
     * @param id
     * @return
     */
    @Override
    public Employee getEmployeeById(long id) {
        Optional < Employee > optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException("  not found for :: " + id);
        }
        return employee;
    }


    /**
     * delete par identifiant
     * @param id
     */
    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);

    }

    @Override
    public Page<Employee> findPages(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }


}
