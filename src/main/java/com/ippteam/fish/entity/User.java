package com.ippteam.fish.entity;

import java.util.Date;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.full_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String fullName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.nike_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String nikeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gender
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String gender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.birthdate
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Date birthdate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.icon
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String icon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.email
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.qq
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Integer qq;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.weibo
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String weibo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.register_way
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private String registerWay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.register_ip
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Integer registerIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.register_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Date registerTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.update_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_name
     *
     * @return the value of user.user_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_name
     *
     * @param userName the value for user.user_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.full_name
     *
     * @return the value of user.full_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.full_name
     *
     * @param fullName the value for user.full_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.nike_name
     *
     * @return the value of user.nike_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getNikeName() {
        return nikeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.nike_name
     *
     * @param nikeName the value for user.nike_name
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setNikeName(String nikeName) {
        this.nikeName = nikeName == null ? null : nikeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gender
     *
     * @return the value of user.gender
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gender
     *
     * @param gender the value for user.gender
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.birthdate
     *
     * @return the value of user.birthdate
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.birthdate
     *
     * @param birthdate the value for user.birthdate
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.icon
     *
     * @return the value of user.icon
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.icon
     *
     * @param icon the value for user.icon
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.email
     *
     * @return the value of user.email
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.email
     *
     * @param email the value for user.email
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phone
     *
     * @return the value of user.phone
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.phone
     *
     * @param phone the value for user.phone
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.qq
     *
     * @return the value of user.qq
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Integer getQq() {
        return qq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.qq
     *
     * @param qq the value for user.qq
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setQq(Integer qq) {
        this.qq = qq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.weibo
     *
     * @return the value of user.weibo
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getWeibo() {
        return weibo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.weibo
     *
     * @param weibo the value for user.weibo
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.register_way
     *
     * @return the value of user.register_way
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public String getRegisterWay() {
        return registerWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.register_way
     *
     * @param registerWay the value for user.register_way
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setRegisterWay(String registerWay) {
        this.registerWay = registerWay == null ? null : registerWay.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.register_ip
     *
     * @return the value of user.register_ip
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Integer getRegisterIp() {
        return registerIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.register_ip
     *
     * @param registerIp the value for user.register_ip
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setRegisterIp(Integer registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.register_time
     *
     * @return the value of user.register_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.register_time
     *
     * @param registerTime the value for user.register_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_time
     *
     * @return the value of user.create_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_time
     *
     * @param createTime the value for user.create_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.update_time
     *
     * @return the value of user.update_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.update_time
     *
     * @param updateTime the value for user.update_time
     *
     * @mbg.generated Thu Dec 22 13:18:03 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}