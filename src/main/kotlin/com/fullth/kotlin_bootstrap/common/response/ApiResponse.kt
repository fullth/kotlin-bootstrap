package com.fullth.kotlin_bootstrap.common.response

sealed class ApiResponse<out T> {
    /**
     * 데이터만 반환
     */
    data class Basic<T>(
        val data: T,
    ) : ApiResponse<T>()

    /**
     * 데이터와 컨텍스트를 반환
     */
    data class WithContext<T>(
        val data: T,
        val context: Context,
    ) : ApiResponse<T>()

    /**
     * 데이터 리스트와 컨텍스트를 반환
     *
     * WIP:
     * - 페이징의 반환 타입은 무조건 배열로 고정할 것인가?
     * - 타입스크립트의 타이핑과 유사하게 유연한 설계를 제공하는 방법도 있지 않을까?
     */
    data class Page<T>(
        val data: MutableList<T>,
        val context: Context,
    ) : ApiResponse<List<T>>() {
        companion object {
            // TODO: JPA와 같은 ORM을 사용하는 경우 SpringPage를 사용하는 구조가 괜찮은지 확인
            fun <T> from(page: org.springframework.data.domain.Page<T>): Page<T> =
                Page<T>(
                    data = page.content,
                    context =
                        Context(
                            page =
                                Context.Page<Any?>(
                                    pageNumber = page.number,
                                    offset = page.number.toLong() * page.size,
                                    size = page.size.toLong(),
                                    total = page.totalElements,
                                ),
                        ),
                )
        }
    }
}

data class Context(
    val page: Page<Any?>? = null,
) {
    data class Page<T>(
        val pageNumber: Int,
        val offset: Long,
        val size: Long,
        val total: Long,
    )
}
