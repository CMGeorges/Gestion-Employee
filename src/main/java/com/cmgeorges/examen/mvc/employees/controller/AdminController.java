package com.cmgeorges.examen.mvc.employees.controller;

import com.cmgeorges.examen.mvc.employees.model.User;
import com.cmgeorges.examen.mvc.employees.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/adminPage")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {


        return findPaginated(1, "firstName", "asc", model);
    }

    /**
     * save users to database
     *
     * @param user
     * @return
     */
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.Update(user);
        return "redirect:/adminPage/";
    }


    /**
     * Update
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String UpdateUser(@PathVariable(value = "id") long id, Model model) {


        Optional<User> user = userService.getUsersById(id);
        model.addAttribute("user", user.get());


        return "user/update_User";
    }


    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method
        userService.deleteUser(id);

        return "redirect:/adminPage/";
    }

    /**
     * can be sort by name and count....
     *
     * @param pageNo
     * @param sortField
     * @param sortDir
     * @param model
     * @return
     */
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam String sortField,
                                @RequestParam String sortDir, Model model) {
        int pageSize = 5;


        Page<User> page = userService.findPages(pageNo, pageSize, sortField, sortDir);
        Iterable<User> userList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUsers", userList);
        return "user/allUsers";


    }
}
