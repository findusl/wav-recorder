package de.findusl.wavrecorder

import kotlinx.io.Buffer

actual val platformRecorder: Recorder
	get() = object : Recorder {
		override val isAvailable: Boolean = false

		override fun startRecording() {
			TODO("Not yet implemented")
		}

		override fun stopRecording(): Result<Buffer> {
			TODO("Not yet implemented")
		}

		override fun close() {}
	}
