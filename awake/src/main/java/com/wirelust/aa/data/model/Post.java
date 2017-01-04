package com.wirelust.aa.data.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

/**
 * Date: 03-Jan-2017
 *
 * @author T. Curran
 */
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	 Long id;

	@Basic
	private String subject;

	@Lob
	private String body;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModified;

	@Basic
	private Integer replies;

	@ManyToOne
	private Account author;

	@ManyToOne
	private Account lastResponder;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "post_participants",
			joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")})
	@BatchSize(size=20)
	private Set<Account> participants = new HashSet<>();

	@PrePersist
	public void prePersist() {
		if (dateCreated == null) {
			dateCreated = new Date();
		}

		dateModified = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Integer getReplies() {
		return replies;
	}

	public void setReplies(Integer replies) {
		this.replies = replies;
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public Account getLastResponder() {
		return lastResponder;
	}

	public void setLastResponder(Account lastResponder) {
		this.lastResponder = lastResponder;
	}

	public Set<Account> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Account> participants) {
		this.participants = participants;
	}
}

