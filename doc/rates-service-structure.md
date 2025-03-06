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
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── inditex
│   │   │           └── rate
│   │   │               └── rate_service
│   │   │                   ├── application
│   │   │                   │   ├── dto
│   │   │                   │   │   ├── RateRequestDto.java
│   │   │                   │   │   └── RateResponseDto.java
│   │   │                   │   └── ports
│   │   │                   │       ├── RateRepositoryJpa.java
│   │   │                   │       └── RateService.java
│   │   │                   ├── domain
│   │   │                   │   ├── exception
│   │   │                   │   │   ├── GlobalExceptionHandler.java
│   │   │                   │   │   └── ValidationExceptionHandler.java
│   │   │                   │   └── model
│   │   │                   │       ├── Currency.java
│   │   │                   │       └── Rate.java
│   │   │                   ├── infrastructure
│   │   │                   │   ├── adapter
│   │   │                   │   │   ├── api
│   │   │                   │   │   │   └── RateController.java
│   │   │                   │   │   └── service
│   │   │                   │   │       ├── CurrencyService.java
│   │   │                   │   │       └── RateServiceImpl.java
│   │   │                   │   ├── config
│   │   │                   │   │   ├── OpenApiConfig.java
│   │   │                   │   │   └── WebClientConfig.java
│   │   │                   │   └── utils
│   │   │                   │       └── UtilsRateService.java
│   │   │                   └── RateServiceApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── inditex
│                   └── rate
│                       └── rate_service
│                           ├── controllerTest
│                           │   ├── RateControllerHttpTest.java
│                           │   └── RateControllerUnitTest.java
│                           ├── currencyServiceTest
│                           │   └── CurrencyServiceTest.java
│                           ├── RateServiceApplicationTests.java
│                           ├── serviceTest
│                           │   └── RateServiceTest.java
│                           └── UtilsRateTest.java
└── target
    ├── classes
    │   ├── application.properties
    │   └── com
    │       └── inditex
    │           └── rate
    │               └── rate_service
    │                   ├── application
    │                   │   ├── dto
    │                   │   │   ├── RateRequestDto.class
    │                   │   │   └── RateResponseDto.class
    │                   │   └── ports
    │                   │       ├── RateRepositoryJpa.class
    │                   │       └── RateService.class
    │                   ├── domain
    │                   │   ├── exception
    │                   │   │   ├── GlobalExceptionHandler.class
    │                   │   │   └── ValidationExceptionHandler.class
    │                   │   └── model
    │                   │       ├── Currency.class
    │                   │       └── Rate.class
    │                   ├── infrastructure
    │                   │   ├── adapter
    │                   │   │   ├── api
    │                   │   │   │   └── RateController.class
    │                   │   │   └── service
    │                   │   │       ├── CurrencyService.class
    │                   │   │       └── RateServiceImpl.class
    │                   │   ├── config
    │                   │   │   ├── OpenApiConfig.class
    │                   │   │   └── WebClientConfig.class
    │                   │   └── utils
    │                   │       └── UtilsRateService.class
    │                   └── RateServiceApplication.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    ├── maven-archiver
    │   └── pom.properties
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── rate-service-0.0.1-SNAPSHOT.jar
    ├── rate-service-0.0.1-SNAPSHOT.jar.original
    ├── surefire-reports
    │   ├── com.inditex.rate.rate_service.controllerTest.RateControllerHttpTest.txt
    │   ├── com.inditex.rate.rate_service.controllerTest.RateControllerUnitTest.txt
    │   ├── com.inditex.rate.rate_service.currencyServiceTest.CurrencyServiceTest.txt
    │   ├── com.inditex.rate.rate_service.RateServiceApplicationTests.txt
    │   ├── com.inditex.rate.rate_service.serviceTest.RateServiceTest.txt
    │   ├── TEST-com.inditex.rate.rate_service.controllerTest.RateControllerHttpTest.xml
    │   ├── TEST-com.inditex.rate.rate_service.controllerTest.RateControllerUnitTest.xml
    │   ├── TEST-com.inditex.rate.rate_service.currencyServiceTest.CurrencyServiceTest.xml
    │   ├── TEST-com.inditex.rate.rate_service.RateServiceApplicationTests.xml
    │   └── TEST-com.inditex.rate.rate_service.serviceTest.RateServiceTest.xml
    └── test-classes
        └── com
            └── inditex
                └── rate
                    └── rate_service
                        ├── controllerTest
                        │   ├── RateControllerHttpTest.class
                        │   └── RateControllerUnitTest.class
                        ├── currencyServiceTest
                        │   └── CurrencyServiceTest.class
                        ├── RateServiceApplicationTests.class
                        ├── serviceTest
                        │   └── RateServiceTest.class
                        └── UtilsRateTest.class
