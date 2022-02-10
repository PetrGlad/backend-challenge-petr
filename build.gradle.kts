plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation("com.sparkjava:spark-core:2.9.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.zaxxer:HikariCP:3.4.2")

    runtimeOnly("org.slf4j:slf4j-simple:1.7.30")
}

application {
    mainClassName = "petrlgad.txapi.AppKt"
}
