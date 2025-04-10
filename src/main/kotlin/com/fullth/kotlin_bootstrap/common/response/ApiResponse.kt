package com.fullth.kotlin_bootstrap.common.response

/**
 * API 응답의 표준 포맷을 정의하는 sealed 클래스
 * @param T 응답 데이터의 타입
 */
sealed class ApiResponse<out T> {
    /**
     * @param data 응답 데이터
     *
     * Response example:
     * ```json
     * {
     *     "data": {
     *         "id": 1,
     *         "name": "fullth",
     *         "email": "fullth@boot.ujiverse.com"
     *     }
     * }
     * ```
     */
    data class Basic<T>(
        val data: T,
    ) : ApiResponse<T>()

    /**
     * @param data 응답 데이터
     * @param context 부가정보
     *
     * Response example:
     * ```json
     * {
     *     "data": {
     *         "id": 1,
     *         "name": "fullth"
     *     },
     *     "context": {
     *         "version": "0.0.1",
     *         "isMobile": true
     *     }
     * }
     * ```
     */
    data class WithContext<T>(
        val data: T,
        val context: Context,
    ) : ApiResponse<T>()

    /**
     * 데이터 리스트와 컨텍스트를 반환
     *
     * @param data 페이지 데이터 목록
     * @param context 페이징 정보를 포함한 컨텍스트
     *
     * Response example:
     * ```json
     * {
     *     "data": [
     *         {
     *             "id": 1,
     *             "name": "fullth"
     *         },
     *         {
     *             "id": 2,
     *             "name": "emptyth"
     *         }
     *     ],
     *     "context": {
     *         "version": "0.0.1",
     *         "page": {
     *             "pageNumber": 0,
     *             "offset": 0,
     *             "size": 10,
     *             "total": 100
     *         }
     *     }
     * }
     * ```
     */
    data class Page<T>(
        val data: List<T>,
        val context: Context,
    ) : ApiResponse<List<T>>() {
        companion object {
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

            fun <T> of(
                content: List<T>,
                pageNumber: Int,
                pageSize: Int,
                total: Long,
            ): Page<T> =
                Page(
                    data = content,
                    context =
                        Context(
                            page =
                                Context.Page(
                                    pageNumber = pageNumber,
                                    offset = pageNumber.toLong() * pageSize,
                                    size = pageSize.toLong(),
                                    total = total,
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
