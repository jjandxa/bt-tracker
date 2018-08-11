package ml.nobb.bttracker.service.impl

import com.turn.ttorrent.common.LoggerUtils
import com.turn.ttorrent.common.TorrentParser
import com.turn.ttorrent.common.protocol.AnnounceRequestMessage
import com.turn.ttorrent.common.protocol.http.HTTPAnnounceRequestMessage
import com.turn.ttorrent.tracker.TrackedTorrent
import com.turn.ttorrent.tracker.Tracker
import ml.nobb.bttracker.config.SystemSettings
import ml.nobb.bttracker.exception.BadRequestException
import ml.nobb.bttracker.exception.NotFoundRequestException
import ml.nobb.bttracker.model.TorrentData
import ml.nobb.bttracker.model.TorrentFileData
import ml.nobb.bttracker.model.constant.TorrentConstant
import ml.nobb.bttracker.repository.TorrentDataRepository
import ml.nobb.bttracker.repository.TorrentFileDataRepository
import ml.nobb.bttracker.service.IAnnounceService
import ml.nobb.bttracker.utils.FileUtil
import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.lang.reflect.Method
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@Service
class AnnounceServiceImpl : IAnnounceService {

    private val logger: Logger = LoggerFactory.getLogger(AnnounceServiceImpl::class.java)

    @Autowired
    private lateinit var systemSettings: SystemSettings

    @Autowired
    private lateinit var tracker: Tracker

    @Autowired
    private lateinit var torrentRepository: TorrentDataRepository

    @Autowired
    private lateinit var torrentFileDataRepository: TorrentFileDataRepository

    /**
     * 上传种子文件
     */
    @Transactional
    override fun uploadTorrent(file: MultipartFile) {

        try {// 解析种子文件
            val torrent = TorrentParser().parse(file.bytes)

            // 文件总大小
            val torrentSize = torrent.files.map { it.size }.sum()

            // 保存种子
            val torrentData = TorrentData(torrent.directoryName, torrentSize)
            torrentRepository.save(torrentData)

            // 保存种子文件
            val torrentFiles = torrent.files.map { TorrentFileData(it.relativePathAsString, it.size, torrentData) }
            torrentFileDataRepository.saveAll(torrentFiles)

            val saveFile = File(systemSettings.torrentsPath + File.separator + torrentData.id +
                    File.separator + torrent.directoryName + TorrentConstant.TORRENT_FILE_SUFFIX)

            // 删除并重新创建文件
            FileUtil.removeAndCreateFile(saveFile)

            file.transferTo(saveFile)

            tracker.announce(TrackedTorrent.load(saveFile))
        } catch (e: Exception) {
            logger.info("上传种子失败; {}", e.message)
            throw RuntimeException("上传种子失败")
        }
    }

    /**
     * 处理 BT 客户端请求
     */
    override fun handler(request: HttpServletRequest, response: HttpServletResponse) {
        // 校验请求地址
        if (Tracker.ANNOUNCE_URL != request.servletPath) {
            logger.info("种子请求地址不正确")
            throw NotFoundRequestException()
        }

        try {

            response.contentType = "text/plain"
            response.setHeader("Server", "text/plain")
            response.setDateHeader("Date", System.currentTimeMillis())

            tracker.myTrackerServiceContainer.myRequestProcessor.process("${request.servletPath}?${request.queryString}",
                    request.remoteAddr,
                    tracker.myTrackerServiceContainer.getRequestHandler(response))
        } catch (ioe: IOException) {
            logger.info("Error while writing response: {}!", ioe.message)
        } catch (t: Throwable) {
            LoggerUtils.errorAndDebugDetails(logger, "error in processing request {}", request, t)
        }
    }
}