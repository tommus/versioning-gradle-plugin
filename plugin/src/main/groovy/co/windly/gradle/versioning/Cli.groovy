package co.windly.gradle.versioning

class Cli {

  /**
   * Executes command in shell.
   */
  static String execute(String cmd) {

    // Prepare output and error streams for command-line process.
    def output = new StringBuilder()
    def error = new StringBuilder()

    // Execute command as command-line process.
    def process = cmd.execute()

    // Consume output.
    process.consumeProcessOutput(output, error)

    // Await the process to finish.
    process.waitForOrKill(1_000)

    // Return trimmed output.
    return output.toString().trim()
  }
}
