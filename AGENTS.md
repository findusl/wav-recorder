# AGENTS

## Commits
- Group commits by topic.
- Documentation-only changes must be separate from code changes.
- Refactors and renames must be separate from behavioral changes.

## Verification
1. Run `./gradlew ktlintCheck`.
2. Then run `./gradlew check`.

`./gradlew check` should compile all configured targets (Android, iOS, JVM, native) on macOS when the required build tools are installed.
