package com.fullth.kotlin_bootstrap.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class Print {
    fun pretty(data: Any) {
        println(jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data))
    }
}
