package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

// შევქმნათ ფუნქცია რიცხვის ტექსტად გარდაქმნისთვის
fun numberToText(number: Int): String {
    // შევქმნათ მასივები
    val units = arrayOf("", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა")
    val teens = arrayOf("ათი", "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")
    val tens = arrayOf("", "ოცი","ოცი", "ორმოცი","ორმოცი", "სამოცი","სამოცი", "ოთხმოცი", "ოთხმოცი")

    // შევამოწმოთ მოცემული რიცხვი იმყოფება თუ არა მოცემულ საზღვარში (1-დან 1000-მდე)
    if (number < 1 || number > 1000) {
        return "რიცხვი საზღვარს სცდება"
    }

    // ამოიღეთ ასობითი, ათეული და ერთეული ციფრები რიცხვიდან
    val hundredsDigit = number / 100
    val tensDigit = (number % 100) / 10
    val onesDigit = number % 10

    var text = ""

    //teens და single ციფრები
    if (number in 10..19) {
        text += teens[number%10]
        return text
    } else if (number<=9) {
        text += units[number]
        return text
    }

    // გადავიყვანოთ ასობითი ციფრი ტექსტად, თუ ის 0-ზე მეტია
    if (hundredsDigit > 0) {
        if(hundredsDigit == 1){text += "ასი"}
        else{
            text += units[hundredsDigit].dropLast(1) + "ასი"
            }

        // ამოვიღოთ "ი" ბოლოში თუ გასცდება ასს
        if (tensDigit > 0 || onesDigit > 0) {
            text = text.dropLast(1) //for example: ხუთ ას(ი) ოც და შვიდი
        }
        if (number%100<20 && number%100>9) {
            text += teens[number%100]
            return text
        } else if (number%100<=9) {
            text += units[number%100]
            return text
        }
    }

    // შევამოწმოთ if ten-ს ციფრები
    if (tensDigit == 1) {
        text += teens[0]
    } else if (tensDigit > 1){
        // შევამოწმოთ ლუწია თუ კენტი, რათა დავამატოთ  "და" და "ათი" სწორედ
        if (tensDigit % 2 == 0){text+=tens[tensDigit]
            if (tensDigit > 0 && onesDigit > 0) {
                text=text.dropLast(1)+"და"
                text += " " + units[onesDigit]
            }
        }
        else{text+=tens[tensDigit-1].dropLast(1)+"და"
            if (tensDigit > 1 && onesDigit >= 0) {
                text += teens[onesDigit]
            }

        }
    }



    // Return საბოლოო რეპრეზენტაცია რიცხვის
    return text
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonMain = findViewById<Button>(R.id.button)
        val textViewEnd = findViewById<TextView>(R.id.textViewEnd)
        val editTextMain = findViewById<EditText>(R.id.editTextNumber)

        buttonMain.setOnClickListener {
            val userInput = editTextMain.text.toString()
            val number = userInput.toIntOrNull()

            val text = if (number != null) {
                numberToText(number)
            } else {
                "შეიყვანეთ მთელი რიცხვი"
            }

            textViewEnd.text = text
        }
    }
}
