package com.netcracker.edu.odelivery.model;

import com.netcracker.edu.odelivery.database.annotation.Attribute;
import com.netcracker.edu.odelivery.database.annotation.AttributeList;
import com.netcracker.edu.odelivery.database.annotation.ObjectType;
import com.netcracker.edu.odelivery.model.attributes.UserAttributes;

import java.util.Date;

@ObjectType(objType = UserAttributes.OBJECT_TYPE_ID)
public class User extends Entity {
    @Attribute(attrId = UserAttributes.LOGIN)
    private String login;
    @Attribute(attrId = UserAttributes.PASSWORD)
    private String password;
    @Attribute(attrId = UserAttributes.EMAIL)
    private String email;
    @Attribute(attrId = UserAttributes.FIRST_NAME)
    private String firstName;
    @Attribute(attrId = UserAttributes.LAST_NAME)
    private String lastName;
    @Attribute(attrId = UserAttributes.PHONE_NUMBER)
    private Long phone;
    @Attribute(attrId =UserAttributes.REGISTRATION_DATE)
    protected Date registrationDate;
    @AttributeList(attrId =UserAttributes.AUTHORITY)
    private String authority;

    public User(){
        registrationDate=new Date();
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
