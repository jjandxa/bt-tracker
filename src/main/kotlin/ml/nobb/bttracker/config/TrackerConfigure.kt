package ml.nobb.bttracker.config

import com.turn.ttorrent.tracker.Tracker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrackerConfigure {

    @Bean
    fun tracker(): Tracker {
        val tracler = Tracker(0)
        tracler.start(true)
        return tracler
    }

}