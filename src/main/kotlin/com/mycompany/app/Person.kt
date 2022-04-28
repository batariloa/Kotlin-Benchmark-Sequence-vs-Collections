data class Person(
    val name: String,
    val age: Int,
    val driversLicence: Boolean = false,
    val gender: Gender
)

enum class Gender {
    MALE, FEMALE
}