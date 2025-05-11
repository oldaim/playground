package design_pattern.singleton

class SingletonClient private constructor(
    private val url: String,
    private val request: String
) {

    companion object{
        private lateinit var url: String
        private lateinit var request: String

        private val instance: Lazy<SingletonClient> =
            lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SingletonClient(url, request) }

        @Synchronized
        fun setInstanceValue(url: String, request: String) {
            if (!instance.isInitialized()) {
                this.url = url
                this.request = request
            }else{
                throw IllegalStateException("SingletonClient is already initialized")
            }
        }


        fun getInstance(): SingletonClient {

            if (instance.isInitialized()) return instance.value
            else throw IllegalStateException("SingletonClient is not initialized")
        }
    }


    fun sendRequest() {
        println("send request $request to $url")
    }


}

fun main() {

    SingletonClient.setInstanceValue("url", "request")

    val client = SingletonClient.getInstance()

    client.sendRequest()

}