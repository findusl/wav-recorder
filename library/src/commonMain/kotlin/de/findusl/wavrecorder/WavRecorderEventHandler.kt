package de.findusl.wavrecorder

interface WavRecorderEventHandler {
	companion object {
		var INSTANCE: WavRecorderEventHandler? = null
	}

	fun failedToInitializeOnWindows(e: Exception) {}
	fun errorWhileRecording(e: Exception) {}
}

