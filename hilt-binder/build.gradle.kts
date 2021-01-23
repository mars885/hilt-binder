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

plugins {
    kotlin()
    bintray()
}

dependencies {
    implementation(deps.vanillaDagger)
}

publishingConfig.releaseArtifact = publishingConfig.hiltBinderArtifact
publishingConfig.releaseVersion = publishingConfig.hiltBinderArtifactVersion
publishingConfig.releaseDescription = publishingConfig.hiltBinderArtifactDesc

apply(from = "../publishing.gradle.kts")

// Ideally, this should go into the publishing.gradle.kts file,
// but due to an existing bug, this configuration has to be duplicated
// in every distributable artifact's build.gradle.kts file.
bintray {
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
        dryRun = false
        publish = true
        override = false
        publicDownloadNumbers = false
    }
}