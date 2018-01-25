package net.datascientists.vo;

import java.util.HashSet;
import java.util.Set;

import net.datascientists.constants.UserState;

public class UserVO {

	private long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String state = UserState.ACTIVE.getState();
	private Set<UserProfileVO> userProfiles = new HashSet<UserProfileVO>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<UserProfileVO> getUserProfiles() {
		return userProfiles;
	}
	public void setUserProfiles(Set<UserProfileVO> userProfiles) {
		this.userProfiles = userProfiles;
	}
}
