package de.findusl.wavrecorder

actual val platformRecorder: Recorder by lazy { AudioRecorder() }
