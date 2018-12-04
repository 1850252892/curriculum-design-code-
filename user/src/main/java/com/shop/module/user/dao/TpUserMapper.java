package com.shop.module.user.dao;

import com.shop.module.user.entity.TpUser;

public interface TpUserMapper {
    int deleteByPrimaryKey(String openId);

    int insert(TpUser record);

    int insertSelective(TpUser record);

    TpUser selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(TpUser record);

    int updateByPrimaryKey(TpUser record);
}