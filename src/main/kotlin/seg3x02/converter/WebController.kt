package seg3x02.converter

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WebController {

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("error", "")
        model.addAttribute("firstNumber", "")
        model.addAttribute("secondNumber", "")
        model.addAttribute("result", "")
    }

    @RequestMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping(value = ["/calculate"])
    fun calculate(
        @RequestParam(value = "firstNumber", required = false) firstNumber: String,
        @RequestParam(value = "secondNumber", required = false) secondNumber: String,
        @RequestParam(value = "operation", required = false) operation: String,
        model: Model
    ): String {

        try {
            val first = firstNumber.toDouble()
            val second = secondNumber.toDouble()

            val result = when (operation) {
                "+" -> first + second
                "-" -> first - second
                "*" -> first * second
                "/" -> {
                    if (second == 0.0) {
                        model.addAttribute("error", "DivisionByZeroError")
                        null
                    } else {
                        first / second
                    }
                }
                else -> {
                    model.addAttribute("error", "OperationFormatError")
                    null
                }
            }

            model.addAttribute("firstNumber", firstNumber)
            model.addAttribute("secondNumber", secondNumber)

            if (result != null) {
                model.addAttribute("result", result)
            }

        } catch (exp: NumberFormatException) {
            model.addAttribute("error", "NumberFormatError")
            model.addAttribute("firstNumber", firstNumber)
            model.addAttribute("secondNumber", secondNumber)
        }

        return "home"
    }
}