package com.demo.dataDemo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Configuration @Inject constructor() {
     val cacheFileName = "numbers1"
}