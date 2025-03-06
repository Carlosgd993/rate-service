rate-service
├── .gitattributes
├── .gitignore
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── .vscode
│   └── settings.json
├── compose.yaml
├── currency-service-api-rest.yml
├── doc
│   ├── rates-service-structure.md
│   └── rates-service-v1-openapi.yaml
├── HELP.md
├── init-db.sql
├── init.sql
├── mappings
├── mvnw
├── mvnw.cmd
├── pom.xml
├── rate_service_Tree.txt
├── specs
│   └── currency-service-api-rest.yml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── inditex
    │   │           └── rate
    │   │               └── rate_service
    │   │                   ├── application
    │   │                   │   ├── dto
    │   │                   │   │   ├── RateRequestDto.java
    │   │                   │   │   └── RateResponseDto.java
    │   │                   │   └── ports
    │   │                   │       ├── RateRepositoryJpa.java
    │   │                   │       └── RateService.java
    │   │                   ├── domain
    │   │                   │   ├── exception
    │   │                   │   │   ├── GlobalExceptionHandler.java
    │   │                   │   │   └── ValidationExceptionHandler.java
    │   │                   │   └── model
    │   │                   │       ├── Currency.java
    │   │                   │       └── Rate.java
    │   │                   ├── infrastructure
    │   │                   │   ├── adapter
    │   │                   │   │   ├── api
    │   │                   │   │   │   └── RateController.java
    │   │                   │   │   └── service
    │   │                   │   │       ├── CurrencyService.java
    │   │                   │   │       └── RateServiceImpl.java
    │   │                   │   ├── config
    │   │                   │   │   ├── OpenApiConfig.java
    │   │                   │   │   └── WebClientConfig.java
    │   │                   │   └── utils
    │   │                   │       └── UtilsRateService.java
    │   │                   └── RateServiceApplication.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── inditex
                    └── rate
                        └── rate_service
                            ├── controllerTest
                            │   ├── RateControllerHttpTest.java
                            │   └── RateControllerUnitTest.java
                            ├── currencyServiceTest
                            │   └── CurrencyServiceTest.java
                            ├── RateServiceApplicationTests.java
                            ├── serviceTest
                            │   └── RateServiceTest.java
                            └── UtilsRateTest.java