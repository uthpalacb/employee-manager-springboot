package com.example.employeemanager.controller;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    
    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "employees";
    }

    
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "edit-employee";
    }


    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-employee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }


    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}