package design_pattern.adapter

// Target Interface
interface DataParser {
    fun parseData(data: String): Map<String, String>
}

// Adaptee Interface
interface JsonDataParser {
    fun parseJsonData(data: String): List<String>
}

// Adaptee Implementation
class JsonDataParserImpl : JsonDataParser {
    override fun parseJsonData(data: String): List<String> {
        return listOf("a 1", "b 2", "c 3")
    }
}

// Adapter - DataParserAdapter 인터페이스 제거하고 직접 DataParser 구현
class JsonDataParserAdapter(
    private val jsonDataParser: JsonDataParser
) : DataParser {
    override fun parseData(data: String): Map<String, String> {
        val parsedStringList: List<String> = jsonDataParser.parseJsonData(data)

        return parsedStringList
            .map { it.split(" ") }
            .associate { it[0] to it[1] }
    }
}

// Client Code
class Client {
    fun processData(data: String, parser: DataParser): Map<String, String> {
        return parser.parseData(data)
    }
}

// 사용 예시
fun main() {
    val jsonParser = JsonDataParserImpl()
    val adapter: DataParser = JsonDataParserAdapter(jsonParser)

    val client = Client()
    val result = client.processData("{some json data}", adapter)

    println(result) // {a=1, b=2, c=3}
}