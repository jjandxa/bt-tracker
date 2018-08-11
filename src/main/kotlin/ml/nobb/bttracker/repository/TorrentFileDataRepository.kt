package ml.nobb.bttracker.repository

import ml.nobb.bttracker.model.TorrentFileData
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TorrentFileDataRepository : PagingAndSortingRepository<TorrentFileData, String> {
}