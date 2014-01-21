package ru.nyrk.gisgmp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nyrk.gisgmp.web.controller.abstr.BaseController;

@RequestMapping("/")
@Controller
public class WelcomeCntrl extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public String welcome(){
      return "default";
    }
}
