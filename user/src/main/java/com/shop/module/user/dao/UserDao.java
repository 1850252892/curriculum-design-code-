package com.shop.module.user.dao;

import com.shop.module.user.entity.User;

public interface UserDao {
//    @Select(" select *\n" +
//            "        from u_user\n" +
//            "        where id=#{uid,jdbcType=INTEGER}")
    User selectUserById(String uid);

    Integer insertUser(User user);
}
