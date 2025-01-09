package com.utmaximur.media

/**
 * Represents MIME types of attachments.
 */
enum class MimeType(val value: String) {

    VND("application/vnd"),
    SEVEN_Z("application/x-7z-compressed"),
    CSV("text/comma-separated-values"),
    DOC("application/msword"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    HTML("text/html"),
    MD("text/markdown"),
    ODT("application/vnd.oasis.opendocument.text"),
    PDF("application/pdf"),
    PPT("application/vnd.ms-powerpoint"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    RAR("application/vnd.rar"),
    RTF("application/rtf"),
    TAR("application/tar"),
    TXT("text/plain"),
    XLS("application/vnd.ms-excel"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    ZIP("application/zip"),
    MOV("video/mov"),
    QUICKTIME("quicktime"),
    VIDEO_QUICKTIME("video/quicktime"),
    MP4("mp4"),
    VIDEO_MP4("video/mp4"),
    AUDIO_MP4("audio/mp4"),
    MP3("audio/mp3"),
    AAC("audio/aac"),
    M4A("audio/m4a"),
    WAV("audio/wav"),
    MPEG("audio/mpeg"),
    GIF("image/gif"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    SVG("image/svg+xml"),
    WEBP("image/webp"),
    UNKNOWN("unknown");

    companion object {
        fun findByValue(value: String) = entries.find { it.value.contains(value) } ?: UNKNOWN
    }
}

fun MimeType.isAudio() = this == MimeType.AAC || this == MimeType.MP3 || this == MimeType.MPEG || this == MimeType.WAV

fun MimeType.isImage() = this == MimeType.GIF || this == MimeType.JPEG || this == MimeType.PNG