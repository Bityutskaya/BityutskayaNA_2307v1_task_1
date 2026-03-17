package ci.nsu.mobile.main.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.graphics.Color
import android.util.Log  // Добавляем импорт для Log
import androidx.core.graphics.luminance
import ci.nsu.mobile.main.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private const val TAG = "MainFragment"  // Добавляем тег для логов
    }

    private val viewModel: MainViewModel by viewModels()

    // Объявляем переменные для views
    private lateinit var editTextColor: EditText
    private lateinit var buttonApply: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Инициализируем views
        editTextColor = view.findViewById(R.id.editTextText)
        buttonApply = view.findViewById(R.id.button2)

        // Устанавливаем обработчик нажатия на кнопку
        buttonApply.setOnClickListener {
            changeButtonColor()
        }

        return view
    }


    private fun changeButtonColor() {
        // Получаем введенный текст, очищаем от пробелов и приводим к нижнему регистру
        val colorName = editTextColor.text.toString()
            .trim()
            .lowercase()
            .replace(Regex("\\s+"), " ")  // заменяем множественные пробелы на один

        // Логируем введенный текст
        Log.d(TAG, "Пользователь ввел: '$colorName'")

        // Проверяем, что поле не пустое
        if (colorName.isEmpty()) {
            Log.w(TAG, "Поле ввода пустое")
            return
        }

        // Определяем цвет по названию
        val colorHex = when (colorName) {
            "red" -> "#FF0000"
            "orange" -> "#FF8000"
            "yellow" -> "#FFEA00"
            "green" -> "#04FF00"
            "blue" -> "#002FFF"
            "indigo" -> "#46307A"
            "violet" -> "#8400FF"
            else -> null
        }

        // Применяем цвет, если нашли соответствие
        if (colorHex != null) {
            try {
                // Используем Color.parseColor() для hex-строк
                val color = Color.parseColor(colorHex)
                buttonApply.setBackgroundColor(color)

                Log.i(TAG, "Цвет изменен на $colorName ($colorHex)")
                color.luminance
                // Если цвет темный, меняем цвет текста на белый для лучшей читаемости
                if (colorName == "blue" || colorName == "indigo" || colorName == "violet") {
                    buttonApply.setTextColor(Color.WHITE)
                    Log.d(TAG, "Установлен белый цвет текста (темный фон)")
                } else {
                    buttonApply.setTextColor(Color.BLACK)
                    Log.d(TAG, "Установлен черный цвет текста (светлый фон)")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при применении цвета: ${e.message}")
            }
        } else {
            Log.w(TAG, "Цвет '$colorName' не найден. Доступны: red, orange, yellow, green, blue, indigo, violet")
        }
    }
}