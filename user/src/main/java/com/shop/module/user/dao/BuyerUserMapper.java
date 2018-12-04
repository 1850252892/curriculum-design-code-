package com.shop.module.user.dao;

import com.shop.module.user.entity.BuyerUser;

public interface BuyerUserMapper {
    int deleteByPrimaryKey(Integer buyerId);

    int insert(BuyerUser record);

    int insertSelective(BuyerUser record);

    BuyerUser selectByPrimaryKey(Integer buyerId);

    int updateByPrimaryKeySelective(BuyerUser record);

    int updateByPrimaryKey(BuyerUser record);
}