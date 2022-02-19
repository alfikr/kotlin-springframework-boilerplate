package com.jasavast.kotlinspringboilerplate.test

import com.jasavast.kotlinspringboilerplate.core.annotations.GetExecution
import org.json.JSONObject

class KelasA {
    @GetExecution("676454")
    fun cobainAja():JSONObject{
        println("method dipanggil")
        return JSONObject()
            .put("hello","world")
    }
    fun test2(){
        println("failed")
    }
}