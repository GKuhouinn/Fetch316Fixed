package com.GKuhouinn.fetch2fileserver

import com.GKuhouinn.fetch2core.FetchLogger


/** Fetch File Server Default Logger*/
open class FetchFileServerLogger(enableLogging: Boolean = true,
                                 tag: String = "FetchFileServerLogger") : FetchLogger(enableLogging, tag)