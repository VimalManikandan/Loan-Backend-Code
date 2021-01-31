package login.login.model;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="userauth")
public class User {

			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name="userid")
			private int userid;
			
			@NotBlank(message="UserName Can't be blank")
			@Column(name="username")
		    private String 	username ;
			
			@NotBlank(message="Password Can't be blank")
			@Column(name="userpwd")
			private String userpwd ;
			
			@Column(name="usertype")
			private String usertype;	
			

			@Column(name="token")
			private String jwtToken;
					
			@JsonIgnore
			@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade= { CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH})
			private List<Loan> loans;
						
			
			public User() {
				super();

			}

			public User(int userid, String username, String userpwd, String usertype, String jwtToken
				) {
				super();
				this.userid = userid;
				this.username = username;
				this.userpwd = userpwd;
				this.usertype = usertype;
				this.jwtToken=jwtToken;
			}		

			public int getUserid() {
				return userid;
			}

			public void setUserid(int userid) {
				this.userid = userid;
			}

			public List<Loan> getLoans() {
				return loans;
			}

			public void setLoans(List<Loan> loans) {
				this.loans = loans;
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getUserpwd() {
				return userpwd;
			}

			public void setUserpwd(String userpwd) {
				this.userpwd = userpwd;
			}


			public String getUsertype() {
				return usertype;
			}


			public void setUsertype(String usertype) {
				this.usertype = usertype;
			}
						
			public String getJwtToken() {
				return jwtToken;
			}

			public void setJwtToken(String jwtToken) {
				this.jwtToken = jwtToken;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + userid;
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				User other = (User) obj;
				if (userid != other.userid)
					return false;
				return true;
			}

			@Override
			public String toString() {
				return "User [uid=" + userid + ", username=" + username + ", userpwd=" + userpwd + ", usertype=" + usertype
						+ "]";
			}
			
}
