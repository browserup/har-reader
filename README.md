HAR reader
==========

Read [HTTP Archives](http://www.softwareishard.com/blog/har-12-spec/) with Java.

```
<dependency>
  <groupId>com.browserup</groupId>
  <artifactId>har-reader</artifactId>
  <version>3.0.0</version>
</dependency>
```

[![Build Status](https://travis-ci.org/sdstoehr/har-reader.svg?branch=master)](https://travis-ci.org/sdstoehr/har-reader)
[![Coverage Status](https://coveralls.io/repos/github/sdstoehr/har-reader/badge.svg?branch=master)](https://coveralls.io/github/sdstoehr/har-reader?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/com.browserup/har-reader.svg)](http://mvnrepository.com/artifact/com.browserup/har-reader)

## Usage

Reading HAR from File:

```java
HarReader harReader = new HarReader();
Har har = harReader.readFromFile(new File("myhar.har"));
System.out.println(har.getLog().getCreator().getName());
```

Reading HAR from String:

```java
HarReader harReader = new HarReader();
Har har = harReader.readFromString("{ ... HAR-JSON-Data ... }");
```

### Customizing HAR reader

As of version 2.0.0 you can create your own `MapperFactory` [(DefaultMapperFactory)](src/main/java/com/browserup/harreader/jackson/DefaultMapperFactory.java)

 
```java
public class MyMapperFactory implements MapperFactory {
    public ObjectMapper instance(HarReaderMode mode) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        
        // configure Jackson object mapper as needed

        mapper.registerModule(module);
        return mapper;
    }
}
```

You can now use your configuration by instantiating the `HarReader` with your `MapperFactory`:

```java
HarReader harReader = new HarReader(new MyMapperFactory());
```

### Building and Testing

```bash
mvn compile
mvn test
mvn package
```

## Latest Releases

### 3.0.0

* Forked from [https://github.com/sdstoehr/har-reader](https://github.com/sdstoehr/har-reader)
* Renamed package root to `com.browserup`
