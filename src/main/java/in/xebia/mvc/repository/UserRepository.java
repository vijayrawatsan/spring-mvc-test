package in.xebia.mvc.repository;

import in.xebia.mvc.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{
	public User findByUserNameAndPassword(String userName, String password);
	public User findByUserName(String userName);
}
