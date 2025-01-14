package MODULE_12.MobilePhoneStore

// Класс, представляющий телефон
data class Phone(val name: String, val price: Int)

// Класс, представляющий магазин
class Store(val city: String, private val phonePrices: Map<String, Int>) {

    fun getAvailablePhones(): List<Phone> {
        return phonePrices.map { (name, price) -> Phone(name, price) }
    }


    fun purchasePhone(phoneName: String): Boolean {
        if (phonePrices.containsKey(phoneName)) {
            println("Вы купили: $phoneName в городе $city за ${phonePrices[phoneName]} USD")
            return true
        } else {
            println("Такого телефона нет в продаже в городе $city")
            return false
        }
    }
}

// Фнукция для получения списка самых популярных телефонов 2024 года
fun getTopPhones(): List<Phone> {
    return listOf(
        Phone("Samsung Galaxy S24 Ultra", 1400),
        Phone("iPhone 15 Pro Max", 1300),
        Phone("Google Pixel 8 Pro", 1100),
        Phone("Xiaomi 14 Pro", 1000),
        Phone("OnePlus 12", 900),
        Phone("Oppo Find X7 Pro", 1200),
        Phone("Vivo X100 Pro", 1050),
        Phone("Honor Magic6 Pro", 950),
        Phone("Nothing Phone (2)", 700),
        Phone("Nokia 3310", 70),
    )
}

fun main() {
    val phones = getTopPhones()

    // Цены в разных городах (округляем до целых)
    val moscowPrices = phones.associate { it.name to (it.price * 1.1).toInt() }
    val spbPrices = phones.associate { it.name to (it.price * 1.05).toInt() }


    val moscowStore = Store("Москва", moscowPrices)
    val spbStore = Store("Санкт-Петербург", spbPrices)

    var continueShopping = true
    while (continueShopping) {
        println("\nВыберите город для покупки:")
        println("1. Москва")
        println("2. Санкт-Петербург")
        println("3. Выход")

        val cityChoice = readLine()?.toIntOrNull()

        when (cityChoice) {
            1 -> selectAndPurchasePhone(moscowStore)
            2 -> selectAndPurchasePhone(spbStore)
            3 -> continueShopping = false
            else -> println("Неверный ввод")
        }
    }
    println("Программа завершена.")
}

fun selectAndPurchasePhone(store: Store) {
    println("\nДоступные телефоны в городе ${store.city}:")
    val phones = store.getAvailablePhones()
    phones.forEachIndexed { index, phone -> println("${index + 1}. ${phone.name} - ${phone.price}") }
    println("0. Вернуться в меню выбора города")

    print("Выберите номер телефона для покупки: ")
    val phoneChoice = readLine()?.toIntOrNull()


    when {
        phoneChoice == null -> {
            println("Неверный ввод.")
        }
        phoneChoice == 0 -> return
        phoneChoice > 0 && phoneChoice <= phones.size -> {
            val selectedPhone = phones[phoneChoice - 1].name
            store.purchasePhone(selectedPhone)

        }
        else -> println("Неверный ввод.")
    }
}