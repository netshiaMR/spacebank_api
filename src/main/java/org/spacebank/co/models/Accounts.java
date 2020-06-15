package org.spacebank.co.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.spacebank.co.models.audit.DateAudit;

@Entity
@Table(name = "Accounts")
public class Accounts extends DateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long user_id;

    @Column(name="bank_id")
	private Long bank_id;

    @Column(name="card_id")
	private Long card_id;

    @Column(length = 15)
	private String branch_code;
    
	@NotBlank
	@Size(max = 25)
	private Long accountNumber;
	
	@NotBlank
	@Size(max = 60)
	private String accountHolder;

	@NotBlank
	@Size(max = 60)
	private String accountStates;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(length = 60)
	private AccountType accountType;
	
	@NotBlank
	private double accountBalance;
		
	@NotBlank
	private double avilableBalance;
	
	@NotBlank
	private double avilable_credit;
	
	@Column(name="credit_limit")
	private double creditLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getBank_id() {
		return bank_id;
	}

	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}

	public Long getCard_id() {
		return card_id;
	}

	public void setCard_id(Long card_id) {
		this.card_id = card_id;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getAccountStates() {
		return accountStates;
	}

	public void setAccountStates(String accountStates) {
		this.accountStates = accountStates;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public double getAvilableBalance() {
		return avilableBalance;
	}

	public void setAvilableBalance(double avilableBalance) {
		this.avilableBalance = avilableBalance;
	}

	public double getAvilable_credit() {
		return avilable_credit;
	}

	public void setAvilable_credit(double avilable_credit) {
		this.avilable_credit = avilable_credit;
	}

	public double getCredit_limit() {
		return creditLimit;
	}

	public void setCredit_limit(double credit_limit) {
		this.creditLimit = credit_limit;
	}
	
	
	
}
