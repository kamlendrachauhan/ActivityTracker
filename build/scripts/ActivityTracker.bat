@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  ActivityTracker startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and ACTIVITY_TRACKER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\accelerometer-rest-to-cassandra-1.0.jar;%APP_HOME%\lib\spark-cassandra-connector-java-assembly-1.3.0-SNAPSHOT.jar;%APP_HOME%\lib\spring-boot-starter-web-1.2.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-jetty-1.2.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-actuator-1.2.3.RELEASE.jar;%APP_HOME%\lib\spring-data-cassandra-1.4.1.RELEASE.jar;%APP_HOME%\lib\spark-core_2.10-1.6.0.jar;%APP_HOME%\lib\spark-mllib_2.10-1.6.0.jar;%APP_HOME%\lib\spring-boot-starter-1.2.3.RELEASE.jar;%APP_HOME%\lib\jackson-databind-2.4.5.jar;%APP_HOME%\lib\hibernate-validator-5.1.3.Final.jar;%APP_HOME%\lib\spring-web-4.1.6.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-4.1.6.RELEASE.jar;%APP_HOME%\lib\jetty-jsp-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-webapp-9.2.9.v20150224.jar;%APP_HOME%\lib\websocket-server-9.2.9.v20150224.jar;%APP_HOME%\lib\javax-websocket-server-impl-9.2.9.v20150224.jar;%APP_HOME%\lib\spring-boot-actuator-1.2.3.RELEASE.jar;%APP_HOME%\lib\spring-cql-1.4.1.RELEASE.jar;%APP_HOME%\lib\spring-expression-4.2.5.RELEASE.jar;%APP_HOME%\lib\spring-data-commons-1.12.1.RELEASE.jar;%APP_HOME%\lib\cassandra-driver-dse-2.1.7.1.jar;%APP_HOME%\lib\slf4j-api-1.7.19.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.19.jar;%APP_HOME%\lib\avro-mapred-1.7.7-hadoop2.jar;%APP_HOME%\lib\chill_2.10-0.5.0.jar;%APP_HOME%\lib\chill-java-0.5.0.jar;%APP_HOME%\lib\xbean-asm5-shaded-4.4.jar;%APP_HOME%\lib\hadoop-client-2.2.0.jar;%APP_HOME%\lib\spark-launcher_2.10-1.6.0.jar;%APP_HOME%\lib\spark-network-common_2.10-1.6.0.jar;%APP_HOME%\lib\spark-network-shuffle_2.10-1.6.0.jar;%APP_HOME%\lib\spark-unsafe_2.10-1.6.0.jar;%APP_HOME%\lib\jets3t-0.7.1.jar;%APP_HOME%\lib\curator-recipes-2.4.0.jar;%APP_HOME%\lib\javax.servlet-3.0.0.v201112011016.jar;%APP_HOME%\lib\commons-lang3-3.3.2.jar;%APP_HOME%\lib\commons-math3-3.4.1.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.10.jar;%APP_HOME%\lib\compress-lzf-1.0.3.jar;%APP_HOME%\lib\snappy-java-1.1.2.jar;%APP_HOME%\lib\lz4-1.3.0.jar;%APP_HOME%\lib\RoaringBitmap-0.5.11.jar;%APP_HOME%\lib\akka-remote_2.10-2.3.11.jar;%APP_HOME%\lib\akka-slf4j_2.10-2.3.11.jar;%APP_HOME%\lib\scala-library-2.10.5.jar;%APP_HOME%\lib\json4s-jackson_2.10-3.2.10.jar;%APP_HOME%\lib\jersey-server-1.9.jar;%APP_HOME%\lib\jersey-core-1.9.jar;%APP_HOME%\lib\mesos-0.21.1-shaded-protobuf.jar;%APP_HOME%\lib\netty-all-4.0.29.Final.jar;%APP_HOME%\lib\stream-2.7.0.jar;%APP_HOME%\lib\metrics-core-3.1.2.jar;%APP_HOME%\lib\metrics-jvm-3.1.2.jar;%APP_HOME%\lib\metrics-json-3.1.2.jar;%APP_HOME%\lib\metrics-graphite-3.1.2.jar;%APP_HOME%\lib\jackson-module-scala_2.10-2.4.4.jar;%APP_HOME%\lib\ivy-2.4.0.jar;%APP_HOME%\lib\oro-2.0.8.jar;%APP_HOME%\lib\tachyon-client-0.8.2.jar;%APP_HOME%\lib\pyrolite-4.9.jar;%APP_HOME%\lib\py4j-0.9.jar;%APP_HOME%\lib\unused-1.0.0.jar;%APP_HOME%\lib\spark-streaming_2.10-1.6.0.jar;%APP_HOME%\lib\spark-sql_2.10-1.6.0.jar;%APP_HOME%\lib\spark-graphx_2.10-1.6.0.jar;%APP_HOME%\lib\breeze_2.10-0.11.2.jar;%APP_HOME%\lib\pmml-model-1.1.15.jar;%APP_HOME%\lib\netty-3.9.0.Final.jar;%APP_HOME%\lib\metrics-core-3.0.2.jar;%APP_HOME%\lib\spring-boot-1.2.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-autoconfigure-1.2.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-logging-1.2.3.RELEASE.jar;%APP_HOME%\lib\snakeyaml-1.14.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\jboss-logging-3.1.3.GA.jar;%APP_HOME%\lib\classmate-1.0.0.jar;%APP_HOME%\lib\jetty-schemas-3.1.M0.jar;%APP_HOME%\lib\javax.servlet-api-3.1.0.jar;%APP_HOME%\lib\javax.servlet.jsp-api-2.3.1.jar;%APP_HOME%\lib\javax.servlet.jsp-2.3.2.jar;%APP_HOME%\lib\javax.servlet.jsp.jstl-1.2.0.v201105211821.jar;%APP_HOME%\lib\javax.servlet.jsp.jstl-1.2.2.jar;%APP_HOME%\lib\org.eclipse.jdt.core-3.8.2.v20130121.jar;%APP_HOME%\lib\jetty-xml-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-servlet-9.2.9.v20150224.jar;%APP_HOME%\lib\websocket-common-9.2.9.v20150224.jar;%APP_HOME%\lib\websocket-client-9.2.9.v20150224.jar;%APP_HOME%\lib\websocket-servlet-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-http-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-annotations-9.2.9.v20150224.jar;%APP_HOME%\lib\javax-websocket-client-impl-9.2.9.v20150224.jar;%APP_HOME%\lib\javax.websocket-api-1.0.jar;%APP_HOME%\lib\spring-tx-4.2.5.RELEASE.jar;%APP_HOME%\lib\avro-ipc-1.7.7.jar;%APP_HOME%\lib\avro-ipc-1.7.7-tests.jar;%APP_HOME%\lib\jackson-core-asl-1.9.13.jar;%APP_HOME%\lib\jackson-mapper-asl-1.9.13.jar;%APP_HOME%\lib\kryo-2.21.jar;%APP_HOME%\lib\hadoop-common-2.2.0.jar;%APP_HOME%\lib\hadoop-hdfs-2.2.0.jar;%APP_HOME%\lib\hadoop-mapreduce-client-app-2.2.0.jar;%APP_HOME%\lib\hadoop-yarn-api-2.2.0.jar;%APP_HOME%\lib\hadoop-mapreduce-client-core-2.2.0.jar;%APP_HOME%\lib\hadoop-mapreduce-client-jobclient-2.2.0.jar;%APP_HOME%\lib\hadoop-annotations-2.2.0.jar;%APP_HOME%\lib\leveldbjni-all-1.8.jar;%APP_HOME%\lib\jackson-annotations-2.4.4.jar;%APP_HOME%\lib\commons-httpclient-3.1.jar;%APP_HOME%\lib\curator-framework-2.4.0.jar;%APP_HOME%\lib\zookeeper-3.4.5.jar;%APP_HOME%\lib\akka-actor_2.10-2.3.11.jar;%APP_HOME%\lib\protobuf-java-2.5.0.jar;%APP_HOME%\lib\uncommons-maths-1.2.2a.jar;%APP_HOME%\lib\json4s-core_2.10-3.2.10.jar;%APP_HOME%\lib\asm-3.1.jar;%APP_HOME%\lib\paranamer-2.6.jar;%APP_HOME%\lib\commons-io-2.4.jar;%APP_HOME%\lib\tachyon-underfs-hdfs-0.8.2.jar;%APP_HOME%\lib\tachyon-underfs-s3-0.8.2.jar;%APP_HOME%\lib\tachyon-underfs-local-0.8.2.jar;%APP_HOME%\lib\spark-catalyst_2.10-1.6.0.jar;%APP_HOME%\lib\parquet-column-1.7.0.jar;%APP_HOME%\lib\parquet-hadoop-1.7.0.jar;%APP_HOME%\lib\core-1.1.2.jar;%APP_HOME%\lib\arpack_combined_all-0.1.jar;%APP_HOME%\lib\breeze-macros_2.10-0.11.2.jar;%APP_HOME%\lib\opencsv-2.3.jar;%APP_HOME%\lib\jtransforms-2.4.0.jar;%APP_HOME%\lib\spire_2.10-0.7.4.jar;%APP_HOME%\lib\pmml-agent-1.1.15.jar;%APP_HOME%\lib\pmml-schema-1.1.15.jar;%APP_HOME%\lib\jaxb-impl-2.2.7.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.11.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\jetty-util-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-security-9.2.9.v20150224.jar;%APP_HOME%\lib\websocket-api-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-io-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-plus-9.2.9.v20150224.jar;%APP_HOME%\lib\javax.annotation-api-1.2.jar;%APP_HOME%\lib\asm-5.0.1.jar;%APP_HOME%\lib\asm-commons-5.0.1.jar;%APP_HOME%\lib\avro-1.7.7.jar;%APP_HOME%\lib\reflectasm-1.07-shaded.jar;%APP_HOME%\lib\minlog-1.2.jar;%APP_HOME%\lib\objenesis-1.2.jar;%APP_HOME%\lib\commons-cli-1.2.jar;%APP_HOME%\lib\commons-math-2.1.jar;%APP_HOME%\lib\xmlenc-0.52.jar;%APP_HOME%\lib\commons-configuration-1.6.jar;%APP_HOME%\lib\hadoop-auth-2.2.0.jar;%APP_HOME%\lib\commons-compress-1.4.1.jar;%APP_HOME%\lib\jetty-util-6.1.26.jar;%APP_HOME%\lib\hadoop-mapreduce-client-common-2.2.0.jar;%APP_HOME%\lib\hadoop-mapreduce-client-shuffle-2.2.0.jar;%APP_HOME%\lib\hadoop-yarn-common-2.2.0.jar;%APP_HOME%\lib\curator-client-2.4.0.jar;%APP_HOME%\lib\jline-0.9.94.jar;%APP_HOME%\lib\config-1.2.1.jar;%APP_HOME%\lib\json4s-ast_2.10-3.2.10.jar;%APP_HOME%\lib\scalap-2.10.0.jar;%APP_HOME%\lib\janino-2.7.8.jar;%APP_HOME%\lib\parquet-common-1.7.0.jar;%APP_HOME%\lib\parquet-encoding-1.7.0.jar;%APP_HOME%\lib\parquet-format-2.3.0-incubating.jar;%APP_HOME%\lib\parquet-jackson-1.7.0.jar;%APP_HOME%\lib\spire-macros_2.10-0.7.4.jar;%APP_HOME%\lib\jaxb-core-2.2.7.jar;%APP_HOME%\lib\jetty-server-9.2.9.v20150224.jar;%APP_HOME%\lib\jetty-jndi-9.2.9.v20150224.jar;%APP_HOME%\lib\asm-tree-5.0.1.jar;%APP_HOME%\lib\commons-collections-3.2.1.jar;%APP_HOME%\lib\commons-digester-1.8.jar;%APP_HOME%\lib\commons-beanutils-core-1.8.0.jar;%APP_HOME%\lib\xz-1.0.jar;%APP_HOME%\lib\hadoop-yarn-client-2.2.0.jar;%APP_HOME%\lib\hadoop-yarn-server-common-2.2.0.jar;%APP_HOME%\lib\hadoop-yarn-server-nodemanager-2.2.0.jar;%APP_HOME%\lib\guice-3.0.jar;%APP_HOME%\lib\jersey-test-framework-grizzly2-1.9.jar;%APP_HOME%\lib\jersey-json-1.9.jar;%APP_HOME%\lib\jersey-guice-1.9.jar;%APP_HOME%\lib\scala-compiler-2.10.0.jar;%APP_HOME%\lib\commons-compiler-2.7.8.jar;%APP_HOME%\lib\parquet-generator-1.7.0.jar;%APP_HOME%\lib\jaxb-api-2.2.7.jar;%APP_HOME%\lib\commons-beanutils-1.7.0.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\cglib-2.2.1-v20090111.jar;%APP_HOME%\lib\jersey-test-framework-core-1.9.jar;%APP_HOME%\lib\jersey-grizzly2-1.9.jar;%APP_HOME%\lib\jettison-1.1.jar;%APP_HOME%\lib\jackson-jaxrs-1.8.3.jar;%APP_HOME%\lib\jackson-xc-1.8.3.jar;%APP_HOME%\lib\jersey-client-1.9.jar;%APP_HOME%\lib\grizzly-http-2.1.2.jar;%APP_HOME%\lib\grizzly-http-server-2.1.2.jar;%APP_HOME%\lib\grizzly-http-servlet-2.1.2.jar;%APP_HOME%\lib\javax.servlet-3.1.jar;%APP_HOME%\lib\stax-api-1.0.1.jar;%APP_HOME%\lib\grizzly-framework-2.1.2.jar;%APP_HOME%\lib\grizzly-rcm-2.1.2.jar;%APP_HOME%\lib\gmbal-api-only-3.0.0-b023.jar;%APP_HOME%\lib\management-api-3.0.0-b012.jar;%APP_HOME%\lib\spring-context-4.2.5.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.2.5.RELEASE.jar;%APP_HOME%\lib\spring-core-4.2.5.RELEASE.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\guava-19.0.jar;%APP_HOME%\lib\cassandra-driver-core-2.1.7.1.jar;%APP_HOME%\lib\netty-handler-4.0.27.Final.jar;%APP_HOME%\lib\netty-buffer-4.0.27.Final.jar;%APP_HOME%\lib\netty-transport-4.0.27.Final.jar;%APP_HOME%\lib\netty-codec-4.0.27.Final.jar;%APP_HOME%\lib\netty-common-4.0.27.Final.jar;%APP_HOME%\lib\jsr305-2.0.1.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.11.jar;%APP_HOME%\lib\javax.el-3.0.1-b08.jar;%APP_HOME%\lib\commons-codec-1.5.jar;%APP_HOME%\lib\commons-net-3.1.jar;%APP_HOME%\lib\commons-lang-2.5.jar;%APP_HOME%\lib\scala-reflect-2.10.5.jar;%APP_HOME%\lib\quasiquotes_2.10-2.0.0.jar;%APP_HOME%\lib\FastInfoset-1.2.12.jar;%APP_HOME%\lib\istack-commons-runtime-2.16.jar;%APP_HOME%\lib\jsr173_api-1.0.jar;%APP_HOME%\lib\jackson-core-2.4.5.jar;%APP_HOME%\lib\spring-aop-4.2.5.RELEASE.jar

@rem Execute ActivityTracker
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %ACTIVITY_TRACKER_OPTS%  -classpath "%CLASSPATH%" com.human.activity.rest.application.Application %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable ACTIVITY_TRACKER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%ACTIVITY_TRACKER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
