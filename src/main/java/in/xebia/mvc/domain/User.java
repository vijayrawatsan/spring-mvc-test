package in.xebia.mvc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class User {
	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = -7633538050298848825L;
	public User(){
	}
	public User(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Pattern(regexp = "^[a-z0-9_-]{3,15}$")
	private String userName;
	@NotNull
	private String password;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
