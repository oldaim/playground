package design_pattern.template_method

import java.util.Deque

abstract class CsvProcessor {

    fun process() {
        val data: String = insertData()
        val processedData = processData(data)
        exportData(processedData)
    }

    protected fun insertData(): String {
        return "insert data"
    }

    abstract fun processData(data: String): String

    protected fun exportData(data: String): String {
        return "export data"
    }
}

class KafkaCsvProcessor: CsvProcessor() {
    override fun processData(data: String): String {
        println(" kafka process data: $data")
        return "some data"
    }
}

class FileCsvProcessor: CsvProcessor() {
    override fun processData(data: String): String {
        println(" file process data: $data")
        return "some data"
    }

}

fun main() {

}