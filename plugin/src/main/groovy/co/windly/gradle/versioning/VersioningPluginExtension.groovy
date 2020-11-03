package co.windly.gradle.versioning

import java.text.SimpleDateFormat

class VersioningPluginExtension {

  //region Version Code

  /**
   * Generates version code.
   *
   * For master branch (aka production build) it'll be calculated from major,
   * minor and patch versions using the following formula:
   * <major> * 1000000 + <minor> * 1000 + <patch>, eg.:
   * - 1000 (for 0.1.0),
   * - 3009 (for 0.3.9),
   * - 1000000 (for 1.0.0).
   *
   * Major, minor and patch numbers will be taken from latest master tag.
   *
   * For other branches (aka development build) it'll be a plain number of
   * commits pushed to the active branch, eg.:
   * - 2251,
   * - 2253,
   * - 2282.
   */
  static int generateVersionCode() {

    // Retrieve a current branch name.
    def branch = getCurrentBranchName()

    // Format production ready version code for master branch.
    if (branch == "master") {
      return productionVersionCode()
    }

    // Format development ready version code for all other branches.
    return developmentVersionCode()
  }

  private static int productionVersionCode() {

    // Retrieve most recent master's tag versioning info.
    def (major, minor, patch, build, sha) = getCurrentBranchLatestTag()

    // Format versioning info as production ready version code.
    return (major.toInteger() * 1_000_000) + (minor.toInteger() * 1_000) + patch.toInteger()
  }

  private static def developmentVersionCode() {

    // Return a number of commits from start up to a commit.
    def count = getGitCommitsCount()

    // Format a number of commits as development ready version code.
    return (count == null || count.empty) ? 0 : count.toInteger()
  }

  //endregion

  //region Version Name

  /**
   * Generates human-readable version name.
   *
   * For master branch (aka production build) it'll be formatted using the
   * following pattern "<major>.<minor>.<patch>, eg.:
   * - "0.1.0",
   * - "0.3.9",
   * - "1.0.0".
   *
   * Major, minor and patch numbers will be taken from latest master tag.
   *
   * For other branches (aka development build) it'll be formatted using the
   * following pattern "<hash>-<date> (<count>)", eg.:
   * - g2cdd822-22.10.2020 (2251),
   * - gfc33cb6-22.10.2020 (2253),
   * - g707057f-29.10.2020 (2282).
   *
   * Hash is a hash string retrieved from latest commit to the current branch.
   * Date refers to the present day. Count is a number of commits pushed to the
   * current branch.
   */
  static String generateVersionName() {

    // Retrieve most recent master's tag versioning info.
    def (major, minor, patch, _, sha) = getCurrentBranchLatestTag()

    // Generate development / production version code.
    def code = generateVersionCode()

    // Snapshot a current timestamp.
    def date = generateBuildDate()

    // Return a development / production version name.
    return (getCurrentBranchName() == "master") ? "${major}.${minor}.${patch}" :
        "${sha}-${date} ($code)"
  }

  //endregion

  //region Git

  static String getGitHash() {

    // Retrieve a most recent commit hash.
    return Cli.execute("git rev-parse --short HEAD")
  }

  static String getCurrentBranchName() {

    // Retrieve a current branch name.
    return Cli.execute("git rev-parse --abbrev-ref HEAD")
  }

  static String getLatestTag() {

    // Retrieve the most recent tag that is reachable from a commit.
    return Cli.execute("git describe --tags").split("-")[0] ?: "0.0.0"
  }

  private static Object getCurrentBranchLatestTag() {

    // Retrieve the most recent tag that is reachable from a commit.
    def name = "git describe ${getCurrentBranchName()} --tags --long"
        .execute()
        .text
        .replace("v", "")
        .trim()

    // Split description so it's possible to get a tag.
    def (tag, build, sha) = name.tokenize('-')

    // Extract major, minor and patch versions from a tag.
    def (major, minor, patch) = (tag != null) ? tag.tokenize('.') : [0, 0, 0]

    // Return a collection of versioning information.
    return [major, minor, patch, build, sha]
  }

  static def getGitCommitsCount() {

    // Return a number of commits from start up to a commit.
    return ("git rev-list ${getCurrentBranchName()} --count").execute().text.trim()
  }

  //endregion

  //region Misc

  static String generateBuildDate() {

    // Prepare expected date format.
    def df = new SimpleDateFormat("dd.MM.yyyy")

    // Use UTC as selected time zone.
    df.setTimeZone(TimeZone.getTimeZone("UTC"))

    //
    return df.format(new Date())
  }

  //endregion
}
