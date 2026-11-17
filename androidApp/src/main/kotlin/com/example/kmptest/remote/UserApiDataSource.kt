package com.example.android.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun main() {
    val user : UserDTO = UserApiDataSource.loadUser()

    println("""Il s'appelle ${user.name} pour le contacter :
            Phone : ${user.coord?.phone}
            Mail : ${user.coord?.mail}""".trimIndent())

    val list : List<UserDTO> = UserApiDataSource.loadUsers()
    println(list.joinToString(separator = "\n\n"))
    UserApiDataSource.close()
}

object UserApiDataSource {
    private const val API_URL_USER =
        "https://www.amonteiro.fr/api/randomuser"
    private const val API_URL_USERS =
        "https://www.amonteiro.fr/api/randomusers"

    //Création et réglage du client
    private val client  = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        //engine { proxy = ProxyBuilder.http("monproxy:1234") }
    }

    //GET Le JSON reçu sera parser en List<UserDTO>,
    //Crash si le JSON ne correspond pas
    suspend fun loadUser(): UserDTO {
        val response = client.get(API_URL_USER){
            //headers {
            //append("Authorization", "Bearer YOUR_TOKEN")
            //append("Custom-Header", "CustomValue")
            //}

            //Ajouter des paramètres à l'URL
            //parameter("limit", 10)
            //parameter("sort", "asc")
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body<UserDTO>()
    }

    suspend fun loadUsers(): List<UserDTO> {
        val response = client.get(API_URL_USERS){
            //headers {
            //append("Authorization", "Bearer YOUR_TOKEN")
            //append("Custom-Header", "CustomValue")
            //}

            //Ajouter des paramètres à l'URL
            //parameter("limit", 10)
            //parameter("sort", "asc")
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        return response.body<List<UserDTO>>()
    }

    //POST
    suspend fun postData(newObject: UserDTO): UserDTO {
        val response = client.post(API_URL_USER){
            setBody(newObject)
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }
        return response.body()
    }

    //Ferme le Client mais celui ci ne sera plus utilisable. Uniquement pour le main
    fun close() = client.close()
}

/* -------------------------------- */
// DTO
/* -------------------------------- */

//Possible qu'il y ait besoin de cette annotation en fonction du compilateur
@Serializable //KotlinX impose cette annotation
data class UserDTO(
    val name: String,
    val age: Int,
    val coord: CoordDTO? = null,
)

@Serializable
data class CoordDTO(
    val phone: String? = "-",
    val mail: String? = "aaa@bbb.fr",
)