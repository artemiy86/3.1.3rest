package rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rest.model.User;
import rest.service.UserServiceImpl;


@Controller
@RequestMapping("/user")
public class UserController {
    private UserServiceImpl service;

    public  UserController(){}

    @Autowired
    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String getRoot(@AuthenticationPrincipal User user, ModelMap model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "view/userInformation";
    }


}