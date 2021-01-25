package login.login.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="loan")
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loanno; 
	
	private String fname ;
	private String lname ;
	private String paddress;
	private int loanAmount;
	private String loantype;
	private int loanterm;

	@ManyToOne(cascade={
			CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}
	)
	@JoinColumn(name="userid",referencedColumnName="userid") 
	private User user;
		
	public Loan() {
		super();
	}
	
	public Loan(int loanno, String fname, String lname, String paddress, int loanAmount, String loantype,int loanterm) {
		super();
		this.loanno = loanno;
		this.fname = fname;
		this.lname = lname;
		this.paddress = paddress;
		this.loanAmount = loanAmount;
		this.loantype = loantype;
		this.loanterm = loanterm;
	}
	
	public int getLoanno() {
		return loanno;
	}

	public void setLoanno(int loanno) {
		this.loanno = loanno;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPaddress() {
		return paddress;
	}

	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}

	public int getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(int loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getLoantype() {
		return loantype;
	}

	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	public int getLoanterm() {
		return loanterm;
	}

	public void setLoanterm(int loanterm) {
		this.loanterm = loanterm;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
/*	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}*/

	@Override
	public String toString() {
		return "Loan [loanno=" + loanno + ", fname=" + fname + ", lname=" + lname + ", paddress=" + paddress
				+ ", loanAmount=" + loanAmount + ", loantype=" + loantype + ", loanterm=" + loanterm + "]";
	} 
	
	

}
