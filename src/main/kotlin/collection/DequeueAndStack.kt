package collection

import java.util.Stack
import java.util.stream.IntStream

class DequeueAndStack {

    val stack: Stack<Int> = Stack<Int>()
    val deque: ArrayDeque<Int> = ArrayDeque<Int>()


    fun addSomething() {

        IntStream.range(0, 10).forEach {
            deque.addFirst(it)
            stack.push(it)
        }

        deque.forEach { print("$it ") }
        println()
        stack.forEach { print("$it ")}
        println()

    }

    fun popSomething() {
        IntStream.range(0, 10).forEach {
            deque.addFirst(it)
            stack.push(it)
        }

        for (i in 0 .. 9){
            println("stack ${stack.pop()}")
            println("deque ${deque.removeFirst()}")
        }
    }
}

fun main() {
    val dequeue = DequeueAndStack()

    dequeue.addSomething()
    dequeue.popSomething()
}