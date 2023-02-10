package com.backend.demo.registrationservice.repository.mapper;

import com.backend.demo.registrationservice.model.user.UserInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoMapper implements RowMapper<UserInfo> {
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(rs.getString("userName"));
        userInfo.setPassword(rs.getString("password"));
        userInfo.setAddress(rs.getString("address"));
        userInfo.setSalary(rs.getBigDecimal("salary"));
        userInfo.setPhone(rs.getString("phone"));
        userInfo.setEmail(rs.getString("email"));
        userInfo.setFirstName(rs.getString("firstName"));
        userInfo.setLastName(rs.getString("lastName"));
        userInfo.setReferenceCode(rs.getString("referenceCode"));
        userInfo.setMemberType(rs.getString("memberType"));
        return userInfo;
    }


}
