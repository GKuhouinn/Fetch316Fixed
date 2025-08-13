package com.GKuhouinn.fetch2.helper

import com.GKuhouinn.fetch2.*
import com.GKuhouinn.fetch2.database.DownloadInfo
import com.GKuhouinn.fetch2.downloader.FileDownloader
import com.GKuhouinn.fetch2.util.defaultNoError
import com.GKuhouinn.fetch2.Status
import com.GKuhouinn.fetch2.util.DEFAULT_GLOBAL_AUTO_RETRY_ATTEMPTS
import com.GKuhouinn.fetch2core.DownloadBlock
import com.GKuhouinn.fetch2core.Logger


class FileDownloaderDelegate(private val downloadInfoUpdater: DownloadInfoUpdater,
                             private val fetchListener: FetchListener,
                             private val retryOnNetworkGain: Boolean,
                             private val globalAutoRetryMaxAttempts: Int) : FileDownloader.Delegate {

    @Volatile
    override var interrupted = false
    val logger: Logger = defaultLogger
    override fun onStarted(download: Download, downloadBlocks: List<DownloadBlock>, totalBlocks: Int) {
        if (!interrupted) {
            val downloadInfo = download as DownloadInfo
            downloadInfo.status = Status.DOWNLOADING
            downloadInfoUpdater.update(downloadInfo)
            fetchListener.onStarted(download, downloadBlocks, totalBlocks)
        }
    }

    override fun onProgress(download: Download, etaInMilliSeconds: Long, downloadedBytesPerSecond: Long) {
        if (!interrupted) {
            fetchListener.onProgress(download, etaInMilliSeconds, downloadedBytesPerSecond)
        }
    }

    override fun onDownloadBlockUpdated(download: Download, downloadBlock: DownloadBlock, totalBlocks: Int) {
        if (!interrupted) {
            fetchListener.onDownloadBlockUpdated(download, downloadBlock, totalBlocks)
        }
    }

    override fun onError(download: Download, error: Error, throwable: Throwable?) {
        if (!interrupted) {
            val maxAutoRetryAttempts = if (globalAutoRetryMaxAttempts != DEFAULT_GLOBAL_AUTO_RETRY_ATTEMPTS) {
                globalAutoRetryMaxAttempts
            } else {
                download.autoRetryMaxAttempts
            }
            val downloadInfo = download as DownloadInfo
            logger.d("file downloader delegate onError()")
            if ( error == Error.NO_STORAGE_SPACE || error == Error.FILE_NOT_CREATED || error == Error.WRITE_PERMISSION_DENIED
                    || error == Error.FILE_NOT_FOUND || error == Error.FILE_ALLOCATION_FAILED) {
                logger.d("file downloader delegate onError() Some File write Error")
                downloadInfo.status = Status.FAILED
                downloadInfoUpdater.update(downloadInfo)
                fetchListener.onError(download, error, throwable)
                return
            }
            if (retryOnNetworkGain && downloadInfo.error == Error.NO_NETWORK_CONNECTION) {
                downloadInfo.status = Status.QUEUED
                downloadInfo.error = defaultNoError
                downloadInfoUpdater.update(downloadInfo)
                fetchListener.onQueued(download, true)
            } else if (download.autoRetryAttempts < maxAutoRetryAttempts) {
                download.autoRetryAttempts += 1
                downloadInfo.status = Status.QUEUED
                downloadInfo.error = defaultNoError
                downloadInfoUpdater.update(downloadInfo)
                fetchListener.onQueued(download, true)
            } else {
                downloadInfo.status = Status.FAILED
                downloadInfoUpdater.update(downloadInfo)
                fetchListener.onError(download, error, throwable)
            }
        }
    }

    override fun onComplete(download: Download) {
        if (!interrupted) {
            val downloadInfo = download as DownloadInfo
            downloadInfo.status = Status.COMPLETED
            downloadInfoUpdater.update(downloadInfo)
            fetchListener.onCompleted(download)
        }
    }

    override fun saveDownloadProgress(download: Download) {
        if (!interrupted) {
            val downloadInfo = download as DownloadInfo
            downloadInfo.status = Status.DOWNLOADING
            downloadInfoUpdater.updateFileBytesInfoAndStatusOnly(downloadInfo)
        }
    }

    override fun getNewDownloadInfoInstance(): DownloadInfo {
        return downloadInfoUpdater.getNewDownloadInfoInstance()
    }

}