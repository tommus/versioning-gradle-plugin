package co.windly.gradle.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project

class VersioningPlugin implements Plugin<Project> {

  //region Apply

  final static EXTENSION_NAME = "versioning"

  void apply(Project project) {

    // Create named extension.
    def extension = project.extensions
        .create(EXTENSION_NAME, VersioningPluginExtension)

    // Create git-related tasks.
    createShowLatestTagTask(project, extension)
    createShowGitCommitCount(project, extension)

    // Create versioning tasks.
    createShowGitHashTask(project, extension)
    createShowVersionCode(project, extension)
    createShowVersionInfoTask(project, extension)
    createShowVersionName(project, extension)
  }

  //endregion

  //region Show Git Commit Count

  /**
   * Create a new task that shows git commits count.
   */
  static def createShowGitCommitCount(Project project, VersioningPluginExtension extension) {

    // Add new task to the project.
    def task = project.task("showGitCommitsCount") {
      doLast {

        // Show git hash.
        println("Git commits count: ${extension.getGitCommitsCount()}.")
      }
    }

    // Define task group.
    task.group = EXTENSION_NAME
  }

  //endregion

  //region Show Git Hash

  /**
   * Create a new task that shows git hash.
   */
  static def createShowGitHashTask(Project project, VersioningPluginExtension extension) {

    // Add new task to the project.
    def task = project.task("showGitHash") {
      doLast {

        // Show git hash.
        println("Git hash: ${extension.getGitHash()}.")
      }
    }

    // Define task group.
    task.group = EXTENSION_NAME
  }

  //endregion

  //region Show Latest Tag

  /**
   * Create a new task that shows latest tag.
   */
  static def createShowLatestTagTask(Project project, VersioningPluginExtension extension) {

    // Add new task to the project.
    def task = project.task("showLatestTag") {
      doLast {

        // Show additional information related to a/m tag.
        println("Latest tag: ${extension.getLatestTag()}.")
      }
    }

    // Define task group.
    task.group = EXTENSION_NAME
  }

  //endregion

  //region Show Version Code

  /**
   * Create a new task that shows version code.
   */
  static def createShowVersionCode(Project project, VersioningPluginExtension extension) {

    // Add new task to the project.
    def task = project.task("showVersionCode") {
      doLast {

        // Show version details.
        println("Version code: ${extension.generateVersionCode()}.")
      }
    }

    // Define task group.
    task.group = EXTENSION_NAME
  }

  //endregion

  //region Show Version Info

  /**
   * Create a new task that shows versioning info.
   */
  static def createShowVersionInfoTask(Project project, VersioningPluginExtension extension) {

    // Add new task to the project.
    def task = project.task("showVersionInfo") {
      doLast {

        // Show current branch name.
        println("Current branch name: ${extension.getCurrentBranchName()}.")

        // Show version details.
        println("Version name: ${extension.generateVersionName()}.")
        println("Version code: ${extension.generateVersionCode()}.")

        // Show additional information related to a/m tag.
        println("Latest tag: ${extension.getLatestTag()}.")
      }
    }

    // Define task group.
    task.group = EXTENSION_NAME
  }

  //endregion

  //region Show Version Name

  /**
   * Create a new task that shows version name.
   */
  static def createShowVersionName(Project project, VersioningPluginExtension extension) {

    // Add new task to the project.
    def task = project.task("showVersionName") {
      doLast {

        // Show version details.
        println("Version name: ${extension.generateVersionName()}.")
      }
    }

    // Define task group.
    task.group = EXTENSION_NAME
  }

  //endregion
}
