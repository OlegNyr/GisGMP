package ru.nyrk.gisgmp.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nyrk.gisgmp.web.security.MyUserDetails;

@Controller
@RequestMapping("/account")
public class AccounCntrl {
    @RequestMapping(method = RequestMethod.GET, value = "login")
    public void login() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "loginfailed")
    public String loginfailed(ModelMap model) {
        model.put("error", "true");
        return "account/login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "userinfo")
    public void infoUser(ModelMap model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUserDetails) {
            model.put("userInfo", (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "userinfo")
    public String submitChangePassword(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

}
