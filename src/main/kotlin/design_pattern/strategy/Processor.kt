package design_pattern.strategy

interface Processor {
    fun process(data: String)
}

class FileProcessor: Processor {
    override fun process(data: String) {
        println("file process data: $data")
    }
}

class KafkaProcessor: Processor {
    override fun process(data: String) {
        println("kafka process data: $data")
    }
}

class CsvProcessor: Processor {
    override fun process(data: String) {
        println("csv process data: $data")
    }
}

class Client {

    fun processWithStrategy(data: String, strategy: Processor) {
        strategy.process(data)
    }
}



fun main() {

    val client = Client()
    val fileProcessor = FileProcessor()
    val kafkaProcessor = KafkaProcessor()
    val csvProcessor = CsvProcessor()

    client.processWithStrategy("some data", fileProcessor)
    client.processWithStrategy("some data", kafkaProcessor)
    client.processWithStrategy("some data", csvProcessor)

}