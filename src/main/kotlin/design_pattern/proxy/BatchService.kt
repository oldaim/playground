package design_pattern.proxy

interface BatchService {
    fun processData(data: String): Map<String, String>
}

class BatchServiceProxy(private val batchService: BatchService): BatchService {
    override fun processData(data: String): Map<String, String> {
        println("Before processing data. You can do something here")
        val result: Map<String, String> = batchService.processData(data)
        println("After processing data. You can do something here")
        return result
    }
}

class BatchServiceImpl: BatchService {
    override fun processData(data: String): Map<String, String> {
        return mapOf("a" to "1", "b" to "2", "c" to "3")
    }
}

fun main() {

    val service: BatchService = BatchServiceImpl()
    val proxy: BatchService = BatchServiceProxy(service)

    val result: Map<String, String> = proxy.processData("some data")

    println(result)

}