package com.jasavast.kotlinspringboilerplate.domain

data class User(val id:String,val firstName:String,val lastName:String,
val aktif:Boolean, val login:String, val password:String, val images:String, val authorities:Set<Authority>)
