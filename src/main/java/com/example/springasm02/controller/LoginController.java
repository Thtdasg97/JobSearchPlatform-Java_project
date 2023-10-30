package com.example.springasm02.controller;
import com.example.springasm02.entity.Role;
import com.example.springasm02.entity.User;
import com.example.springasm02.service.RoleService;
import com.example.springasm02.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class LoginController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(Model theModel) {
        User newUser = new User();
        theModel.addAttribute("newUser", newUser);

        // get the role list form database
        List<Role> roleList = roleService.findAllRoles();
        theModel.addAttribute("roleList", roleList);

        return "public/login";
    }

    @GetMapping("/showMyRegisterForm")
    public String showMyRegisterForm(Model theModel) {
        User newUser = new User();
        theModel.addAttribute("newUser", newUser);

        // get the role list form database
        List<Role> roleList = roleService.findAllRoles();
        theModel.addAttribute("roleList", roleList);

        return "public/register-form";

    }

    @PostMapping("/auth/register")
    public String saveUser(@ModelAttribute(name = "newUser") User theUser, @RequestParam("role_id") int roleId) {
        Role role = roleService.findRoleById(roleId);
        theUser.setRole(role);
        userService.saveUser(theUser);

        return "public/registration-confirmation";
    }

    @PostMapping("/auth/login")
    public String authenticateUser(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   HttpSession session,
                                   Model theModel) {
        User user = userService.findUserByEmail(email);

        if (user != null && verifyPassword(user, password)) {
            // Successful login
            setSessionAttributes(user, session);
            return user.getRole().getRoleName().equals("HR")
                    ? "redirect:/showRecruiterLoginPage"
                    : "redirect:/showMyHomePage";

        } else {
            // Invalid credentials
            theModel.addAttribute("error", true);
            return "public/login";
        }
    }

    // verify password
    private boolean verifyPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    // set session attribute
    private void setSessionAttributes(User user, HttpSession session) {
        session.setAttribute("email", user.getEmail());
        session.setAttribute("fullName", user.getFullName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("user", user);
        session.setAttribute("status", user.getStatus());
    }

}
