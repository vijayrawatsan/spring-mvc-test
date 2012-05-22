package in.xebia.mvc.controller;

import in.xebia.mvc.domain.User;
import in.xebia.mvc.repository.UserRepository;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	protected static Logger logger = Logger.getLogger("MainController");
	
	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
    	return "login";
	}
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid User user, BindingResult result, Model model) {
    	if(result.hasErrors()){
    		model.addAttribute("STATUS", "INVALID LOGIN");
    		return "redirect:/login";
    	}
    	if(userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword())!=null){
    		model.addAttribute("STATUS", "SUCCESS");
    		return "home";
    	}
    	model.addAttribute("STATUS", "DOES NOT EXIST");
    	return "redirect:/login";
	}
    
    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers() {
    	return userRepository.findAll();
	}
    
    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String userName) {
    	return userRepository.findByUserName(userName);
	}
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody User getUserByRequestParameter(@RequestParam String userName) {
    	return userRepository.findByUserName(userName);
	}
}