package com.bitsessential.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ApplicationUser {
	@Id
    @GeneratedValue(generator="user-uuid")
    @GenericGenerator(name="user-uuid", strategy = "uuid")
    @Column(unique = true)
	@Size(max = 16)
    private long userId;
     
    @NotNull
    @Column(unique=true)
    private String userName;
    
    @NotNull
    @Column
    private String userPassword;

    @NotNull
    @Column
    private String userType;
    
    
    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}