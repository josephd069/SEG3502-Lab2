package seg3x02.converter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(WebController::class)
class WebControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun request_to_home() {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

    @Test
    fun addition() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/calculate")
                .param("firstNumber", "10")
                .param("secondNumber", "5")
                .param("operation", "+")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("result", 15.0))
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

    @Test
    fun subtraction() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/calculate")
                .param("firstNumber", "10")
                .param("secondNumber", "5")
                .param("operation", "-")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("result", 5.0))
    }

    @Test
    fun multiplication() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/calculate")
                .param("firstNumber", "10")
                .param("secondNumber", "5")
                .param("operation", "*")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("result", 50.0))
    }

    @Test
    fun division() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/calculate")
                .param("firstNumber", "10")
                .param("secondNumber", "5")
                .param("operation", "/")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.model().attribute("result", 2.0))
    }

    @Test
    fun division_by_zero() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/calculate")
                .param("firstNumber", "10")
                .param("secondNumber", "0")
                .param("operation", "/")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.model()
                    .attribute("error", "DivisionByZeroError")
            )
    }

    @Test
    fun invalid_number() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/calculate")
                .param("firstNumber", "abc")
                .param("secondNumber", "5")
                .param("operation", "+")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.model()
                    .attribute("error", "NumberFormatError")
            )
    }
}