package composition.data

class App {

    companion object{
        const val URL = "http://localhost:8080/"
        const val USER = "user"
        const val PASSWORD = "password"

    }

    val db by lazy { DataBase(url = URL, username = USER, password = PASSWORD) }

    fun start(){
        println("Starting App")
    }

    fun fetchData() {

        val result: String = db.query("SELECT * FROM users")

        println("query results: $result")

    }
}

