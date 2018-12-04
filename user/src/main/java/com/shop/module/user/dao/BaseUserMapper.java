package com.shop.module.user.dao;

import com.shop.module.user.entity.BaseUser;

public interface BaseUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(BaseUser record);

    int insertSelective(BaseUser record);

    BaseUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(BaseUser record);

    int updateByPrimaryKey(BaseUser record);
}