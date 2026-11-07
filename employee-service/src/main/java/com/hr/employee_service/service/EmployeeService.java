package com.hr.employee_service.service;

import com.hr.employee_service.exception.DuplicateEmployeeException;
import com.hr.employee_service.exception.EmployeeNotFoundException;
import com.hr.employee_service.model.Employee;
import com.hr.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public Employee create(Employee employee) {
        if (repository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEmployeeException("Email déjà utilisé : " + employee.getEmail());
        }
        if (repository.existsByMatricule(employee.getMatricule())) {
            throw new DuplicateEmployeeException("Matricule déjà utilisé : " + employee.getMatricule());
        }
        employee.setId(null);
        return repository.save(employee);
    }

    @Transactional
    public Employee update(Long id, Employee data) {
        Employee existing = findById(id);
        if (!existing.getEmail().equals(data.getEmail()) && repository.existsByEmail(data.getEmail())) {
            throw new DuplicateEmployeeException("Email déjà utilisé : " + data.getEmail());
        }
        if (!existing.getMatricule().equals(data.getMatricule()) && repository.existsByMatricule(data.getMatricule())) {
            throw new DuplicateEmployeeException("Matricule déjà utilisé : " + data.getMatricule());
        }
        existing.setMatricule(data.getMatricule());
        existing.setFirstName(data.getFirstName());
        existing.setLastName(data.getLastName());
        existing.setEmail(data.getEmail());
        existing.setPhone(data.getPhone());
        existing.setHireDate(data.getHireDate());
        existing.setStatus(data.getStatus());
        existing.setPositionId(data.getPositionId());
        existing.setDepartmentId(data.getDepartmentId());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Employee existing = findById(id);
        repository.delete(existing);
    }
}