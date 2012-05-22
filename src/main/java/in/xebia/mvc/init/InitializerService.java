package in.xebia.mvc.init;

import in.xebia.mvc.domain.User;
import in.xebia.mvc.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class InitializerService {

	@Autowired
	private UserRepository userRepository;
	
	public void init(){
		userRepository.save(new User("vijay","1a1dc91c907325c69271ddf0c944bc72"));
		userRepository.save(new User("amit","1a1dc91c907325c69271ddf0c944bc72"));
		userRepository.save(new User("karan","1a1dc91c907325c69271ddf0c944bc72"));
	}
}

