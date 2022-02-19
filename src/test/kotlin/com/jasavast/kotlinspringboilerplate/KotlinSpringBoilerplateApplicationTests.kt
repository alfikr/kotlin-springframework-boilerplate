package com.jasavast.kotlinspringboilerplate

import com.jasavast.kotlinspringboilerplate.core.security.TokenProvider
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinSpringBoilerplateApplicationTests {
    @Autowired
    lateinit var tokenProvider:TokenProvider;
    @Test
    fun contextLoads() {
        var a:Authentication=UsernamePasswordAuthenticationToken("admin",
            "", mutableSetOf(SimpleGrantedAuthority("ADMIN"),
                SimpleGrantedAuthority("USER")
            )
        )
        val c= tokenProvider.createToken(a,true);
        println(c)
    }

}
