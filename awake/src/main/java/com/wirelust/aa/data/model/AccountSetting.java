package com.wirelust.aa.data.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Date: 11-03-2015
 *
 * @author T. Curran
 */
@Entity
@Access( AccessType.FIELD )
@Cacheable
public class AccountSetting implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Account account;

	@Basic
	private String key;

	@Basic
	private String value;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getDateSet() {
		return dateSet;
	}

	public void setDateSet(Date dateSet) {
		this.dateSet = dateSet;
	}
}
