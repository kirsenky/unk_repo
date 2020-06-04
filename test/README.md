# unk_repo

# REST/gRPC performance test project

# Test objectives 
1. Create PostgreSQL DB

--- CREATE DATABASE collector_db;
--- enable uuid-ossp CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--- DB item ---
CREATE TABLE item (
id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
path VARCHAR(256) NOT NULL,
dsc VARCHAR(256) DEFAULT NULL
);

2. Create a REST Spring microservice to ADD / UPD / DEL items
3. Create a gRPC Spring microservice to ADD / UPD / DEL
4. Benchmark the two above services by adding / updating / deleting 1 million items
5. Provide the code in a git repository with documentation in README.md (install, deploy, run) + benchmark results

NB:

- licence can be GPL
- for accessing the database, use JOOQL for both subprojects
=================================================================================

# Installation
1. Install Postgress (if not installed)
   - run as postgress user commands:
     ${deployment_folder}\test\db\scripts\create.bat
2. Deploy from GIT https://github.com/kirsenky/unk_repo.git
3. Run in main project folder (${deployment_folder}/test) command: mvn -install 

# Test evidence
# REST test run listing
D:\jjj\jdk-13.0.2\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:56220,suspend=y,server=n -Dserver.port=8090 -Dfile.encoding=UTF-8 -classpath "D:\unk_repo\test\rest\client\target\classes;D:\unk_repo\test\db\target\classes;D:\.m2\postgresql\postgresql\9.1-901.jdbc4\postgresql-9.1-901.jdbc4.jar;D:\.m2\org\jooq\jooq\3.13.2\jooq-3.13.2.jar;D:\.m2\org\reactivestreams\reactive-streams\1.0.3\reactive-streams-1.0.3.jar;D:\.m2\javax\xml\bind\jaxb-api\2.3.1\jaxb-api-2.3.1.jar;D:\.m2\javax\activation\javax.activation-api\1.2.0\javax.activation-api-1.2.0.jar;D:\.m2\org\jooq\jooq-meta\3.13.2\jooq-meta-3.13.2.jar;D:\.m2\org\jooq\jooq-codegen\3.13.2\jooq-codegen-3.13.2.jar;D:\.m2\org\springframework\boot\spring-boot\2.3.0.RELEASE\spring-boot-2.3.0.RELEASE.jar;D:\.m2\org\springframework\spring-core\5.2.6.RELEASE\spring-core-5.2.6.RELEASE.jar;D:\.m2\org\springframework\spring-jcl\5.2.6.RELEASE\spring-jcl-5.2.6.RELEASE.jar;D:\.m2\org\springframework\spring-context\5.2.6.RELEASE\spring-context-5.2.6.RELEASE.jar;D:\.m2\org\springframework\spring-aop\5.2.6.RELEASE\spring-aop-5.2.6.RELEASE.jar;D:\.m2\org\springframework\spring-beans\5.2.6.RELEASE\spring-beans-5.2.6.RELEASE.jar;D:\.m2\org\springframework\spring-expression\5.2.6.RELEASE\spring-expression-5.2.6.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-starter\2.3.0.RELEASE\spring-boot-starter-2.3.0.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-starter-logging\2.3.0.RELEASE\spring-boot-starter-logging-2.3.0.RELEASE.jar;D:\.m2\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;D:\.m2\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;D:\.m2\org\apache\logging\log4j\log4j-to-slf4j\2.13.2\log4j-to-slf4j-2.13.2.jar;D:\.m2\org\apache\logging\log4j\log4j-api\2.13.2\log4j-api-2.13.2.jar;D:\.m2\org\slf4j\jul-to-slf4j\1.7.30\jul-to-slf4j-1.7.30.jar;D:\.m2\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;D:\.m2\org\yaml\snakeyaml\1.26\snakeyaml-1.26.jar;D:\.m2\org\springframework\boot\spring-boot-starter-web\2.3.0.RELEASE\spring-boot-starter-web-2.3.0.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-starter-json\2.3.0.RELEASE\spring-boot-starter-json-2.3.0.RELEASE.jar;D:\.m2\com\fasterxml\jackson\core\jackson-databind\2.11.0\jackson-databind-2.11.0.jar;D:\.m2\com\fasterxml\jackson\core\jackson-annotations\2.11.0\jackson-annotations-2.11.0.jar;D:\.m2\com\fasterxml\jackson\core\jackson-core\2.11.0\jackson-core-2.11.0.jar;D:\.m2\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.11.0\jackson-datatype-jdk8-2.11.0.jar;D:\.m2\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.11.0\jackson-datatype-jsr310-2.11.0.jar;D:\.m2\com\fasterxml\jackson\module\jackson-module-parameter-names\2.11.0\jackson-module-parameter-names-2.11.0.jar;D:\.m2\org\springframework\boot\spring-boot-starter-tomcat\2.3.0.RELEASE\spring-boot-starter-tomcat-2.3.0.RELEASE.jar;D:\.m2\org\apache\tomcat\embed\tomcat-embed-core\9.0.35\tomcat-embed-core-9.0.35.jar;D:\.m2\org\glassfish\jakarta.el\3.0.3\jakarta.el-3.0.3.jar;D:\.m2\org\apache\tomcat\embed\tomcat-embed-websocket\9.0.35\tomcat-embed-websocket-9.0.35.jar;D:\.m2\org\springframework\spring-web\5.2.6.RELEASE\spring-web-5.2.6.RELEASE.jar;D:\.m2\org\springframework\spring-webmvc\5.2.6.RELEASE\spring-webmvc-5.2.6.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-autoconfigure\2.3.0.RELEASE\spring-boot-autoconfigure-2.3.0.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-starter-test\2.3.0.RELEASE\spring-boot-starter-test-2.3.0.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-test\2.3.0.RELEASE\spring-boot-test-2.3.0.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-test-autoconfigure\2.3.0.RELEASE\spring-boot-test-autoconfigure-2.3.0.RELEASE.jar;D:\.m2\com\jayway\jsonpath\json-path\2.4.0\json-path-2.4.0.jar;D:\.m2\net\minidev\json-smart\2.3\json-smart-2.3.jar;D:\.m2\net\minidev\accessors-smart\1.2\accessors-smart-1.2.jar;D:\.m2\org\ow2\asm\asm\5.0.4\asm-5.0.4.jar;D:\.m2\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar;D:\.m2\jakarta\xml\bind\jakarta.xml.bind-api\2.3.3\jakarta.xml.bind-api-2.3.3.jar;D:\.m2\jakarta\activation\jakarta.activation-api\1.2.2\jakarta.activation-api-1.2.2.jar;D:\.m2\org\assertj\assertj-core\3.16.1\assertj-core-3.16.1.jar;D:\.m2\org\hamcrest\hamcrest\2.2\hamcrest-2.2.jar;D:\.m2\org\junit\jupiter\junit-jupiter\5.6.2\junit-jupiter-5.6.2.jar;D:\.m2\org\junit\jupiter\junit-jupiter-api\5.6.2\junit-jupiter-api-5.6.2.jar;D:\.m2\org\opentest4j\opentest4j\1.2.0\opentest4j-1.2.0.jar;D:\.m2\org\junit\platform\junit-platform-commons\1.6.2\junit-platform-commons-1.6.2.jar;D:\.m2\org\junit\jupiter\junit-jupiter-params\5.6.2\junit-jupiter-params-5.6.2.jar;D:\.m2\org\junit\jupiter\junit-jupiter-engine\5.6.2\junit-jupiter-engine-5.6.2.jar;D:\.m2\org\junit\vintage\junit-vintage-engine\5.6.2\junit-vintage-engine-5.6.2.jar;D:\.m2\org\apiguardian\apiguardian-api\1.1.0\apiguardian-api-1.1.0.jar;D:\.m2\org\junit\platform\junit-platform-engine\1.6.2\junit-platform-engine-1.6.2.jar;D:\.m2\junit\junit\4.13\junit-4.13.jar;D:\.m2\org\mockito\mockito-core\3.3.3\mockito-core-3.3.3.jar;D:\.m2\net\bytebuddy\byte-buddy\1.10.10\byte-buddy-1.10.10.jar;D:\.m2\net\bytebuddy\byte-buddy-agent\1.10.10\byte-buddy-agent-1.10.10.jar;D:\.m2\org\objenesis\objenesis\2.6\objenesis-2.6.jar;D:\.m2\org\mockito\mockito-junit-jupiter\3.3.3\mockito-junit-jupiter-3.3.3.jar;D:\.m2\org\skyscreamer\jsonassert\1.5.0\jsonassert-1.5.0.jar;D:\.m2\com\vaadin\external\google\android-json\0.0.20131108.vaadin1\android-json-0.0.20131108.vaadin1.jar;D:\.m2\org\springframework\spring-test\5.2.6.RELEASE\spring-test-5.2.6.RELEASE.jar;D:\.m2\org\xmlunit\xmlunit-core\2.7.0\xmlunit-core-2.7.0.jar;D:\.m2\org\springframework\boot\spring-boot-devtools\2.3.0.RELEASE\spring-boot-devtools-2.3.0.RELEASE.jar;D:\.m2\org\springframework\boot\spring-boot-configuration-processor\2.3.0.RELEASE\spring-boot-configuration-processor-2.3.0.RELEASE.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.3.4\lib\idea_rt.jar" unk.test.rest.client.RestClient
Connected to the target VM, address: '127.0.0.1:56220', transport: 'socket'

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.0.RELEASE)

