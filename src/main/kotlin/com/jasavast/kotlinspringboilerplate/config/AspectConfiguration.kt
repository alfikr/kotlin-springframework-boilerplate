package com.jasavast.kotlinspringboilerplate.config

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy
@Aspect
class AspectConfiguration {
    @Around("@annotation(com.jasavast.kotlinspringboilerplate.core.annotations.DeleteExecution) ||" +
            "@annotation(com.jasavast.kotlinspringboilerplate.core.annotations.PutExecution) ||" +
            "@annotation(com.jasavast.kotlinspringboilerplate.core.annotations.GetExecution) ||" +
            "@annotation(com.jasavast.kotlinspringboilerplate.core.annotations.PostExecution)" )
    fun proses(joinPoint:ProceedingJoinPoint):Any{
        val start:Long=System.currentTimeMillis();
        try{
            var o:Any= joinPoint.proceed();
            val end:Long=System.currentTimeMillis();
            //TODO Logging
            return o;
        }catch (e:Throwable){
            val end:Long = System.currentTimeMillis();
            val line:Int=if(e.stackTrace.isNotEmpty()) e.stackTrace[0].lineNumber else -1;
            //TODO Logging if process failed
            throw e;
        }
    }
}