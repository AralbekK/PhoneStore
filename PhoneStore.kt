package MODULE_12.MobilePhoneStore

// Класс, представляющий телефон
data class Phone(val name: String, val price: Int)

// Класс, представляющий магазин
class Store(val city: String, private val phonePrices: Map<String, Int>, private val salesCounter: SalesCounter, private val repairShop: RepairShop? = null) {

    // Метод для получения списка доступных телефонов
    fun getAvailablePhones(): List<Phone> {
        return phonePrices.map { (name, price) -> Phone(name, price) }
    }

    // Метод для покупки телефона
    fun purchasePhone(phoneName: String): Boolean {
        if (phonePrices.containsKey(phoneName)) {
            val price = phonePrices[phoneName] ?: return false
            println("Вы купили: $phoneName в городе $city за $price USD")
            salesCounter.recordSale(phoneName, price)  // Передача цены при регистрации продажи
            return true
        } else {
            println("Такого телефона нет в продаже в городе $city")
            return false
        }
    }

    // Метод для ремонта телефона
    fun repairPhone(phoneName: String): Boolean {
        return repairShop?.repairPhone(phoneName) ?: run {
            println("Ремонтная мастерская недоступна в городе $city")
            false
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
        Phone("Nothing Phone 2", 700),
        Phone("Nokia 3310", 70),
    )
}

fun main() {
    val phones = getTopPhones()

    // Цены в разных городах (округляем до целых)
    val moscowPrices = phones.associate { it.name to (it.price * 1.1).toInt() }
    val spbPrices = phones.associate { it.name to (it.price * 1.05).toInt() }

    val salesCounter = SalesCounter()
    val repairShop = RepairShop()

    // Создание магазинов с разными ценами и ремонтной мастерской для Москвы
    val moscowStore = Store("Москва", moscowPrices, salesCounter, repairShop)
    val spbStore = Store("Санкт-Петербург", spbPrices, salesCounter)

    var continueShopping = true
    while (continueShopping) {
        println("\nВыберите город для покупки:")
        println("1. Москва")
        println("2. Санкт-Петербург")
        println("3. Показать общий счет продаж")
        println("4. Показать общую сумму покупок")
        println("5. Отремонтировать телефон в Москве")
        println("6. Выход")

        val cityChoice = readLine()?.toIntOrNull()

        when (cityChoice) {
            1 -> selectAndPurchasePhone(moscowStore)  // Выбор и покупка телефона в Москве
            2 -> selectAndPurchasePhone(spbStore)  // Выбор и покупка телефона в Санкт-Петербурге
            3 -> showTotalSales(salesCounter)  // Показ общего счета продаж
            4 -> showTotalAmount(salesCounter)  // Показ общей суммы покупок
            5 -> repairPhoneInMoscow(moscowStore)  // Ремонт телефона в Москве
            6 -> continueShopping = false  // Выход из программы
            else -> println("Неверный ввод")
        }
    }
    println("Программа завершена.")
}


// Фнукция для выбора и покупки телефона
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

// Функция для показа общего счета продаж
fun showTotalSales(salesCounter: SalesCounter) {
    println("\nОбщий счет продаж телефонов:")
    salesCounter.getTotalSales().forEach { (phone, count) ->
        println("$phone: $count")
    }
}
// Функция для показа общей суммы покупок
fun showTotalAmount(salesCounter: SalesCounter) {
    println("\nОбщая сумма покупок: ${salesCounter.getTotalAmount()} USD")
}

// Функция для ремонта телефона в Москве
fun repairPhoneInMoscow(store: Store) {
    println("Введите название вашего сломанного телефона: ")
    val phoneName = readLine() ?: return

    println("Нужен ли ремонт вашему телефону? (да/нет)")
    val needRepair = readLine()

    if (needRepair.equals("да", ignoreCase = true)) {
        store.repairPhone(phoneName)
    } else {
        println("Телефон не нуждается в ремонте.")
    }
}
