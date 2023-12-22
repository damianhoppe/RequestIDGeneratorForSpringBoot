# Request ID Generator for Spring Boot

Request tracking tool for microservices architecture.
It generates an identifier for incoming requests, injects
it into outgoing request and displays it in logs.

[![](https://jitpack.io/v/damianhoppe/RequestIDGeneratorForSpringBoot.svg)](https://jitpack.io/#damianhoppe/RequestIDGeneratorForSpringBoot)

## Get started

### Gradle

Add the following repository:
```
maven(url = "https://jitpack.io")
```
and add the following dependency:
```
implementation("com.github.damianhoppe.RequestIDGeneratorForSpringBoot:starter:{latest_version}")
```
which includes common, config, core and logback modules, or you can specify modules separately:
```
implementation("com.github.damianhoppe.RequestIDGeneratorForSpringBoot:common:{latest_version}")//Included in core
implementation("com.github.damianhoppe.RequestIDGeneratorForSpringBoot:config:{latest_version}")//Included in core
implementation("com.github.damianhoppe.RequestIDGeneratorForSpringBoot:core:{latest_version}")
implementation("com.github.damianhoppe.RequestIDGeneratorForSpringBoot:logback:{latest_version}")
implementation("com.github.damianhoppe.RequestIDGeneratorForSpringBoot:openfeign:{latest_version}")
```

### Enable

Add the following annotation to the main application class or configuration class:
```
@EnableRequestIdGenerator
```
It loads configurations located in the package:
`pl.damianhoppe.spring.requestidgenerator.configuration`

which allows you to automatically load the configuration of other submodules, you just need to add a dependency.

### Configuration

Modules can be configured in two ways: through application properties and in the code.

#### Properties

| Property                          | Description                                                                                                                                                                                                                                                                                    | Default value           |
|-----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| ridg.profile                      | Name of configuration profile or "none","" for no profile                                                                                                                                                                                                                                      | ""                      |
| ridg.mode                         | Mode of request id generator. Available modes: servletRequestListener, filter                                                                                                                                                                                                                  | servletRequestListener  |
| ridg.requestId.headerName         | Name of header with request id for incoming and outgoing requests                                                                                                                                                                                                                              | X-Request-Id            |
| ridg.requestId.attributeName      | Name of request attribute with request id.                                                                                                                                                                                                                                                     | X-Request-Id            |
| ridg.requestId.providingStrategy  | Request id providing strategy. Available values: read(only reads the id from the incoming request), generate(always generate new id, regardless of whether the request has the appropriate header with identifier), auto(reads the id from header or generates a new one if it does not exist) | auto                    |
| ridg.requestId.placeholder        | Placeholder for the request id if it cannot be obtained (when it could not be read from the incoming request and providing strategy does not allow it to be generated)                                                                                                                         | ""                      |
| ridg.requestId.generationDisabled | Disables request id generation                                                                                                                                                                                                                                                                 | false                   |
| ridg.filter.order                 | Order of request id generator filter                                                                                                                                                                                                                                                           | Integer.MIN_VALUE + 100 |
| ridg.openFeign.disableInterceptor | Disables request interceptor for outgoing requests via openFeign which injects header with request id                                                                                                                                                                                          | false                   |
| ridg.logs.mdcKey                  | The name of the key under which the request id is saved in MDC (Mapped Diagnostic Context). Changing this value to something other than the default also requires updating the log pattern to display the request id.                                                                          | RequestId               |

#### In code configuration

Each module can have its own configuration class that loads default values from the application properties.
All default configurations are loaded into the RIdGConfigurer class object, which allows you to override the default parameters.
These classes may also offer additional configuration possibilities.

Below is an example of the configuration in code:
```
@Configuration
class RequestIdGeneratorConfiguration {

    @Bean
    fun requestIdGeneratorConfig(configurer: RIdGConfigurer): RIdGConfig {
        return configurer
            .customize<MainConfig> {it
                .requestIdHeaderName("X-Request-Id")
            }
            .customize<CoreConfig> { it
                .mode(RequestIdGeneratingMode.SERVLET_REQUEST_LISTENER)
                .providingStrategy(RequestIdProvidingStrategy.GENERATE)
                .placeholder("-")
            }
            .customize<OpenFeignConfig> {it
                .urlMatcher(AntUrlMatcher("http://second-server/**"))
            }
            .build()
    }
}
```

Some configuration options, including filtering requests based on url, are available from the code level.
Below is a list of parameters for individual module configuration classes
with which they have been extended compared to the configuration via application properties.

- _MainConfig.filterBuilder_ - Builder for Filter that is responsible for generation of request id. It is not required and may allow you
to override the default filter.
- _MainConfig.servletRequestListenerBuilder_ - Builder for ServletRequestListener that is responsible for generation of request id.
It is not required and may allow you to override the default listener.
- _MainConfig.interceptors_ - List of interceptors that track the start and end of handling incoming requests 
- _MainConfig.urlMatcher_ - UrlMatcher checks the URL of incoming requests that can be processed. 
- _OpenFeign.urlMatcher_ - UrlMatcher checking url of outgoing requests. Only for matching requests, header with request id is injected.

#### Configuration profiles

It is also possible to edit the default properties by activating the configuration profile.
To do this, set the `ridg.profile` property to the profile name. By default, there are 2 profiles:
- internal - intended for internal servers to which users do not have direct access, and requests are sent only from other services.
In this mode, identifiers are read from headers or generated by default.
- external - intended for servers that are open to requests from outside, identifiers are not read from headers, but generated.

Of course, when the profile is loaded, only the default properties are changed, which can be overwritten via application
properties(e.g. application.yml).

It is possible to create your own configuration profiles to replace the default properties.
Below is an example of a defined sample profile named "testProfile", which overwrites the default properties:
```
@ConfigurationProfile("testProfile")
class TestConfigurationProfile {
    @Provider
    fun overrideDefaultProperties(): RIdGProperties {
        return RIdGProperties().apply {
            this.requestId.headerName = "Request-Id"
            ...
        }
    }
}
```
The above profile must be in a package visible to the Spring context(application package or added using ComponentScan).
After setting the `ridg.profile=testProfile` property, the object returned by the `overrideDefaultProperties` method will be used when
configuring the application.

## Read request id in controller

You can manually read the generated request ID in the current thread serving the request with:
```
RequestContext.requestId
```
You can also read the assigned attribute for a given request:
```
request.getAttribute(requestIdAttributeName)
```
By default, the attribute name is `RIdGProperties.DEFAULT_REQUEST_ID_ATTRIBUTE`, but if the name has been changed in the configuration,
it can be retrieved from the RIdGConfigHolder bean:
```
configHolder.get().getConfig<MainConfig>().requestIdAttributeName
```

## Error controller

You can easily include the request id in the error message for creating component:
```
@Component
class ErrorController(
    errorAttributes: ErrorAttributes,
    serverProperties: ServerProperties,
    config: RIdGConfigHolder,
) : WithIdErrorController(errorAttributes, serverProperties, config) { }
```
Results:
```
curl http://localhost:8080/hi
Response:
{"timestamp":"2023-12-21T22:26:14.371+00:00","status":404,"error":"Not Found","path":"/hi","errorId":"de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca"}
```
You can change errorId to a different name by passing the responseAttributeName parameter to the constructor.

## Modules

### common
Contains classes and interfaces common to all modules.

### config
Responsible for loading and handling the configuration of all other modules.
It offers all the classes and data structures needed to define the configuration, 
offers interfaces and a helper class that allows you to remember the data and requeset id as part of request handling in a current
thread by accessing the singleton object and supports loading a configuration profile.

### core
Support for the generator of incoming request IDs via a Filer or ServletRequestListener and calling interceptors.

### logback
Offers putting the request id in MDC and configuring a log pattern that includes the request id.

Sample logs:
![Sample logs](/pictures/logs.png)
---
```
2023-12-21T23:26:10.819+01:00  INFO 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] .s.DefaultReqIdGenServletRequestListener : Assigned requestId: d47f8a70-258c-45a8-8288-14c2ba78fbfb for request: /
2023-12-21T23:26:10.839+01:00  INFO 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2023-12-21T23:26:10.839+01:00  INFO 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2023-12-21T23:26:10.839+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Detected StandardServletMultipartResolver
2023-12-21T23:26:10.839+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Detected AcceptHeaderLocaleResolver
2023-12-21T23:26:10.839+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Detected FixedThemeResolver
2023-12-21T23:26:10.839+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Detected org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator@6251087
2023-12-21T23:26:10.839+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Detected org.springframework.web.servlet.support.SessionFlashMapManager@26aae6fa
2023-12-21T23:26:10.839+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : enableLoggingRequestDetails='false': request parameters and headers will be masked to prevent unsafe logging of potentially sensitive data
2023-12-21T23:26:10.839+01:00  INFO 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
2023-12-21T23:26:10.849+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : GET "/", parameters={}
2023-12-21T23:26:10.859+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to pl.damianhoppe.reqidgen.sample.mainserver.MainController#index()
2023-12-21T23:26:10.869+01:00  INFO 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] p.d.r.sample.mainserver.MainController   : Hello world!
2023-12-21T23:26:10.881+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] m.m.a.RequestResponseBodyMethodProcessor : Using 'text/plain', given [*/*] and supported [text/plain, */*, application/json, application/*+json]
2023-12-21T23:26:10.889+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] m.m.a.RequestResponseBodyMethodProcessor : Writing ["Hello world!"]
2023-12-21T23:26:10.894+01:00 DEBUG 4376 d47f8a70-258c-45a8-8288-14c2ba78fbfb --- [main-server] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
2023-12-21T23:26:14.344+01:00  INFO 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] .s.DefaultReqIdGenServletRequestListener : Assigned requestId: de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca for request: /hi
2023-12-21T23:26:14.349+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : GET "/hi", parameters={}
2023-12-21T23:26:14.356+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped to ResourceHttpRequestHandler [classpath [META-INF/resources/], classpath [resources/], classpath [static/], classpath [public/], ServletContext [/]]
2023-12-21T23:26:14.359+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.w.s.r.ResourceHttpRequestHandler     : Resource not found
2023-12-21T23:26:14.362+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.servlet.resource.NoResourceFoundException: No static resource hi.]
2023-12-21T23:26:14.362+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Completed 404 NOT_FOUND
2023-12-21T23:26:14.362+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : "ERROR" dispatch for GET "/error", parameters={}
2023-12-21T23:26:14.362+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to pl.damianhoppe.reqidgen.sample.mainserver.ErrorController#error(HttpServletRequest)
2023-12-21T23:26:14.530+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'application/json', given [*/*] and supported [application/json, application/*+json]
2023-12-21T23:26:14.530+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing [{timestamp=Thu Dec 21 23:26:14 CET 2023, status=404, error=Not Found, path=/hi, errorId=de6b5bd8-5e4 (truncated)...]
2023-12-21T23:26:14.579+01:00 DEBUG 4376 de6b5bd8-5e41-48b5-86f6-d8a61ffbbaca --- [main-server] [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Exiting from "ERROR" dispatch, status 404
```

### openfeign
Responsible for injecting the header into outgoing requests (using openFeign) and the current request id

### starter
Contains modules:
- common
- config
- core
- logback

It should always work out of the box with the default spring boot configuration

### sample
Contains an example using 3 services:
- Eureka server
- Main server - sends a request to the second service 
- Second server

## License

This project is licensed under [Apache 2.0 license](LICENSE)