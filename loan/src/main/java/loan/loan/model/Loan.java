package loan.loan.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="loan")
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loanno; 
	
	@NotBlank(message="Name can't be blank")
	private String fname ;
	
	@NotBlank(message="Name can't be blank")
	private String lname ;
	private String paddress;
	
	@NotNull(message="Loan amount can't be blank")
	@Min(value=0, message="Loan Amount can't be negative")
	private int loanAmount;
	
	@NotBlank(message="Loan Type can't be blank")
	private String loantype;
	
	@NotNull(message="Loan amount can't be blank")
	@Min(value=0, message="Loan term can't be negative")
	private int loanterm;
	
	@ManyToOne(cascade={
			//CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + loanno;
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
		Loan other = (Loan) obj;
		if (loanno != other.loanno)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Loan [loanno=" + loanno + ", fname=" + fname + ", lname=" + lname + ", paddress=" + paddress
				+ ", loanAmount=" + loanAmount + ", loantype=" + loantype + ", loanterm=" + loanterm + "]";
	} 
	
	

}
