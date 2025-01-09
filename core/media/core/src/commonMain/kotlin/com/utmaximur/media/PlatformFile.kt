package com.utmaximur.media

expect class PlatformFile {

    /**
     * Reads the name of the PlatformFile.
     * @return The name of the file as a string.
     */
    val name: String

    /**
     * Reads the path of the PlatformFile.
     * @return The path of the file as a string.
     */
    val path: String?

    /**
     * Reads the URI to String of the PlatformFile.
     */
    val uriString: String

    /*
     * Reads the MIME typeof the PlatformFile as a String.
     */
    val mimeType: String?

    /**
     * Reads the content of the PlatformFile as a byte array.
     * @return The content of the file as a byte array.
     */
    suspend fun readBytes(): ByteArray

    /**
     * Reads the size of the PlatformFile.
     * If the file is a directory, the size of the directory is returned.
     */
    fun getSize(): Long?
}

val PlatformFile.baseName: String
    get() = name.substringBeforeLast(".", name)

val PlatformFile.extension: String
    get() = name.substringAfterLast(".")

typealias PlatformFiles = List<PlatformFile>