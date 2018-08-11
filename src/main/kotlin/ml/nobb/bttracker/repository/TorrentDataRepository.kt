package ml.nobb.bttracker.repository

import ml.nobb.bttracker.model.TorrentData
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TorrentDataRepository : PagingAndSortingRepository<TorrentData, String> {
}