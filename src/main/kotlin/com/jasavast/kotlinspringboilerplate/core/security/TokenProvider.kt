package com.jasavast.kotlinspringboilerplate.core.security

import org.springframework.security.core.Authentication

class TokenProvider {

    fun createToken(auth: Authentication):String{
        return "";
    }
    fun validateToken(token:String):Boolean{
        throw NotImplementedError()
    }
    fun getAuth(token:String):Authentication{
        throw NotImplementedError()
    }
}