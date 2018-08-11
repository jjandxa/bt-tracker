package ml.nobb.bttracker.service

import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface IAnnounceService {

    /**
     * 上传种子文件
     */
    fun uploadTorrent(file: MultipartFile)

    /**
     * 处理 BT 客户端请求
     */
    fun handler(request: HttpServletRequest, response: HttpServletResponse)
}