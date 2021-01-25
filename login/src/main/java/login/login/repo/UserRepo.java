package login.login.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import login.login.model.User;



public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByUsernameAndUserpwd(String username,String userpwd);

	public User findByUsername(String username);
}
