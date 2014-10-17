package com.wrox.site;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface EmployeeService
{
    public void saveEmployee(
            @NotNull(message = "{validate.employeeService.saveEmployee}")
            @Valid Employee employee
    );

    public Employee getEmployee(
            @Min(value = 1L,
                    message = "{validate.employeeService.getEmployee.id}") long id
    );

    @NotNull
    public List<Employee> getAllEmployees();
}
