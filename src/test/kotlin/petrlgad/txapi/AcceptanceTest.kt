package petrlgad.txapi

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration

val GSON = Gson()

fun getApiUrl(urlSuffix: String) = URI.create("http://localhost:8080/$urlSuffix")


fun main() {
    val client: HttpClient = createHttpClient()

    // Create an invoice
    val sampleInvoice = hashMapOf(
        "code" to "inv1234",
        "title" to "lots of nuts",
        "description" to "described here",
        "issuedAt" to "2022-02-01T12:44:13Z",
        "customerId" to PREDEFINED_CUSTOMER_ID,
        "positions" to listOf(
            mapOf(
                "description" to "first item",
                "amount" to 14.63
            )
        )
    )
    doPost(
        client, "invoices/create",
        sampleInvoice
    ).also {
        require(it.statusCode() == 200) { "Failed to create 1st account: $it" }
        val expectedInvoice = hashMapOf(
            "code" to "inv1234",
            "title" to "lots of nuts",
            "description" to "described here",
            "issuedAt" to "2022-02-01T12:44:13Z",
            "customerId" to PREDEFINED_CUSTOMER_ID,
            "positions" to listOf(
                mapOf(
                    "description" to "first item",
                    "amount" to 14.63
                )
            ),
            "totalAmount" to 14.63
        )
        require(GSON.fromJson(it.body(), HashMap::class.java) == expectedInvoice)

    }
//    doPut(client, "accounts/by-id/2",
//            hashMapOf(
//                    "id" to "2",
//                    "currency" to "EUR",
//                    "value" to 0.0))
//            .also {
//                require(it.statusCode() == 200) { "Failed to create 2nd account: $it" }
//            }
//
//    // Non existing source account
//    doPut(client, "transfers/by-owner/me-at-home/by-id/1",
//            hashMapOf(
//                    "id" to "1",
//                    "currency" to "EUR",
//                    "amount" to 1.0,
//                    "from_account_id" to 321,
//                    "to_account_id" to 2))
//            .also {
//                require(it.statusCode() == 400) { "Transfer failed: $it" }
//            }
//    // Non existing target account
//    doPut(client, "transfers/by-owner/me-at-home/by-id/1",
//            hashMapOf(
//                    "id" to "1",
//                    "currency" to "EUR",
//                    "amount" to 1.0,
//                    "from_account_id" to 321,
//                    "to_account_id" to 2))
//            .also {
//                require(it.statusCode() == 400) { "Transfer failed: $it" }
//            }
//    // Overdraft
//    doPut(client, "transfers/by-owner/me-at-home/by-id/1",
//            hashMapOf(
//                    "id" to "1",
//                    "currency" to "EUR",
//                    "amount" to 99999.0,
//                    "from_account_id" to 1,
//                    "to_account_id" to 2))
//            .also {
//                require(it.statusCode() == 400) { "Transfer failed: $it" }
//            }
//
//    // Successful transfer
//    doPut(client, "transfers/by-owner/me-at-home/by-id/1",
//            hashMapOf(
//                    "id" to "1",
//                    "currency" to "EUR",
//                    "amount" to 45.0,
//                    "from_account_id" to 1,
//                    "to_account_id" to 2))
//            .also {
//                require(it.statusCode() == 200) { "Transfer failed: $it" }
//            }
//
//    doGet(client, "accounts/list")
//            .also {
//                require(it.statusCode() == 200) { "Cannot det accounts list: $it" }
//                require(it.headers().firstValue("Content-Type").map {it.startsWith("application/json")}.get()) {
//                    "Accounts list format is not JSON: $it"
//                }
//                require(GSON.fromJson(it.body(), ArrayList::class.java) ==
//                        arrayListOf(
//                                hashMapOf(
//                                        "id" to "1",
//                                        "currency" to "EUR",
//                                        "value" to 355.0),
//                                hashMapOf(
//                                        "id" to "2",
//                                        "currency" to "EUR",
//                                        "value" to 45.0)))
//                { "Unexpected accounts state: $it" }
//            }
}


private fun doPost(client: HttpClient, urlSuffix: String, body: Any): HttpResponse<String> {
    val request = HttpRequest.newBuilder()
        .uri(getApiUrl(urlSuffix))
        .timeout(Duration.ofMinutes(1))
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(GSON.toJson(body)))
        .build()
    return client.send(request, BodyHandlers.ofString(Charsets.UTF_8))
}

private fun doPut(client: HttpClient, urlSuffix: String, body: Any): HttpResponse<String> {
    val request = HttpRequest.newBuilder()
        .uri(getApiUrl(urlSuffix))
        .timeout(Duration.ofMinutes(2))
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(GSON.toJson(body)))
        .build()
    return client.send(request, BodyHandlers.ofString(Charsets.UTF_8))
}

private fun doGet(client: HttpClient, urlSuffix: String): HttpResponse<String> {
    val request = HttpRequest.newBuilder()
        .uri(getApiUrl(urlSuffix))
        .timeout(Duration.ofMinutes(2))
        .build()
    return client.send(request, BodyHandlers.ofString(Charsets.UTF_8))
}

private fun createHttpClient(): HttpClient =
    HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(2))
        .build()
