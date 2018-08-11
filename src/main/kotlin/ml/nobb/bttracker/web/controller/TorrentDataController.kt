package ml.nobb.bttracker.web.controller

import ml.nobb.bttracker.model.vo.ApiResult
import ml.nobb.bttracker.service.IAnnounceService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/torrent")
class TorrentDataController {

    private val logger: Logger = LoggerFactory.getLogger(TorrentDataController::class.java)

    @Autowired
    private lateinit var announceService: IAnnounceService

    /**
     * 上传种子
     */
    @ResponseBody
    @PostMapping("/")
    fun uploadTorrent(file: MultipartFile?): ApiResult {
        logger.info("上传种子文件开始...")
        // 校验种子是否存在
        if (ObjectUtils.isEmpty(file)) {
            logger.info("种子文件为空")
            return ApiResult.fail("种子文件为空")
        }

        try {
            announceService.uploadTorrent(file!!)
        } catch (e: Exception) {
            return ApiResult.fail("上传种子失败")
        }
        logger.info("上传种子文件结束...")
        val a = ApiResult.success()
        return a
    }

    /**
     * 删除种子
     */
    @DeleteMapping("/{id}")
    fun deleteTorrent(@PathVariable("id") id: String) {
        logger.info("删除种子")
        // TODO
    }
}