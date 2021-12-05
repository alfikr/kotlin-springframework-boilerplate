package com.jasavast.kotlinspringboilerplate.api.test

import com.jasavast.kotlinspringboilerplate.service.ApiAbstract
import org.json.JSONObject
import org.springframework.stereotype.Component

@Component
class ApiCommonTest (): ApiAbstract() {

    fun getData():JSONObject{
        var result:JSONObject= JSONObject();
        result.put("status","OK");
        result.put("data",reqData)
        return result;
    }
}