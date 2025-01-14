package MODULE_12.MobilePhoneStore

// Класс для ремонтной мастерской
class RepairShop {
    fun repairPhone(phoneName: String): Boolean {
        println("Ваш телефон $phoneName ремонтируется...")
        // Логика ремонта телефона
        println("Телефон $phoneName был успешно отремонтирован!")
        return true
    }
}
