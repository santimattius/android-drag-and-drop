package com.santimattius.android.compose.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun ktorHttpClient(apiKey: String) = HttpClient(OkHttp) {

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
        filter { request ->
            request.url.host.contains("ktor.io")
        }
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }

    engine {
        addInterceptor { chain ->
            val request = chain.request()
            val originalUrl = request.url.newBuilder()
            val url = originalUrl.addQueryParameter("api_key", apiKey).build()
            val requestBuilder = request.newBuilder().url(url)
            chain.proceed(requestBuilder.build())
        }
    }
}