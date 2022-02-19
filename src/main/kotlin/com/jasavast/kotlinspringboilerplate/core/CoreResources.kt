package com.jasavast.kotlinspringboilerplate.core

import com.jasavast.kotlinspringboilerplate.controller.vm.ReqVM
import com.jasavast.kotlinspringboilerplate.core.error.ClientStatusCode
import com.jasavast.kotlinspringboilerplate.core.error.ErrorResponseException
import com.jasavast.kotlinspringboilerplate.service.ApiAbstract
import kotlinx.coroutines.reactive.collect
import org.json.JSONObject
import org.springframework.context.ApplicationContext
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.Collections
import java.util.stream.Collectors
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSuperclassOf


@RestController
@RequestMapping("/api/request")
class CoreResources(private final val context: ApplicationContext){
    @GetMapping("")
    fun getResponse(request: ServerHttpRequest): Mono<JSONObject> {
        val req=validationRequestGet(request);
        var bean:Object = context.getBean(req.getString("beanName")) as Object;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions
        }
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions
        }

        return Mono.empty();
    }
    @PostMapping("")
    @PutMapping("")
    fun postResponse(request: ReqVM): Mono<JSONObject>{

        val method=request.method.split("\\.")
        if (method.size!=2){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
        }
        var bean:Object = context.getBean(method[0]) as Object;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(request.getData());
            bean::class.declaredFunctions
        }
        return Mono.empty();
    }
    @DeleteMapping("")
    fun deleteResponse(request: ServerHttpRequest):Mono<JSONObject>{
        val req=validationRequestGet(request);
        var bean:Object = context.getBean(req.getString("beanName")) as Object;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions
        }
        return Mono.empty();
    }
    @PutMapping("")
    fun updateResponse(request: ReqVM):Mono<JSONObject>{
        val method=request.method.split("\\.")
        if (method.size!=2){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
        }
        var bean:Object = context.getBean(method[0]) as Object;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(request.getData());
            bean::class.declaredFunctions
        }
        return Mono.empty();
    }
    fun validationRequestGet(request: ServerHttpRequest): JSONObject {
        val map =request.queryParams.toSingleValueMap();
        val req=JSONObject(map);
        if (!req.has("method")){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"parameter method tidak ditemukan");
        }
        val method=req.getString("method").split("\\.");
        if (method.size!=2){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
        }
        req.append("beanName",method[0])
        req.append("method",method[1])
        return req;
    }

}

