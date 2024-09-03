package com.tyut.travel.controller.frontdesk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @version v1.0
 * @author OldGj 2024/5/2
 * @apiNote 人工智能控制层
 */
@Controller
@RequestMapping("/ai")
public class AiController {


    @RequestMapping("/chat")
    public String chat(){
        return "redirect:/frontdesk/springai";
    }


}
