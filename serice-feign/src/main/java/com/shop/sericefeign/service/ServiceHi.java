package com.shop.sericefeign.service;

import com.shop.sericefeign.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ServiceHi {
    @Autowired
    SchedualServiceHi schedualServiceHi;

    public String sayHiFromClientOne (String name){
        return schedualServiceHi.sayHiFromClientOne(name);
    }
}
