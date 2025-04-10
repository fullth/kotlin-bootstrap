package com.fullth.kotlin_bootstrap.common.respnose

import com.fullth.kotlin_bootstrap.common.response.ApiResponse
import com.fullth.kotlin_bootstrap.utils.Print
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ApiResponseTest {
    data class User(
        val id: Long,
        val name: String,
    )

    @Nested
    @DisplayName("ApiResponse.Basic 테스트")
    inner class BaseResponseTest {
        @Test
        fun `응답 테스트 - Basic`() {
            // given
            val user = User(1, "fullth")

            // when
            val response = ApiResponse.Basic(user)

            // then
            println(Print().pretty(response))
            assertThat(response.data).isEqualTo(user)
        }
    }
}
