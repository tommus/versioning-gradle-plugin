# Versioning Gradle Plugin
[![Maven Central][mavenbadge-svg]][mavencentral] [![GitHub][license-svg]][license]

A Gradle plugin that generates Android application version names and codes based
on repository details.

## Usage

### Add to the project's classpath

```groovy
dependencies {
  (...)
  classpath "co.windly:versioning-gradle-plugin:1.0.0"
}
```

### Apply plugin in a module

```groovy
apply plugin: "co.windly.versioning"
```

### Assign `versionCode` and `versionName`

```groovy
versionCode = versioning.generateVersionCode()
      versionName = versioning.generateVersionName()
```

## Version code policy

For master branch (aka production build) it'll be calculated from major, minor and
patch versions using the following formula: <major> * 1000000 + <minor> * 1000 + <patch>, eg.:

- 1000 (for 0.1.0),
- 3009 (for 0.3.9),
- 1000000 (for 1.0.0).

> Major, minor and patch numbers will be taken from latest master tag.

For other branches (aka development build) it'll be a plain number of commits
pushed to the active branch, eg.:

- 2251,
- 2253,
- 2282.

## Version name policy

For master branch (aka production build) it'll be formatted using the following
pattern "<major>.<minor>.<patch>, eg.:

- "0.1.0",
- "0.3.9",
- "1.0.0".

> Major, minor and patch numbers will be taken from latest master tag.

For other branches (aka development build) it'll be formatted using the
following pattern "<hash>-<date> (<count>)", eg.:

- g2cdd822-22.10.2020 (2251),
- gfc33cb6-22.10.2020 (2253),
- g707057f-29.10.2020 (2282).

> Hash is a hash string retrieved from latest commit to the current branch.
> Date refers to the present day.
> Count is a number of commits pushed to the current branch.

## Tasks

The following Gradle tasks are available in `versioning` group:

- showGitCommitsCount
- showGitHash
- showLatestTag
- showVersionCode
- showVersionInfo
- showVersionName

[license-svg]: https://img.shields.io/github/license/tommus/versioning-gradle-plugin.svg?color=97ca00
[license]: http://www.apache.org/licenses/LICENSE-2.0
[mavenbadge-svg]: https://img.shields.io/maven-central/v/co.windly/versioning-gradle-plugin.svg?color=97ca00
[mavencentral]: https://search.maven.org/artifact/co.windly/versioning-gradle-plugin
