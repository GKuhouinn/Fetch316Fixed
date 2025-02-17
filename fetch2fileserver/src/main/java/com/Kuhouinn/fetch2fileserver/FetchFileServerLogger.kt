package com.Kuhouinn.fetch2fileserver

import com.Kuhouinn.fetch2core.FetchLogger


/** Fetch File Server Default Logger*/
open class FetchFileServerLogger(enableLogging: Boolean = true,
                                 tag: String = "FetchFileServerLogger") : FetchLogger(enableLogging, tag)