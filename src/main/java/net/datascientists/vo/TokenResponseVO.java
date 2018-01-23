package net.datascientists.vo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponseVO {
    @JsonProperty
    private String token;
    @JsonProperty
    private Map<String,Object> userInfo = new HashMap<>();

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<String, Object> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Map<String, Object> userInfo) {
		this.userInfo = userInfo;
	}
	
}
