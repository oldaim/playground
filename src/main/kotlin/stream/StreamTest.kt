package stream

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.stream.LongStream
import kotlin.math.cos
import kotlin.math.roundToLong
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.streams.toList
import kotlin.system.measureTimeMillis

class StreamTest {

    fun eagerWithoutCoroutineTest(list: List<Long>) {

        val times = measureTimeMillis {
           val result = list
               .map { calInteger(it) }
               .fold(0L) { a, b -> a + b }
           println("eagerWithoutCoroutineText result: $result")
       }

       println("times: $times nanoseconds")
    }

    fun eagerWithCoroutineTest(list: List<Long>){
        val times = measureTimeMillis {
            runBlocking(Dispatchers.Default) {
                val result = list.map { async {  calInteger(it) } }
                    .awaitAll()
                    .fold(0L) { a, b -> a + b }

                println("eagerWithCoroutineText result: $result")
            }
        }

        println("times: $times nanoseconds")

    }


    fun sequenceWithoutCoroutineTest(list: List<Long>) {

        val times = measureTimeMillis {
            val result = list.asSequence()
                .map { calInteger(it) }
                .fold(0L) { a, b -> a + b }

            println("sequenceWithoutCoroutineText result: $result")
        }

        println("times: $times nanoseconds")
    }


    fun parallelStreamsTest(list: List<Long>) {

        val times = measureTimeMillis {

            val result = list.parallelStream()
                .map { calInteger(it) }
                .reduce { a, b -> a + b }

            println("parallelStream result: $result")
        }

        println("times: $times nanoseconds")
    }


    private fun calInteger(i: Long): Long {

        return (cos(i.toDouble()) + sin(i.toDouble()) + sqrt(i.toDouble())).roundToLong()
    }

}

fun main() {
    val test = StreamTest()
    val testList: List<Long> = LongStream.range(0, 100_000_00).toList()

    test.eagerWithCoroutineTest(testList)
    println()
    test.eagerWithoutCoroutineTest(testList)
    println()
    test.sequenceWithoutCoroutineTest(testList)
    println()
    test.parallelStreamsTest(testList)

}