package loan.loan.model;



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



@Entity
@Table(name="userauth")
public class User {

			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name="userid")
			private int userid;
			
			@Column(name="username")
		    private String 	username ;
			
			@Column(name="userpwd")
			private String userpwd ;
			
			@Column(name="usertype")
			private String usertype;	
			

			@Column(name="loggedin")
			private boolean loggedin;

			@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade= { 
					//CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH})
			private List<Loan> loans;
			
			
			public User() {
				super();

			}

			public User(int userid, String username, String userpwd, String usertype, boolean loggedin,List<Loan> loans) {
				super();
				this.userid = userid;
				this.username = username;
				this.userpwd = userpwd;
				this.usertype = usertype;
				this.loggedin=loggedin;
			//	this.loans=loans;
			}		

			public int getUserid() {
				return userid;
			}

			public void setUserid(int userid) {
				this.userid = userid;
			}
/*
			public List<Loan> getLoans() {
				return loans;
			}

			public void setLoans(List<Loan> loans) {
				this.loans = loans;
			}*/

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
			
			

			public boolean isLoggedin() {
				return loggedin;
			}

			public void setLoggedin(boolean loggedin) {
				this.loggedin = loggedin;
			}

			@Override
			public String toString() {
				return "User [uid=" + userid + ", username=" + username + ", userpwd=" + userpwd + ", usertype=" + usertype
						+ "]";
			}
			
}