2020-06-04 16:17:17.839  INFO 9084 --- [  restartedMain] unk.test.rest.client.RestClient          : Starting RestClient on User-PC with PID 9084 (D:\unk_repo\test\rest\client\target\classes started by unk in D:\unk_repo\test)
2020-06-04 16:17:17.848  INFO 9084 --- [  restartedMain] unk.test.rest.client.RestClient          : No active profile set, falling back to default profiles: default
2020-06-04 16:17:18.067  INFO 9084 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2020-06-04 16:17:18.068  INFO 9084 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2020-06-04 16:17:20.841  INFO 9084 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8090 (http)
2020-06-04 16:17:20.864  INFO 9084 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-06-04 16:17:20.865  INFO 9084 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
2020-06-04 16:17:21.080  INFO 9084 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-06-04 16:17:21.080  INFO 9084 --- [  restartedMain] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3012 ms
2020-06-04 16:17:21.525  INFO 9084 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-06-04 16:17:21.859  WARN 9084 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : Unable to start LiveReload server
2020-06-04 16:17:22.076  INFO 9084 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8090 (http) with context path ''
2020-06-04 16:17:22.103  INFO 9084 --- [  restartedMain] unk.test.rest.client.RestClient          : Started RestClient in 6.189 seconds (JVM running for 7.812)
2020-06-04 16:17:23.872  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : === Starting performance test for REST client/server ==
2020-06-04 16:18:16.938  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 0 (100 records inserted/updated/deleted) is 53 Seconds
2020-06-04 16:19:04.259  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 1 (100 records inserted/updated/deleted) is 47 Seconds
2020-06-04 16:20:11.552  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 2 (100 records inserted/updated/deleted) is 67 Seconds
2020-06-04 16:20:57.738  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 3 (100 records inserted/updated/deleted) is 46 Seconds
2020-06-04 16:21:44.936  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 4 (100 records inserted/updated/deleted) is 47 Seconds
2020-06-04 16:22:31.418  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 5 (100 records inserted/updated/deleted) is 46 Seconds
2020-06-04 16:23:17.384  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 6 (100 records inserted/updated/deleted) is 45 Seconds
2020-06-04 16:24:02.293  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 7 (100 records inserted/updated/deleted) is 44 Seconds
2020-06-04 16:24:48.873  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 8 (100 records inserted/updated/deleted) is 46 Seconds
2020-06-04 16:25:34.895  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : Time spent for iteration 9 (100 records inserted/updated/deleted) is 46 Seconds
2020-06-04 16:25:34.895  INFO 9084 --- [  restartedMain] unk.test.rest.client.TestRunner          : === Performance test for REST client/server finished ==
Disconnected from the target VM, address: '127.0.0.1:56220', transport: 'socket'

Process finished with exit code 0

# Used materials

https://www.postgresql.org/
https://github.com/jOOQ/jOOQ
https://spring.io/guides/gs/consuming-rest
https://awesomeopensource.com/project/yidongnan/grpc-spring-boot-starter







