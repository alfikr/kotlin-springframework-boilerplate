package com.jasavast.kotlinspringboilerplate.service

import lombok.extern.slf4j.Slf4j
import org.json.JSONObject

@Slf4j
abstract class ApiAbstract() {

    protected open var req:JSONObject?=null;
    protected  open var reqData:JSONObject?=null;

    fun init(req: JSONObject){
        this.req=req;
        this.reqData=req.getJSONObject("data");
    }
}