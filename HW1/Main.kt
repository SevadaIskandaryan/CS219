import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun main(){
    task1()
    task2()
    task3()
    task4()
    task5()
    task6()
    task7()
    task8and9()
    task10()
}

fun task1() {
    println("---TASK 1---")

    val myArray = arrayOf(1, 2, 3, 4, 5)


    val myList = listOf("apple", "banana", "cherry", "date", "fig")

    println("Elements in the array:")
    for (element in myArray) {
        println(element)
    }

    println("Elements in the list:")
    for (element in myList) {
        println(element)
    }
}

fun task2() {
    println("---Task 2---")
    var myString = "Hello, World!"

    // Concatenation
    myString += " Welcome to Kotlin" 

    // Substring extraction
    val substring = myString.substring(7, 12) 

    // Changing case
    val upperCaseString = myString.toUpperCase()
    val lowerCaseString = myString.toLowerCase()

    // Print test
    println("Original String: $myString")
    println("Concatenated String: $myString")
    println("Substring: $substring")
    println("Uppercase: $upperCaseString")
    println("Lowercase: $lowerCaseString")
}


fun task3() {
    println("---Task 3---")

    val myMap = mapOf(
        "name" to "John",
        "age" to 30,
        "country" to "USA"
    )

    for ((key, value) in myMap) {
        println("Key: $key, Value: $value")
    }
}



fun task4() {
    println("---Task 4---")
    val numbersToCheck = listOf(5, -2, 0, 10, -8)

    for (number in numbersToCheck) {
        val result = classifyNumber(number)
        println("$number is $result")
    }
}

//task4 continuation
fun classifyNumber(number: Int): String {
    return when {
        number > 0 -> "Positive"
        number < 0 -> "Negative"
        else -> "Zero"
    }
}


fun task5() {
    println("---Task 5---")
    // Ask
    print("Please enter your name: ")
    val name = readLine()
    print("Please enter your age: ")
    val age = readLine()?.toIntOrNull()

    // Check if age is a valid integer
    if (name != null && age != null) {
        // Generate a personalized greeting and message based on age
        val greeting = "Hello, $name!"
        val message = when {
            age < 0 -> "You entered a negative age. Please enter a valid age."
            age < 18 -> "You are under 18 years old."
            age in 18..64 -> "You are between 18 and 64 years old."
            else -> "You are 65 or older."
        }

        // Print the personalized greeting and message
        println(greeting)
        println(message)
    } else {
        println("Invalid input. Please enter a valid name and age.")
    }
}

fun divideNumbers(dividend: Double, divisor: Double): Double? {
    return if (divisor != 0.0) {
        dividend / divisor
    } else {
        null
    }
}

fun task6() {
    println("---Task 6---")
    val dividend = 10.0  // Predefined dividend
    val divisor = 2.0   // Predefined divisor

    val result = divideNumbers(dividend, divisor)
    
    if (result != null) {
        println("Result of $dividend / $divisor = $result")
    } else {
        println("Division by zero is not allowed.")
    }
}



fun task7() {
    println("---Task 7---")
    val currentDateTime = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val formattedDateTime = currentDateTime.format(formatter)

    println("Current Date and Time: $formattedDateTime")
}


//task 8
class Person(val name: String, val age: Int){
    fun getLifeStage(): String {
        return when {
            age < 0 -> "Invalid age"  // Handle negative age
            age in 0..12 -> "Child"
            age in 13..19 -> "Teenager"
            age in 20..64 -> "Adult"
            else -> "Senior"
        }
    }
}

fun task8and9() {
    println("---Task 8 and 9---")
    val person = Person("John Bob", 30)

    println("Person's Name: ${person.name}")
    println("Person's Age: ${person.age}")

    val lifeStage = person.getLifeStage()
    println("Life Stage: $lifeStage")
}

fun task10() {
    println("---Task 10---")
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val evenNumbers = numbers.filter { it % 2 == 0 }

    println("Even numbers in the list: $evenNumbers")
}




