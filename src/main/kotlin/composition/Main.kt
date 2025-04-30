package composition

class Main

fun main(args: Array<String>) {

    //MediaType 구현체
    val audioMediaType = AudioMediaType()
    val videoMediaType = VideoMediaType()

    //local Streaming Source 구현체
    val localStreamingSource = LocalStreamingSource()
    val netWorkStreamingSource = NetWorkStreamingSource()

    //MediaStreaming 구현체
    val firstMediaStreamingPlayer = MediaStreamingPlayerImpl(audioMediaType, localStreamingSource)
    val secondMediaStreamingPlayer = MediaStreamingPlayerImpl(videoMediaType, localStreamingSource)

    firstMediaStreamingPlayer.playMedia()
    secondMediaStreamingPlayer.playMedia()
}