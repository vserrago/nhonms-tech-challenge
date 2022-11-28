plugins {
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
    "junit" to "5.9.0",
    "picocli" to "4.7.0",
)

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:${versions["jackson"]}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${versions["jackson"]}")
    implementation("info.picocli:picocli:${versions["picocli"]}")

    testImplementation("org.assertj:assertj-core:${versions["assertj"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions["junit"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${versions["junit"]}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}