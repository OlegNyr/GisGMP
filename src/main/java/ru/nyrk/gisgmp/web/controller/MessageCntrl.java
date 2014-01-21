package ru.nyrk.gisgmp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nyrk.gisgmp.database.service.MessagesService;
import ru.nyrk.gisgmp.database.service.MessagesServiceImpl;
import ru.nyrk.gisgmp.util.kendo.DataSourceRequest;
import ru.nyrk.gisgmp.util.kendo.DataSourceResult;
import ru.nyrk.gisgmp.web.controller.abstr.BaseController;

@Controller
@RequestMapping("/mes")
public class MessageCntrl extends BaseController {



    @Qualifier("messagesService")
    @Autowired
    private MessagesService messagesService;

    @RequestMapping("/")
    public String list(){
        return "/mess/list";
    }


    @RequestMapping(value = "/rest/dataSource", method = RequestMethod.POST)
    public
    @ResponseBody
    DataSourceResult regions(@RequestBody DataSourceRequest request) {
        return messagesService.getListDataSource(request);
    }

}
