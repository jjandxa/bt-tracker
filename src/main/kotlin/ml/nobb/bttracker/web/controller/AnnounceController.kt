package ml.nobb.bttracker.web.controller

import ml.nobb.bttracker.service.IAnnounceService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * BT Tracker 控制类
 */
@Controller
class AnnounceController {

    private val logger: Logger = LoggerFactory.getLogger(AnnounceController::class.java)

    @Autowired
    private lateinit var announceService: IAnnounceService

    /**
     * 接收 BT 客户端请求
     */
    @GetMapping("/announce")
    fun announce(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("收到 BT 客户端的连接请求 IP: {} ，客户端: {}", request.remoteAddr,
                request.getHeader(HttpHeaders.USER_AGENT))
        announceService.handler(request, response)
    }
}
