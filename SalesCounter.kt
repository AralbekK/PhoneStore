package MODULE_12.MobilePhoneStore

// Класс для учета количества проданных телефонов
class SalesCounter {
    private val salesMap = mutableMapOf<String, Int>()

    // Метод для записи продажи телефона
    fun recordSale(phoneName: String) {
        salesMap[phoneName] = salesMap.getOrDefault(phoneName, 0) + 1
    }

    // Метод для получения количества проданных телефонов по модели
    fun getSalesCount(phoneName: String): Int {
        return salesMap.getOrDefault(phoneName, 0)
    }

    // Метод для получения общего числа продаж всех моделей
    fun getTotalSales(): Map<String, Int> {
        return salesMap
    }
}
