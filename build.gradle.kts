val mockitoVersion: String by project
val junitVersion: String by project
val logbackVersion: String by project

plugins {
    kotlin("jvm") version "1.4.20"
}

group "tech.pronghorn"
version "0.2.1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    testRuntimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//uploadArchives {
//    repositories {
//        mavenDeployer {
//            pom {
//                artifactId = 'common'
//            }
//            pom.project {
//                name 'Pronghorn Common'
//                packaging 'jar'
//                description 'Common features used across multiple pronghorn.tech projects.'
//                url 'https://pronghorn.tech'
//                scm {
//                    url 'https://github.com/pronghorn-tech/coroutines.git'
//                    connection 'scm:git@github.com:pronghorn-tech/coroutines.git'
//                    developerConnection 'scm:git@github.com:pronghorn-tech/coroutines.git'
//                }
//            }
//        }
//    }
//}