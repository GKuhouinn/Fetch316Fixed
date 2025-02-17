package com.Kuhouinn.fetchmigrator.fetch1

import com.Kuhouinn.fetch2.Download

data class DownloadTransferPair(val newDownload: Download, val oldID: Long)