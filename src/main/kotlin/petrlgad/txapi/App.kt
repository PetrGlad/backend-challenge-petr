/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package petrlgad.txapi

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import spark.Request
import spark.Response
import spark.Spark.*
import java.math.BigDecimal
import java.sql.Connection
import java.util.*
import kotlin.collections.ArrayList

val LOGGER = LoggerFactory.getLogger("petrglad.invoices.api")

const val jsonContentType = "application/json; charset=utf-8"

val gson = Gson()

typealias InvoiceId = UUID
typealias CustomerId = UUID

data class Customer(
    val id: CustomerId,
    val name: String,
    val address: String
)

data class InvoicePosition(
    val description: String,
    val amount: Float
)

data class Invoice(
    val id: InvoiceId,
    val code: String, // human readable invoice code/number
    val title: String,
    val description: String?,
    val issuedAt: String, // ISO date time
    val customer: Customer,
    val positions: List<InvoicePosition>,
    val totalAmount: Float
)

val PREDEFINED_CUSTOMER_ID = UUID.randomUUID()
val customers = mapOf(
    PREDEFINED_CUSTOMER_ID to Customer(PREDEFINED_CUSTOMER_ID, "Best Customer", "Berlin")
)

val invoices = listOf<Invoice>()

fun exceptionToClientError(resp: Response, handler: () -> String): String =
    try {
        handler()
    } catch (ex: Exception) {
        LOGGER.warn("Request handling error.", ex)
        resp.status(400)
        resp.type(jsonContentType)
        gson.toJson("FAILED")
    }

fun totalAmount(invoice: Invoice) : Float {
}

fun main(_args: Array<String>) {
    port(8080)
    post("/invoices/create") { req: Request, resp: Response ->
        Invoice(
            id = UUID.randomUUID(),
            totalAmount = 14.63f
        )
    }


//    get("/accounts/list") { req: Request, resp: Response ->
//        exceptionToClientError(resp) {
//            dataSource.connection.use { conn ->
//                val result = ArrayList<Account>()
//                val stmt = conn.prepareStatement("select id, value, currency from account")
//                val rs = stmt.executeQuery()
//                while (rs.next()) {
//                    result.add(
//                        Account(
//                            rs.getString(1),
//                            rs.getBigDecimal(2),
//                            rs.getString(3)
//                        )
//                    )
//                }
//                resp.type(jsonContentType)
//                gson.toJson(result)
//            }
//        }
//    }
//    put("/accounts/by-id/:id") { req: Request, resp: Response ->
//        exceptionToClientError(resp) {
//            require(req.contentType().startsWith("application/json")) {
//                "JSON body is required, got content type ${req.contentType()}"
//            }
//            dataSource.connection.use { conn ->
//                val acc = gson.fromJson(req.body(), Account::class.java)
//                val stmt = conn.prepareStatement("insert into account (id, value, currency) values (?, ?, ?) ")
//                stmt.setString(1, acc.id)
//                stmt.setBigDecimal(2, acc.value)
//                stmt.setString(3, acc.currency)
//                stmt.executeUpdate()
//                conn.commit()
//
//                resp.type(jsonContentType)
//                gson.toJson("OK")
//            }
//        }
//    }
//    put("/transfers/by-owner/:owner/by-id/:id") { req: Request, resp: Response ->
//        exceptionToClientError(resp) {
//            require(req.contentType().startsWith("application/json")) {
//                "JSON body is required, got content type ${req.contentType()}"
//            }
//            dataSource.connection.use { conn ->
//                val tx = gson.fromJson(req.body(), Transfer::class.java)
//                LOGGER.info("Got transfer $tx")
//                require(updateBalance(conn, tx.from_account_id, tx.currency, -tx.amount) == 1) {
//                    "Source account #${tx.from_account_id} is not found or cannot be updated due to overdraft."
//                }
//                require(updateBalance(conn, tx.to_account_id, tx.currency, tx.amount) == 1) {
//                    "Destination account #${tx.to_account_id} is not found."
//                }
//                conn.commit()
//
//                resp.type(jsonContentType)
//                gson.toJson("OK")
//            }
//        }
//    }
}
