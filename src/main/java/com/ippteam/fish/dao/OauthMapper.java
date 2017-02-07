package com.ippteam.fish.dao;

import com.ippteam.fish.entity.Oauth;
import com.ippteam.fish.entity.OauthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("OauthDao")
public interface OauthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    long countByExample(OauthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int deleteByExample(OauthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int insert(Oauth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int insertSelective(Oauth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    List<Oauth> selectByExample(OauthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    Oauth selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int updateByExampleSelective(@Param("record") Oauth record, @Param("example") OauthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int updateByExample(@Param("record") Oauth record, @Param("example") OauthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int updateByPrimaryKeySelective(Oauth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth
     *
     * @mbg.generated Tue Feb 07 15:45:54 CST 2017
     */
    int updateByPrimaryKey(Oauth record);
}