package com.wrox.site;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultEmployeeService implements EmployeeService
{
    @Override
    public void saveEmployee(Employee employee)
    {
        // no-op
    }

    @Override
    public Employee getEmployee(long id)
    {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees()
    {
        return null; // to force trigger a validation error
    }
}
