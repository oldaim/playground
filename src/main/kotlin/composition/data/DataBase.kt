package composition.data

data class DataBase(
    val url: String,
    val username: String,
    val password: String,
){
    init {
        require(url.isNotBlank())
        require(username.isNotBlank())
        require(password.isNotBlank())
    }

    fun query(query: String): String {
        println("query: $query")
        return "result"
    }
}
