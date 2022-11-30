plugins {
    id("com.github.johnrengelman.shadow").version("7.1.2")
    id("java")
}

group = "net.serrago"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val versions = mapOf(
    "assertj" to "3.23.1",
    "jackson" to "2.14.0",
    "jimfs" to "1.2",
    "junit" to "5.9.0",
    "mockito" to "4.9.0",
    "picocli" to "4.7.0",
)

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:${versions["jackson"]}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${versions["jackson"]}")
    implementation("info.picocli:picocli:${versions["picocli"]}")

    testImplementation("org.assertj:assertj-core:${versions["assertj"]}")
    testImplementation("com.google.jimfs:jimfs:${versions["jimfs"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions["junit"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${versions["junit"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${versions["junit"]}")
    testImplementation("org.mockito:mockito-core:${versions["mockito"]}")
    testImplementation("org.mockito:mockito-junit-jupiter:${versions["mockito"]}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.jar {
    manifest.attributes["Main-Class"] = "net.serrago.ntc.OidFilter"
}