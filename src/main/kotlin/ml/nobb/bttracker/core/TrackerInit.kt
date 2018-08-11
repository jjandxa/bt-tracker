package ml.nobb.bttracker.core

import com.turn.ttorrent.tracker.TrackedTorrent
import com.turn.ttorrent.tracker.Tracker
import ml.nobb.bttracker.config.SystemSettings
import ml.nobb.bttracker.model.constant.TorrentConstant
import ml.nobb.bttracker.repository.TorrentDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct

@Component
class TrackerInit {

    @Autowired
    private lateinit var systemSettings: SystemSettings

    @Autowired
    private lateinit var tracker: Tracker

    @Autowired
    private lateinit var torrentDataRepository: TorrentDataRepository

    @PostConstruct
    fun loadTorrentFile() {
        val torrentList = torrentDataRepository.findAll()
        torrentList.forEach {
            val torrentFile = File("${systemSettings.torrentsPath}${File.separator}" +
                    "${it.id}${File.separator}${it.name}${TorrentConstant.TORRENT_FILE_SUFFIX}")
            if (torrentFile.exists()) {
                tracker.announce(TrackedTorrent.load(torrentFile))
                if (!it.file) {
                    // 该种子被标记了本地不存在文件，但实际上本地存在的话则更新标识
                    it.file = true
                    torrentDataRepository.save(it)
                }
            } else {
                // 如果本地种子文件不存在，则更新标识
                it.file = false
                torrentDataRepository.save(it)
            }
        }
    }
}