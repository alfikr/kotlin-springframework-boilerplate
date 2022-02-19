package com.jasavast.kotlinspringboilerplate.core.error

import org.springframework.http.HttpStatus

enum class ClientStatusCode(kode:String,type:String,description:String,httpStatus: HttpStatus) {
    SUCCESS("success","success","Success",HttpStatus.OK),
    METHOD_NOT_FOUND("method_not_found",Constant.BUSINESS_ERROR,"method not found",HttpStatus.NOT_FOUND),
    OBJECT_NOT_FOUND("object_not_found",Constant.BUSINESS_ERROR,"object not found",HttpStatus.NOT_FOUND);
    private object Constant {
        public final val BUSINESS_ERROR:String = "business_error"
    }
}