/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

apply(plugin = PLUGIN_JAVA_LIBRARY)
apply(plugin = PLUGIN_MAVEN_PUBLISH)
//apply(plugin = PLUGIN_BINTRAY)

project.group = publishingConfig.releaseGroupId
project.version = publishingConfig.releaseVersion

configure<JavaPluginExtension> {
    withJavadocJar()
    withSourcesJar()
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("MyPublication") {
            from(components["java"])
            groupId = publishingConfig.releaseGroupId
            artifactId = publishingConfig.releaseArtifact
            version = publishingConfig.releaseVersion

            pom {
                name.set(publishingConfig.releaseArtifact)
                description.set(publishingConfig.releaseDescription)
                url.set(publishingConfig.releaseWebsite)

                licenses {
                    license {
                        name.set(publishingConfig.licenseName)
                        url.set(publishingConfig.licenseUrl)
                    }
                }

                developers {
                    developer {
                        id.set(publishingConfig.developerId)
                        name.set(publishingConfig.developerName)
                        email.set(publishingConfig.developerEmail)
                    }
                }

                scm {
                    connection.set(publishingConfig.gitUrl)
                    developerConnection.set(publishingConfig.gitUrl)
                    url.set(publishingConfig.siteUrl)
                }
            }
        }
    }
}

tasks {
    named<org.gradle.api.tasks.javadoc.Javadoc>("javadoc") {
        if(JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
}

// https://github.com/bintray/gradle-bintray-plugin/issues/301
// Unfortunately, the provided solution does not work at the
// moment, since another error with text "Unresolved reference: jfrog"
// pops and there seems to be nothing I can do about it.
/*configure<com.jfrog.bintray.gradle.BintrayExtension> {
    user = property("bintrayUser", "")
    key = property("bintrayApiKey", "")

    setPublications("MyPublication")

    with(pkg) {
        repo = publishingConfig.releaseRepoName
        name = publishingConfig.releaseArtifact
        desc = publishingConfig.releaseDescription
        websiteUrl = publishingConfig.siteUrl
        vcsUrl = publishingConfig.gitUrl
        issueTrackerUrl = publishingConfig.issueTracker
        githubReleaseNotesFile = publishingConfig.releaseNotesFile
        setLicenses(publishingConfig.allLicenses)
        dryRun = true
        publish = true
        override = false
        publicDownloadNumbers = false
    }
}*/