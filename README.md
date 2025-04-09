# KotlinBootstrap
## 개요
* 초기 프로젝트를 생성하면 전역 에러 설정, 응답 데이터 직렬화 및 포맷팅, 페이징, dto 타입 변환 등 반드시 필요한 설정들이 존재합니다.
* 반복되는 설정을 라이브러리를 통해 해소합니다.
* 팀 프로젝트 혹은 사이드 프로젝트에서 활용하기 위한 라이브러리를 개발합니다.

## 구현할 기능
* 에러 처리
* 필터링
* 페이징
* 반환 타입

## 구현 방향
* 간단한 기능을 제공후에 지속적인 유지보수로 발전시키는 방향으로 구상하였습니다.
* A) 강제화되어 있어 사용자는 정해진 스펙에 맞춰 사용하기만 하면 되는 기능을 제공할 예정입니다.
* B) 기초 설계만 제공하여 사용자가 커스텀하게 수정 가능하도록 하는 기능을 제공할 예정입니다.

## 고려할 점
* 어노테이션등으로 기능을 제공하는 경우 코드 전반적으로 의존적인 코드가 분포되지 않도록 설계

## 패키지 구조
* 예시(by. Claude)
```
src
├── main
│   └── kotlin
│       └── com.fullth.kotlin_bootstrap
│           ├── KotlinBootstrapApplication.kt
│           ├── common
│           │   ├── config
│           │   │   └── WebMvcConfig.kt
│           │   └── response
│           │       ├── ApiResponse.kt
│           │       └── ResponseEntityBuilder.kt
│           ├── core
│           │   ├── error
│           │   │   ├── GlobalExceptionHandler.kt
│           │   │   └── exception
│           │   │       └── BusinessException.kt
│           │   ├── filter
│           │   ├── FilterConfig.kt
│           │   │   └── RequestLoggingFilter.kt
│           │   └── pagination
│           │       ├── PageRequest.kt
│           │       └── PagingUtil.kt
│           └── support
│               └── converter
│                   ├── StringToEnumConverter.kt
│                   └── StringToLocalDateTimeConverter.kt
```
* common
  * 해당 프로젝트의 구현에 필요한 공통된 처리를 관리합니다
* core
  * 주요 구현 사항의 처리를 관리합니다
* helper
  * 기타 보조적인 기능을 관리합니다