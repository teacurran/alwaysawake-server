package com.wirelust.aa.data.model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Date: 03-Nov-2016
 *
 * @author T. Curran
 */
@Entity
public class Invite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	 Long id;

	@Basic
	private String value;

	@ManyToOne
	private Account fromAccount;

	@ManyToOne
	private Account usedByAccount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateClaimed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getUsedByAccount() {
		return usedByAccount;
	}

	public void setUsedByAccount(Account usedByAccount) {
		this.usedByAccount = usedByAccount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateClaimed() {
		return dateClaimed;
	}

	public void setDateClaimed(Date dateClaimed) {
		this.dateClaimed = dateClaimed;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
