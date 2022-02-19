package com.jasavast.kotlinspringboilerplate.core.error

class ParameterException(clientStatusCode: ClientStatusCode,message:String):RuntimeException(message){
}