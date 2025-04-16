package com.fullth.kotlin_bootstrap.common.respnose

import com.fullth.kotlin_bootstrap.common.response.ApiResponse
import com.fullth.kotlin_bootstrap.common.response.Context
import com.fullth.kotlin_bootstrap.utils.Print
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.InstanceOfAssertFactories
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

    @Nested
    @DisplayName("ApiResponse.WithContext 테스트")
    inner class WithContextResponseTest {
        @Test
        fun `기존 API 응답은 동일하지만 버전에 따른 분기가 필요한 경우 커스텀 프로퍼티를 반환할 수 있다`() {
            // when
            val context =
                Context.onlyMeta(
                    meta =
                        mapOf(
                            "version" to "1.0.0",
                            "isMobile" to true,
                            "deviceId" to "device123",
                            "userAgent" to "Mozilla/5.0",
                        ),
                )

            // then
            println(Print().pretty(context))
            assertThat(context)
                .isInstanceOf(Context.Context.MetaContext::class.java)
                .extracting("meta")
                .asInstanceOf(InstanceOfAssertFactories.MAP)
                .containsAllEntriesOf(
                    mapOf(
                        "version" to "1.0.0",
                        "isMobile" to true,
                        "deviceId" to "device123",
                        "userAgent" to "Mozilla/5.0",
                    ),
                )
        }

        @Test
        fun `페이지 정보만 필요한 경우 onlyPage를 사용하여 반환할 수 있다`() {
            // given
            val page =
                Context.Page<Any?>(
                    pageNumber = 0,
                    offset = 0,
                    size = 10,
                    total = 100,
                )

            // when
            val context = Context.onlyPage(page)

            // then
            println(Print().pretty(context))
            assertThat(context)
                .isInstanceOf(Context.Context.PageContext::class.java)
                .extracting("page")
                .isEqualTo(page)
        }

        @Test
        fun `페이지, 추가 프로퍼티 정보 둘 다 필요한 경우 of를 사용하여 반환할 수 있다`() {
            // given
            val page =
                Context.Page<Any?>(
                    pageNumber = 0,
                    offset = 0,
                    size = 10,
                    total = 100,
                )
            val meta =
                mapOf(
                    "version" to "1.0.0",
                    "isMobile" to true,
                )

            // when
            val context = Context.of(page, meta)

            // then
            println(Print().pretty(context))
            assertThat(context)
                .isInstanceOf(Context.Context.FullContext::class.java)
                .extracting("page", "meta")
                .containsExactly(page, meta)
        }
    }
}
