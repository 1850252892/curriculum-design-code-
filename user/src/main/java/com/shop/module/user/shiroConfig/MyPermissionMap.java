package com.shop.module.user.shiroConfig;

import com.shop.module.user.entity.UserPermission;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyPermissionMap {

    public Map<String,String> getPermissions(List<UserPermission> permissions){
     Map<String,String> map = new LinkedHashMap<>();
      for (UserPermission permission:permissions){
           map.put(permission.getUrl(),"perms["+permission.getPerName()+"]");
      }

     return map;
    }
}
