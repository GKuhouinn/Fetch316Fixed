package com.GKuhouinn.fetchmigrator.fetch1

import com.GKuhouinn.fetch2.Download

data class DownloadTransferPair(val newDownload: Download, val oldID: Long)