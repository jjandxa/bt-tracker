package ml.nobb.bttracker.model

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseModel {

    @Id
    @GeneratedValue(generator = "model-uuid")
    @GenericGenerator(name = "model-uuid", strategy = "uuid")
    var id: String? = null

    /**
     * 记录创建时间
     */
    val createTime: Date = Date()
}