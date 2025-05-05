package composition.media

interface StreamingSource{
    fun getSource()
}

class NetWorkStreamingSource: StreamingSource {
    override fun getSource() {
        println("You Use NetWorkStreamingSource")
    }
}

class LocalStreamingSource: StreamingSource {
    override fun getSource() {
        println("You Use LocalStreamingSource")
    }
}

