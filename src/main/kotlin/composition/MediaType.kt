package composition

interface MediaType{
    fun play()
    fun stop()
}

class AudioMediaType: MediaType{
    override fun play() {
        println("AudioMediaType Playing")
    }

    override fun stop() {
        println("AudioMediaType Stopped")
    }
}

class VideoMediaType: MediaType{
    override fun play() {
        println("VideoMediaType Playing")
    }

    override fun stop() {
        println("VideoMediaType Stopped")
    }
}