package design_pattern.decorater

interface ListUpdater {
    fun updateList(list: List<Int>): List<Int>
}

class SimpleListUpdater: ListUpdater {
    override fun updateList(list: List<Int>): List<Int> {
        return list
    }
}

class ListUpdaterAddDecorator(private val listUpdater: ListUpdater): ListUpdater {
    override fun updateList(list: List<Int>): List<Int> {
        val result: List<Int> = listUpdater.updateList(list)
        return result.map { it + 1 }
    }
}

class ListUpdaterMultiplyDecorator(private val listUpdater: ListUpdater): ListUpdater {
    override fun updateList(list: List<Int>): List<Int> {
        val result: List<Int> = listUpdater.updateList(list)
        return result.map { it * 3 }
    }
}

class ListUpdaterDivideDecorator(private val listUpdater: ListUpdater): ListUpdater {
    override fun updateList(list: List<Int>): List<Int> {
        val result: List<Int> = listUpdater.updateList(list)
        return result.map { it / 2 }
    }
}

fun main() {
    val simpleUpdate = SimpleListUpdater()
    val addDecorator = ListUpdaterAddDecorator(simpleUpdate)
    val multiplyDecorator = ListUpdaterMultiplyDecorator(addDecorator)
    val divideDecorator = ListUpdaterDivideDecorator(multiplyDecorator)

    println(divideDecorator.updateList(listOf(1,2,3,4,5,6,7,8,9,10)))
}