/**
 * = vertx-wiremongo
 * :toc: left
 *
 * vertx-wiremongo is a client to be used with Vert.x.
 *
 * == Using vertx-wiremongo
 *
 * To use the client, add the following dependency to the _dependencies_ section of your build descriptor:
 *
 * * Maven (in your `pom.xml`):
 *
 * [source,xml,subs="+attributes"]
 * ----
 * <dependency>
 *   <groupId>${maven.groupId}</groupId>
 *   <artifactId>${maven.artifactId}</artifactId>
 *   <version>${maven.version}</version>
 * </dependency>
 * ----
 *
 * * Gradle (in your `build.gradle` file):
 *
 * [source,groovy,subs="+attributes"]
 * ----
 * compile '${maven.groupId}:${maven.artifactId}:${maven.version}'
 * ----
 *
 */

@ModuleGen(name = "vertx-wiremongo", groupPackage = "com.noenv.wiremongo")
package com.noenv.wiremongo;

import io.vertx.codegen.annotations.ModuleGen;
