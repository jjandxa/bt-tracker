package ml.nobb.bttracker.model

import javax.persistence.*

/**
 * 种子
 */
@Entity
class TorrentData(
        // 种子名称
        val name: String? = null,
        // 种子总大小  单位：字节
        val size: Long? = null,
        // 磁盘上是否存在该种子
        var file: Boolean = true,
        // 种子关联的文件列表
        @OneToMany(mappedBy = "torrent")
        val files: List<TorrentFileData>? = null) : BaseModel()