package com.shop.module.user.dao;

import com.shop.module.user.entity.Addresses;

public interface AddressesMapper {
    int deleteByPrimaryKey(Integer addrId);

    int insert(Addresses record);

    int insertSelective(Addresses record);

    Addresses selectByPrimaryKey(Integer addrId);

    int updateByPrimaryKeySelective(Addresses record);

    int updateByPrimaryKey(Addresses record);
}