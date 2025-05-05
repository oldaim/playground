package thread

import kotlinx.coroutines.*
import java.util.concurrent.Executors

class Data {


    // 중단 함수
    // 다른 중단 함수나 코루틴 내에서만 호출 가능
    // 일시 중단 가능하고, 다른 코루틴이 일시 중단 이후 해당 스레드를 사용할수 있음
    suspend fun fetchData(): String{
        delay(100)
        return "Data"
    }

    suspend fun sendData(data: String){
        delay(100)
    }
}


fun main() {
    val data = Data()
    val parentJob = SupervisorJob()
    val childJob = Job(parentJob)
    val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    val exceptionHandler = CoroutineExceptionHandler { context, exception -> exception.printStackTrace() }

    val scope = CoroutineScope(parentJob + dispatcher + exceptionHandler)
    val childScope = CoroutineScope(childJob + dispatcher + exceptionHandler)

    scope.launch {

        data.fetchData()

        childScope.launch {
            data.fetchData()
        }

    }





}

