package thread

import kotlinx.coroutines.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import kotlin.math.cos
import kotlin.math.sin
import kotlin.system.measureTimeMillis

class CoroutineTest(
    val requestCount: Int = 1000,
) {

    fun normalTest(){

        val coroutineTime = measureTimeMillis {
            val users = (1..requestCount).map { fetchUserThread(it) }

            println("coroutine result sizes: ${users.size}")
        }

        println("coroutine time: ${coroutineTime}ms")

    }

    fun coroutinesTest(){
        runBlocking {
            val coroutineTime = measureTimeMillis {
                val users = (1..requestCount).map {
                    async(Dispatchers.Default){
                        fetchUserCoroutine(it)
                    }
                }

                val result = users.awaitAll()
                println("coroutine result sizes: ${result.size}")
            }

            println("coroutine time: ${coroutineTime}ms")
        }
    }

    fun threadPoolTest(){
        val measureTimeMillis = measureTimeMillis {
            val executor = Executors.newFixedThreadPool(100)

            val futures = (1..requestCount).map { id ->
                executor.submit<Double> {
                    fetchUserThread(id)
                }
            }

            // 모든 결과 수집
            val results = futures.map { it.get() }
            println("스레드 풀로 ${results.size}개 요청 처리 완료")

            executor.shutdown()
        }

        println("thread pool test: ${measureTimeMillis}ms")

    }

    fun completableFutureTest(){

        val executor = ForkJoinPool(100)

        val measureTimeMillis = measureTimeMillis {
            val futures = (1..requestCount).map { id ->
                CompletableFuture.supplyAsync({
                    fetchUserThread(id)
                }, executor)
            }

            val results = futures.map { it.join() }

            println("ForkJoinPool 로  ${results.size}개 요청 처리 완료")
        }

        println("completableFuture pool test: ${measureTimeMillis}ms")
    }

    fun virtualThreadPoolTest(){
        val measureTimeMillis = measureTimeMillis {
            val executor = Executors.newVirtualThreadPerTaskExecutor()

            val futures = (1..requestCount).map { id ->
                executor.submit {
                    fetchUserThread(id)
                }
            }

            val results = futures.map { it.get() }

            println("가상 스레드 풀로 ${results.size}개 요청 처리 완료")
        }

        println("completableFuture pool test: ${measureTimeMillis}ms")
    }


    private suspend fun fetchUserCoroutine(i: Int): Double {
        if (i % 100 == 0) yield()
        return sin(i.toDouble()) * cos(i.toDouble())
    }

    private fun fetchUserThread(i: Int): Double {
        // 네트워크 지연 시뮬레이션
        return sin(i.toDouble()) * cos(i.toDouble())
    }

}

fun main() {

    val test = CoroutineTest(10000000)

    test.normalTest()
    //test.coroutinesTest()
    //test.threadPoolTest()
    //test.completableFutureTest()
    //test.virtualThreadPoolTest()

}