package com.inicu.config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Dipin
 *
 */

@Controller
public class ViewController {

	@RequestMapping("/")
    String index(Model model){
		model.addAttribute("username","Dipin");
		model.addAttribute("mode", "development");
        return "index";
    }
}
