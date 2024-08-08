package com.example.demo.Controller;


import com.example.demo.Dto.UserDtoLog;
import com.example.demo.Dto.UserDtoReg;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    @Autowired
    public AuthController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("userDtoLog", new UserDtoLog());
        return "login";
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute("userDtoLog") UserDtoLog log, Model model) {
        return "redirect:/courses/show-favo";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userDtoReg", new UserDtoReg());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDtoReg") UserDtoReg reg, Model model) {
        service.save(reg);
        return "redirect:/login";
    }
}
