package com.jasavast.kotlinspringboilerplate.core

import org.json.JSONObject
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/request")
class CoreResources {
    @GetMapping("")
    fun getResponse(): Mono<JSONObject> {
        return Mono.empty();
    }
    @PostMapping("")
    fun postResponse(): Mono<JSONObject>{

        return Mono.empty();
    }
    @DeleteMapping("")
    fun deleteResponse():Mono<JSONObject>{
        return Mono.empty();
    }
    @PutMapping("")
    fun updateResponse():Mono<JSONObject>{
        return Mono.empty();
    }
}