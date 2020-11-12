# GWT Layout

![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg)  [![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Chat on Gitter](https://badges.gitter.im/hal/elemento.svg)](https://gitter.im/gwtproject/gwt-modules) ![CI](https://github.com/gwtproject/gwt-json/workflows/CI/badge.svg)

A future-proof port of the `com.google.gwt.json.JSON` GWT module, with no dependency on `gwt-user` (besides the Java Runtime Emulation), to prepare for GWT 3 / J2Cl.

##  Migrating from `com.google.gwt.json.Json`

1. Add the dependency to your build.

   For Maven:

   ```xml
   <dependency>
       <groupId>org.gwtproject.json</groupId>
       <artifactId>gwt-json</artifactId>
       <version>HEAD-SNAPSHOT</version>
   </dependency>
   ```

   For Gradle:

   ```gradle
   implementation("org.gwtproject.json:gwt-json:HEAD-SNAPSHOT")
   ```

2. Update your GWT module to use

   ```xml
   <inherits name="org.gwtproject.json.JSON" />
   ```

3. Change the `import`s in your Java source files:

   ```java
   import org.gwtproject.json.client.*;
   ```

## Instructions

To build gwt-json:

* run `mvn clean verify`

on the parent directory. This will build the artifact and run tests against the JVM, J2CL, and GWT2.

## System Requirements

**GWT JSON requires GWT 2.9.0 or newer!**


## Dependencies

GWT JSON depends on:

* gwt-core

