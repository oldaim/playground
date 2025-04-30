package org.oldaim

import java.util.Collections
import java.util.HashMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

fun main() {
    // 세 가지 맵 타입 테스트
    testMapConcurrency("HashMap", HashMap<String, Int>())
    testMapConcurrency("SynchronizedMap", Collections.synchronizedMap(HashMap<String, Int>()))
    testMapConcurrency("ConcurrentHashMap", ConcurrentHashMap<String, Int>())

    // 성능 비교 테스트
    println("\n===== 성능 비교 테스트 =====")
    comparePerformance()
}

inline fun <reified K  , reified V> testMapConcurrency(mapName: String, map: MutableMap<K, V>) {
    println("\n===== $mapName 테스트 =====")

    // 테스트 파라미터
    val threadCount = 10
    val iterationsPerThread = 100000

    // 카운트다운 래치를 사용하여 모든 스레드가 동시에 시작하도록 함
    val startLatch = CountDownLatch(1)
    val endLatch = CountDownLatch(threadCount)

    // 스레드풀 생성
    val executor = Executors.newFixedThreadPool(threadCount)

    try {
        // 여러 스레드가 동시에 맵을 수정
        for (i in 0 until threadCount) {
            val threadId: Int = i
            executor.submit {
                try {
                    // 모든 스레드가 준비될 때까지 대기
                    startLatch.await()

                    // 각 스레드는 고유한 키 범위에 대해 작업
                    for (j in 0 until iterationsPerThread) {
                        val key = "${threadId}_$j" as K
                        val value = j as V

                        // 삽입
                        map[key] = value

                        // 조회
                        val retrievedValue = map[key]

                        // 삭제 (50% 확률)
                        if (j % 2 == 0) {
                            map.remove(key)
                        }
                    }
                } catch (e: Exception) {
                    println("스레드 $threadId 에러: ${e.message}")
                    e.printStackTrace()
                } finally {
                    // 작업 완료 신호
                    endLatch.countDown()
                }
            }
        }

        // 모든 스레드 시작 신호
        val executionTime = measureTimeMillis {
            startLatch.countDown()

            // 모든 스레드가 완료될 때까지 대기
            val completed = endLatch.await(30, TimeUnit.SECONDS)
            if (!completed) {
                println("$mapName: 30초 타임아웃! 일부 스레드가 완료되지 않았습니다.")
            }
        }

        // 결과 출력
        println("$mapName 실행 시간: $executionTime ms")
        println("최종 맵 크기: ${map.size}")

        // 예상 크기 계산
        val expectedSize = threadCount * iterationsPerThread / 2 // 50%는 삭제됨
        println("예상 크기: $expectedSize")
        println("데이터 일관성: ${if (map.size == expectedSize) "일치" else "불일치"}")

    } finally {
        executor.shutdown()
    }
}

fun comparePerformance() {
    val maps = listOf(
        Triple("HashMap", HashMap<String, Int>(), false),
        Triple("SynchronizedMap", Collections.synchronizedMap(HashMap<String, Int>()), true),
        Triple("ConcurrentHashMap", ConcurrentHashMap<String, Int>(), true)
    )

    val operations = 100_000
    val threadCount = 8

    for ((name, map, isConcurrencySupported) in maps) {
        if (!isConcurrencySupported) {
            println("$name: 단일 스레드 테스트")
            val time = measureTimeMillis {
                for (i in 0 until operations) {
                    map["key_$i"] = i
                }
                for (i in 0 until operations) {
                    map["key_$i"]
                }
                for (i in 0 until operations) {
                    map.remove("key_$i")
                }
            }
            println("$name 단일 스레드 실행 시간: $time ms")
        } else {
            println("$name: 멀티 스레드 테스트 ($threadCount 스레드)")
            val startLatch = CountDownLatch(1)
            val endLatch = CountDownLatch(threadCount)
            val executor = Executors.newFixedThreadPool(threadCount)

            val operationsPerThread = operations / threadCount

            val time = measureTimeMillis {
                for (t in 0 until threadCount) {
                    val threadId = t
                    executor.submit {
                        try {
                            startLatch.await()

                            val start = threadId * operationsPerThread
                            val end = start + operationsPerThread

                            // 삽입
                            for (i in start until end) {
                                map["key_$i"] = i
                            }

                            // 조회
                            for (i in start until end) {
                                map["key_$i"]
                            }

                            // 삭제
                            for (i in start until end) {
                                map.remove("key_$i")
                            }
                        } finally {
                            endLatch.countDown()
                        }
                    }
                }

                startLatch.countDown()
                endLatch.await()
            }

            println("$name 멀티 스레드 실행 시간: $time ms")
            executor.shutdown()
        }
    }
}