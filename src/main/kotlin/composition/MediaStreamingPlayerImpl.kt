package composition

class MediaStreamingPlayerImpl(
    private val mediaType: MediaType,
    private val streamingSource: StreamingSource
): MediaStreamingPlayer, MediaType by mediaType, StreamingSource by streamingSource {

    fun playMedia() {
        play()
        getSource()
        stop()
        println("Stop MediaStreamingPlayer")
    }
}