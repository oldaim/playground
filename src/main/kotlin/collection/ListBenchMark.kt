package collection

import java.util.LinkedList
import java.util.stream.IntStream
import kotlin.streams.toList
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class ListBenchMark {

    // ArrayList 와 LinkedList 에 순차 접근 하는 상황
    fun benchmarkOne(arrayList: List<Int>, linkedList: List<Int>) {

        val arrayListTime: Long = measureTimeMillis {
            for (el in arrayList) {
                val test = el
            }
        }

        val linkedListTime = measureTimeMillis {
            for (el in linkedList) {
                val test = el
            }
        }

        println("ArrayList 와 LinkedList 에 순차 접근 하는 상황")
        println("benchmark 1: arrayList 순회: $arrayListTime, linkedList 순회: $linkedListTime")

    }

    // ArrayList 와 LinkedList 에 중간에 임의의 요소를 넣는 상황
    fun benchmarkTwo(arrayList: java.util.ArrayList<Int>, linkedList: LinkedList<Int>) {

        val randomList = IntStream.range(0, 100000).toList()

        val arrayListTime: Long = measureNanoTime {

            arrayList.addAll(123451,randomList)

        }

        val linkedListTime = measureNanoTime {

            linkedList.addAll(123451,randomList)
        }

        println("ArrayList 와 LinkedList 에 중간에 임의의 요소를 넣는 상황")
        println("benchmark 2: arrayList 순회: $arrayListTime, linkedList 순회: $linkedListTime")

    }

    fun benchmarkThree(arrayList: java.util.ArrayList<Int>, linkedList: LinkedList<Int>) {

        val randomList = IntStream.range(0, 100000).toList()

        val arrayListTime: Long = measureTimeMillis {

            arrayList.addAll(0,randomList)

        }

        val linkedListTime = measureTimeMillis {

            linkedList.addAll(0,randomList)
        }

        println("맨앞에 임의요소를 넣는 상황")
        println("benchmark 3: arrayList 순회: $arrayListTime, linkedList 순회: $linkedListTime")

    }
}

fun main() {

    val arrayList = ArrayList<Int>()
    val linkedList = LinkedList<Int>()

    IntStream.range(0 , 10000000).forEach { i ->
        arrayList.add(i)
        linkedList.add(i)
    }

    val listBenchMark = ListBenchMark()

    listBenchMark.benchmarkOne(arrayList, linkedList)
    listBenchMark.benchmarkTwo(arrayList, linkedList)
    listBenchMark.benchmarkThree(arrayList, linkedList)

}