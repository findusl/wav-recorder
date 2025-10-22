package de.findusl.wavrecorder

import java.util.Locale

actual val platformRecorder: Recorder by lazy {
	val osName = System.getProperty("os.name")?.lowercase(Locale.getDefault()) ?: ""
	if ("win" in osName) WindowsAudioRecorder() else MacOsAudioRecorder()
}
