package com.Kuhouinn.fetch2fileserver.provider

import com.Kuhouinn.fetch2core.FileResource

interface FileResourceProvider {

    val id: String

    fun execute()

    fun interrupt()

    fun isServingFileResource(fileResource: FileResource): Boolean

}