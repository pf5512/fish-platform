package com.ippteam.fish.entity;

public class Oauth {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth.id
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth.uid
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    private Integer uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth.oauth_name
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    private String oauthName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth.oauth_id
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    private String oauthId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth.oauth_access_token
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    private String oauthAccessToken;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth.oauth_expires
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    private Integer oauthExpires;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth.id
     *
     * @return the value of oauth.id
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth.id
     *
     * @param id the value for oauth.id
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth.uid
     *
     * @return the value of oauth.uid
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth.uid
     *
     * @param uid the value for oauth.uid
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth.oauth_name
     *
     * @return the value of oauth.oauth_name
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public String getOauthName() {
        return oauthName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth.oauth_name
     *
     * @param oauthName the value for oauth.oauth_name
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public void setOauthName(String oauthName) {
        this.oauthName = oauthName == null ? null : oauthName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth.oauth_id
     *
     * @return the value of oauth.oauth_id
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public String getOauthId() {
        return oauthId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth.oauth_id
     *
     * @param oauthId the value for oauth.oauth_id
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public void setOauthId(String oauthId) {
        this.oauthId = oauthId == null ? null : oauthId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth.oauth_access_token
     *
     * @return the value of oauth.oauth_access_token
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth.oauth_access_token
     *
     * @param oauthAccessToken the value for oauth.oauth_access_token
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public void setOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken == null ? null : oauthAccessToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth.oauth_expires
     *
     * @return the value of oauth.oauth_expires
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public Integer getOauthExpires() {
        return oauthExpires;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth.oauth_expires
     *
     * @param oauthExpires the value for oauth.oauth_expires
     *
     * @mbg.generated Tue Feb 07 14:24:44 CST 2017
     */
    public void setOauthExpires(Integer oauthExpires) {
        this.oauthExpires = oauthExpires;
    }
}