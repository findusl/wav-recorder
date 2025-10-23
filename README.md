# Wav Recorder

[![Maven Central](https://img.shields.io/maven-central/v/de.findusl/wav-recorder)](https://central.sonatype.com/artifact/de.findusl/wav-recorder)
[![Java CI with Gradle](https://github.com/findusl/wav-recorder/actions/workflows/gradle.yml/badge.svg)](https://github.com/findusl/wav-recorder/actions/workflows/gradle.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Wav Recorder is a lightweight Kotlin Multiplatform library that captures audio from the system microphone and returns it as a WAV-formatted `kotlinx.io.Buffer`. It focuses on predictable WAV output for speech pipelines and AI-driven applications without bundling higher-level speech tooling.

## Features

- **Unified API** – obtain a `Recorder` instance via `platformRecorder`, start/stop, and receive a WAV buffer you can persist or stream.
- **Cross-platform implementations** – Android (API 24+), Windows, and macOS ship with working backends; other native targets currently fall back to `isAvailable = false`.
- **Built-in WAV conversion** – raw PCM data is converted to canonical 16-bit, mono WAV so you can drop it straight into transcription workflows.
- **Extensible error hooks** – implement `WavRecorderEventHandler` to observe initialisation and recording failures (useful for UI messaging or analytics).

## Getting Started

### Gradle Setup (Kotlin DSL)

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("de.findusl:wav-recorder:0.1.1")
        }
    }
}
```

Ensure `mavenCentral()` is present in your repository list.

### Quick Usage

```kotlin
import de.findusl.wavrecorder.platformRecorder
import kotlinx.io.readByteArray

val recorder = platformRecorder
require(recorder.isAvailable) { "Recording is not supported on this device." }

recorder.startRecording()
// ... wait or collect input ...
val wavBuffer = recorder.stopRecording().getOrThrow()
val wavBytes: ByteArray = wavBuffer.readByteArray()
recorder.close()
```

- Android callers must request `android.permission.RECORD_AUDIO` before invoking `startRecording()`.
- Always call `close()` when you no longer need the recorder to release microphone resources.

### Handling Errors

Implement `WavRecorderEventHandler` (assign to `WavRecorderEventHandler.INSTANCE`) to surface issues such as Windows initialisation problems or runtime recording failures:

```kotlin
WavRecorderEventHandler.INSTANCE = object : WavRecorderEventHandler {
    override fun failedToInitializeOnWindows(e: Exception) {
        // Show a toast, log to analytics, etc.
    }

    override fun errorWhileRecording(e: Exception) { /* ... */ }
}
```

## Platform Notes

- **Android** – Uses `AudioRecord`, dynamically picks a supported sample rate, outputs mono, 16-bit WAV.
- **Windows** – Relies on `javax.sound.sampled.TargetDataLine` with a 44.1 kHz mono configuration.
- **macOS** – Scans available audio devices and selects the first microphone-capable line with a specified sample rate.
- **Linux / other native targets** – Placeholder implementation; `isAvailable` returns `false` until native backends are added.

## Roadmap / Contributions

Planned improvements include native backends (Linux/iOS) and higher-level streaming helpers. Issues and pull requests are welcome—check the existing workflows in `.github/workflows` if you plan to extend CI or publishing.

## License

This project is licensed under the [MIT License](LICENSE).
