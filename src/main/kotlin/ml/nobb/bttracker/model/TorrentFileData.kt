package ml.nobb.bttracker.model

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * 种子文件
 */
@Entity
class TorrentFileData(
        // 文件名称
        val name: String? = null,
        // 文件大小 单位：字节
        val size: Long? = null,
        @ManyToOne
        @JoinColumn(name = "torrent_id") val torrent: TorrentData? = null) : BaseModel()