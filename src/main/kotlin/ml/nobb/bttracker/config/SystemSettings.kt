package ml.nobb.bttracker.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "system")
class SystemSettings {

    /**
     * 种子文件保存路径
     */
    lateinit var torrentsPath: String
}