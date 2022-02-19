package com.jasavast.kotlinspringboilerplate.core.error

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
class ErrorResponseException(clientStatusCode: ClientStatusCode, message: String) : RuntimeException(message) {
}