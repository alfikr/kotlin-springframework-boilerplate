package com.jasavast.kotlinspringboilerplate.controller

import org.json.JSONObject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/request")
class CoreApi {
    @PostMapping("")
    fun postRequest(@RequestBody req:JSONObject):JSONObject{
        return req;
    }
}