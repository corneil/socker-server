# Simple Socket Server

## Build

This will build an application jar and a native version using GraalVM

```shell
./gradlew build nativeCompile
```
Remove nativeCompile if you don't have GraalVM installed or don't require a native image.

## Execute

The default value for _**port**_ is 9000

## Application Jar

```shell
java -jar ./build/libs/socket-server-1.0-SNAPSHOT.jar [port]
```

## Distribution
Expand `build/distributions/socket-server-1.0-SNAPSHOT.zip` or `build/distributions/socket-server-1.0-SNAPSHOT.tar`

```shell
cd build/distributions
unzip socket-server-1.0-SNAPSHOT.zip
cd socket-server-1.0-SNAPSHOT/bin
./socket-server [port]
```
## Native

```shell
./build/native/nativeCompile/socket-server [port]
```


