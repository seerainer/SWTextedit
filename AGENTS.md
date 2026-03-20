# AGENTS.md

## Project Overview

SWTextedit is a simple text editor application built with Java and SWT (Standard Widget Toolkit).
This repository contains the source code for the application, which is built using Gradle.

## Build and Run

### Prerequisites

*   Java Development Kit (JDK) 25 or compatible.
*   Gradle (provided via wrapper `./gradlew`).

### Commands

*   **Build the project:**
    ```bash
    ./gradlew build
    ```

*   **Run the application:**
    ```bash
    ./gradlew run
    ```
    *Note: On macOS, the application runs with `-XstartOnFirstThread` automatically.*

*   **Clean the build:**
    ```bash
    ./gradlew clean
    ```

*   **Create Native Image (GraalVM):**
    ```bash
    ./gradlew nativeCompile
    ```

*   **Tests:**
    Currently, there are no explicit test tasks configured or test source files visible in the standard `src/test` location.
    If tests are added, they should be placed in `src/test/java`.
    To run tests (if they existed):
    ```bash
    ./gradlew test
    ```

## Code Style & Conventions

### Java

*   **Language Level:** Java 25.
*   **Package Naming:** `io.github.seerainer.swtextedit` and subpackages (`config`, `dialog`, `util`, `widgets`, etc.).
*   **Formatting:**
    *   Use 4 spaces for indentation (implied from typical Java projects, though check existing files if editing).
    *   Standard Java bracing style (OTBS).
*   **Imports:**
    *   Organize imports alphabetically.
    *   Separate `java.*`/`javax.*` from third-party libraries (like `org.eclipse.swt.*`) and project imports.
    *   Avoid wildcard imports (`import foo.*;`) unless necessary for static imports.
*   **Naming:**
    *   Classes: PascalCase (e.g., `SWTextedit`, `ConfigData`).
    *   Methods/Variables: camelCase (e.g., `openClose`, `guiWidgets`).
    *   Constants: UPPER_SNAKE_CASE (e.g., `LOG`, `START`).
*   **Logging:**
    *   Use `java.util.logging.Logger`.
    *   Instance: `private static final Logger LOG = Logger.getLogger(ClassName.class.getName());`.
*   **SWT Usage:**
    *   The project heavily relies on SWT. Ensure all SWT UI updates happen on the UI thread.
    *   Dispose of resources (Colors, Fonts, Images) properly if they are not managed by the system.
*   **Final Keyword:**
    *   The code uses `final` extensively for parameters and local variables (e.g., `final String file`, `final var shell`). Maintain this style.
*   **Type Inference:**
    *   Use `var` for local variable type inference where the type is obvious (e.g., `final var shell = guiWidgets.getShell();`).

### Project Structure

*   `src/io/github/seerainer/swtextedit/`: Main source code.
    *   `widgets/`: Contains UI widget wrappers/implementations.
    *   `util/`: Utility classes for common operations.
    *   `config/`: Configuration handling.
    *   `dialog/`: Dialog implementations (About, Find/Replace, etc.).
*   `resources/`: Contains non-code assets like images (`.png`, `.gif`) and localization files (`.properties`).

## Error Handling

*   Use standard Java `try-catch` blocks.
*   Log exceptions using the `Logger` instance.
*   Avoid swallowing exceptions without logging or handling.

## Agent Instructions

*   **When adding new features:** Follow the existing pattern of separating logic into `widgets`, `util`, or `dialog` packages.
*   **When modifying UI:** Be mindful of SWT threading rules.
*   **When writing file I/O:** Use standard Java NIO or the project's utility classes if available.
*   **Resource Management:** If you allocate an SWT Resource, you must dispose of it.
