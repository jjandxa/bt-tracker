package ml.nobb.bttracker.utils

/**
 * 文件操作工具
 */
import java.io.File

class FileUtil {
    companion object {
        /**
         * 删除并创建文件
         */
        fun removeAndCreateFile(file: File) {
            // 如果存在则删除文件
            if (file.exists()) {
                file.delete()
            }
            file.parentFile.mkdirs()
            // 重新创建
            file.createNewFile()
        }
    }
}