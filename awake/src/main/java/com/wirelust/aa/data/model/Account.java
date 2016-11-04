package com.wirelust.aa.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.wirelust.aa.util.StringUtils;

/**
 * Date: 11-03-2015
 *
 * @author T. Curran
 */
@Entity
@Cacheable
@NamedQueries({
		@NamedQuery(name = Account.QUERY_BY_USERNAME_NORMALIZED,
				query = "SELECT A " +
						"FROM Account A " +
						"WHERE A.usernameNormalized = :username"),

		@NamedQuery(name = Account.QUERY_BY_USERNAME_NORMALIZED_OR_EMAIL,
				query = "SELECT A " +
						"FROM Account A " +
						"WHERE A.usernameNormalized = :username " +
						"OR A.email = :email"),

		@NamedQuery(name = Account.QUERY_BY_EMAIL,
				query = "SELECT A " +
						"FROM Account A " +
						"WHERE A.email = :email")
	}
)
public class Account implements java.io.Serializable {

	public static final String QUERY_BY_EMAIL				= "account.byEmail";
	public static final String QUERY_BY_USERNAME_NORMALIZED	= "account.byUsernameNormalized";
	public static final String QUERY_BY_USERNAME_NORMALIZED_OR_EMAIL	= "account.byUsernameNormalizedOrEmail";

	public enum DisabledReason {
		GENERIC(1),
		EXCESS_PW_FAIL(2),
		TOS_VIOLATION(3);

		private int value;

		DisabledReason(final int value) {
			this.value = value;
		}

		public static DisabledReason fromValue(final int value) {
			for (DisabledReason item : DisabledReason.values()) {
				if (item.getValue() == value) {
					return item;
				}
			}
			return null;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Status {
		WAITING(0),
		INVITED(1),
		EMAIL_VERIFY(2),
		ACTIVE(3),
		DISABLED(4);

		private int value;

		Status(final int value) {
			this.value = value;
		}

		public static Status fromValue(final int value) {
			for (Status item : Status.values()) {
				if (item.getValue() == value) {
					return item;
				}
			}
			return null;
		}

		public int getValue() {
			return value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	 Long id;

	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String usernameNormalized;

	@Column(length=200)
	private String fullName;

	@Basic
	private String location;

	@Lob
	private String bio;

	@Basic
	private String email;

	@Column(length = 200)
	private String password;

	@Basic
	private String passwordSalt;

	@Basic
	private String background;

	@Basic
	private String avatar;

	@Basic
	private Integer timezone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModified;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLogin;

	@Basic
	private Integer loginFailureCount = 0;

	@Basic
	private int statusId;

	@Basic
	private boolean disabled;

	@Basic
	private boolean admin;

	@Basic
	private String website;

	@Basic
	private Integer followingCount;

	@Basic
	private Integer followersCount;

	@Basic
	private Integer disabledReasonId;

	@Basic
	private int invites;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<AccountSetting> settings = new ArrayList<>();

	@Transient
	private Status status;

	@Transient
	private DisabledReason disabledReason;

	public Account() {
		dateCreated = new Date();
		dateModified = new Date();
	}

	@PrePersist
	public void prePersist() {
		dateModified = new Date();

		if (status == null) {
			statusId = Status.WAITING.getValue();
		} else {
			statusId = status.getValue();
		}

		if (disabledReason == null) {
			disabledReasonId = null;
		} else {
			disabledReasonId = disabledReason.getValue();
		}
	}

	@PostLoad
	public void postLoad() {
		status = Status.fromValue(statusId);
		disabledReason = DisabledReason.fromValue(disabledReasonId);
	}

	public void setUsername(String username) {
		this.username = username;
		this.setUsernameNormalized(StringUtils.normalizeUsername(username));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
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

	public Date getDateLogin() {
		return dateLogin;
	}

	public void setDateLogin(Date dateLogin) {
		this.dateLogin = dateLogin;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public List<AccountSetting> getSettings() {
		return settings;
	}

	public void setSettings(List<AccountSetting> settings) {
		this.settings = new ArrayList<>(settings);
	}

	public String getUsernameNormalized() {
		return usernameNormalized;
	}

	public void setUsernameNormalized(String usernameNormalized) {
		this.usernameNormalized = usernameNormalized;
	}

	public DisabledReason getDisabledReason() {
		return disabledReason;
	}

	public void setDisabledReason(DisabledReason disabledReason) {
		this.disabledReason = disabledReason;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public void setWebsite(String inWebsite) {
		website = inWebsite;
	}

	public String getWebsite() {
		return website;
	}

	public Integer getFollowingCount() {
		if (followingCount == null) {
			followingCount = 0;
		}
		return followingCount;
	}

	public void setFollowingCount(Integer followingCount) {
		this.followingCount = followingCount;
	}

	public Integer getFollowersCount() {
		if (followersCount == null) {
			followersCount = 0;
		}
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public Integer getDisabledReasonId() {
		return disabledReasonId;
	}

	public void setDisabledReasonId(Integer disabledReasonId) {
		this.disabledReasonId = disabledReasonId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getInvites() {
		return invites;
	}

	public void setInvites(int invites) {
		this.invites = invites;
	}
}
