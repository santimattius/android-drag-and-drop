package com.santimattius.android.compose.feature.main.data

import com.santimattius.android.compose.feature.main.domain.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType

class MovieRepository(private val client: HttpClient) {

    suspend fun getMovies(): List<Movie> {
        val url = Url("https://api.themoviedb.org/3/movie/popular")
        val response = client.get(url) {
            contentType(ContentType.Application.Json)
        }
        val result = response.body<MovieResponse>()
        return result.asMovies()
    }

    private fun MovieResponse.asMovies(): List<Movie> {
        return this.results.map {
            Movie(
                id = it.id,
                title = it.title,
                image = it.poster
            )
        }
    }
}

