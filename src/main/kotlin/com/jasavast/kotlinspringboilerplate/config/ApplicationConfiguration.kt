package com.jasavast.kotlinspringboilerplate.config

import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = [
    "com.jasavast.kotlinspringboilerplate.api",
    "com.jasavast.kotlinspringboilerplate.core",
    "com.jasavast.kotlinspringboilerplate.config"
])

class ApplicationConfiguration {
}