package com.jasavast.kotlinspringboilerplate.core

import com.jasavast.kotlinspringboilerplate.controller.vm.ReqVM
import com.jasavast.kotlinspringboilerplate.core.error.ClientStatusCode
import com.jasavast.kotlinspringboilerplate.core.error.ErrorResponseException
import com.jasavast.kotlinspringboilerplate.service.ApiAbstract
import org.json.JSONObject
import org.springframework.context.ApplicationContext
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSuperclassOf


@RestController
@RequestMapping("/api/request")
class CoreResources(private final val context: ApplicationContext){
    @GetMapping("")
    fun getResponse(request: ServerHttpRequest): Mono<JSONObject> {
        val req=validationRequestGet(request);
        var bean: Any = context.getBean(req.getString("beanName")) as Any;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions
        }
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions.forEach { r->run{

            } }
        }

        return Mono.empty();
    }
    @PostMapping("")
    @PutMapping("")
    fun postResponse(@Valid request: ReqVM): Mono<JSONObject>{

        val method=request.method.split("\\.")
        require(method.size==2, lazyMessage = {
            if(method.size!=2){
                throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
            }
        })
        var bean: Any = context.getBean(method[0]) as Any;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(request.getData());
            bean::class.declaredFunctions.forEach{r-> kotlin.run {

            }}
        }
        return Mono.empty();
    }
    @DeleteMapping("")
    fun deleteResponse(request: ServerHttpRequest):Mono<JSONObject>{
        val req=validationRequestGet(request);
        var bean: Any = context.getBean(req.getString("beanName")) as Any;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(req);
            bean::class.declaredFunctions.forEach{r-> kotlin.run {

            }}
        }
        return Mono.empty();
    }
    @PutMapping("")
    fun updateResponse(@Valid request: ReqVM):Mono<JSONObject>{
        val method=request.method.split("\\.")
        if (method.size!=2){
            throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
        }
        var bean: Any = context.getBean(method[0]) as Any;
        if(ApiAbstract::class.isSuperclassOf(bean::class)){
            (bean as ApiAbstract).init(request.getData());
            bean::class.declaredFunctions.forEach{m->
                kotlin.run {

                }
            }
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
        require(method.size==2, lazyMessage = {
            if(method.size!=2){
                throw ErrorResponseException(ClientStatusCode.METHOD_NOT_FOUND,"invalid value method parameter")
            }
        })
        req.append("beanName",method[0])
        req.append("method",method[1])
        return req;
    }

}

