package com.jasavast.kotlinspringboilerplate.test

import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.IntStream

class InvocationIT {
    private val logger:Logger=LoggerFactory.getLogger(InvocationIT::class.java)
    @Test
    fun testInvoke(){
        val a:Object=KelasA() as Object
        println("cobain aja")
        logger.info("hello")
        IntRange(1,5).forEach { r-> println(r) }
        a.`class`.declaredMethods.forEach { r->
            run {
                if (r.name.equals("cobainAja")) {
                    var result:JSONObject=r.invoke(a) as JSONObject
                    println(result)
                }
            }
        }
    }
}