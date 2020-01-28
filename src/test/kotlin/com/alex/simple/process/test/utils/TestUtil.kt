package com.alex.simple.process.test.utils

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.net.URISyntaxException

class TestUtil {
    object Utils {
        /**
         * Method for fast create object from json file
         */
        @JvmStatic
        fun <T> initDto(relativeFileName: String, classType: Class<T>, objectMapper: ObjectMapper): T? {
            var dto: T? = null
            try {
                val data: String = this::class.java.getResource(relativeFileName).readText(Charsets.UTF_8)
                dto = objectMapper.readValue(data, classType)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return dto
        }
    }
}