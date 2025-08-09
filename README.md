# ✨ SWTextedit

<p align="center">
  <img src="https://github.githubassets.com/images/icons/emoji/unicode/1f4dd.png" width="96" height="96" />
</p>

<p align="center">
  <b>SWTextedit</b> is a modern, cross-platform, and lightweight text editor built with Java and Eclipse SWT.<br>
  <i>Fast. Minimal. Open Source.</i>
</p>

<p align="center">
  <a href="https://github.com/seerainer/SWTextedit/releases"><img src="https://img.shields.io/github/v/release/seerainer/SWTextedit?style=flat-square" alt="Release"></a>
  <a href="LICENSE"><img src="https://img.shields.io/github/license/seerainer/SWTextedit?style=flat-square" alt="License"></a>
</p>

---

## 🚀 Features

- 🖥️ **Cross-platform**: Windows, Linux, macOS (Java 17+)
- 🌙 **Dark mode**: Adapts to system dark mode (Windows)
- 🔄 **Undo/Redo**: Huge stack for safe editing
- 🔍 **Find/Replace**: Powerful search dialog
- 🌐 **Encoding support**: ASCII, ISO, UTF-8, UTF-16, and more
- 🖋️ **Customizable font & colors**
- 🌏 **Multi-language**: English & German
- 🖨️ **Print support**
- 🖱️ **Drag & Drop** file opening
- 💼 **Portable**: No install needed

---

## 🛠️ Getting Started

### Prerequisites
- Java 17 or newer (Java 24 recommended)
- SWT libraries (auto-managed by Gradle)

### Build & Run

```sh
# Clone
$ git clone https://github.com/seerainer/SWTextedit.git
$ cd SWTextedit

# Build
$ ./gradlew build

# Run
$ java -jar build/libs/SWTextedit-<version>.jar
```

### 🏃 Native Image (Optional)
Build a native executable with GraalVM:
```sh
./gradlew nativeCompile
```
Find the binary in `build/native/nativeCompile/`.
