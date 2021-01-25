package com.cmgeorges.examen.mvc.employees.controller;

import com.cmgeorges.examen.mvc.employees.model.Employee;
import com.cmgeorges.examen.mvc.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Qualifier("employeeServiceImpl")
    @Autowired
    private EmployeeService service;

    @GetMapping("/")
    public String viewHomePage(Model model){



        return findPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model){

        Employee employee = new Employee();
        model.addAttribute("employee",employee);

        return "employee/create-form";

    }

    /**
     * save employee to database
     * @param employee
     * @return
     */
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        service.createEmployee(employee);
        return "redirect:/employee/";
    }



    /**
     * Update
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String UpdateEmployee(@PathVariable( value = "id") long id, Model model) {


        Employee employee = service.getEmployeeById(id);
        model.addAttribute("employee", employee);


        return "employee/update_Employee";
    }


    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method
        service.deleteEmployee(id);

        return "redirect:/employee/";
    }

//    Pages Managing


    /**
     * can be sort by name and count....
     * @param pageNo
     * @param sortField
     * @param sortDir
     * @param model
     * @return
     */
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,@RequestParam String sortField,
                                @RequestParam String sortDir, Model model) {
        int pageSize = 5;


        Page < Employee > page = service.findPages(pageNo, pageSize, sortField, sortDir);
        Iterable < Employee > listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);
        return "employee/home";



    }


}


