package design_pattern.template_callback

class Client {
    fun processWithCallback(data: String,callback: (String) -> Unit) {
        callback(data)
    }
}

fun main() {

    val client = Client()

    val consumer1: (String) -> Unit = { println("$it + 1") }
    val consumer2: (String) -> Unit = { println("$it + 2") }
    val consumer3: (String) -> Unit = { println("$it + 3") }

    client.processWithCallback("1") { consumer1(it) }
    client.processWithCallback("2") { consumer2(it) }
    client.processWithCallback("3") { consumer3(it) }

}