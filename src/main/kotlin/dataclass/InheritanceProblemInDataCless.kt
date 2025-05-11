package dataclass

data class CoupangItem(
    override val itemId: String,
    override val itemName: String,
    override val itemOriginalPrice: Int,
    val rocketMemberShipUserPrice: Int
): Item(itemId, itemName, itemOriginalPrice)

data class NaverItem(
    override val itemId: String,
    override val itemName: String,
    override val itemOriginalPrice: Int,
    val naverMembershipUserPrice: Int
): Item(itemId, itemName, itemOriginalPrice)

data class KakaoItem(
    override val itemId: String,
    override val itemName: String,
    override val itemOriginalPrice: Int,
    val kakaoPrimeUserPrice: Int
): Item(itemId, itemName, itemOriginalPrice)

abstract class Item(
    open val itemId: String,
    open val itemName: String,
    open val itemOriginalPrice: Int
)

fun main() {
    val coupangItem = CoupangItem("1", "쿠팡", 10000, 8000)
    val naverItem = NaverItem("1", "쿠팡", 10000, 8000)
    val kakaoItem = KakaoItem("1", "쿠팡", 10000, 8000)

    printItem(coupangItem)
    printItem(naverItem)
    printItem(kakaoItem)

    val isTrue = coupangItem.equals(naverItem)

    println(isTrue)
}

fun <T: Item> printItem(item: T) {

    println(item.hashCode())

}