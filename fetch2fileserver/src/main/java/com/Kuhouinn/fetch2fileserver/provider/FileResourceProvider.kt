package com.GKuhouinn.fetch2fileserver.provider

import com.GKuhouinn.fetch2core.FileResource

interface FileResourceProvider {

    val id: String

    fun execute()

    fun interrupt()

    fun isServingFileResource(fileResource: FileResource): Boolean

}