package org.oldaim

import java.util.concurrent.ConcurrentHashMap

class HashMapTest {

    fun hashMapThreadTest(hashMap: HashMap<String, Int>){

        val thread1 =Thread {
            hashMap.put("A", 1)
        }

        val thread2 = Thread {
            val result: Int? = hashMap.remove("A")

            if (result != null){
                println(result)
            }else{
                throw NoSuchElementException("no such key")
            }
        }

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()
    }


}

fun main() {

    val hashMap = hashMapOf<String, Int>()
    val concurrentMap: ConcurrentHashMap<String, Int> = ConcurrentHashMap()

    val test =  HashMapTest()

    test.hashMapThreadTest(hashMap)

}