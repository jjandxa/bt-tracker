package ml.nobb.bttracker.web.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonController {

    @RequestMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}