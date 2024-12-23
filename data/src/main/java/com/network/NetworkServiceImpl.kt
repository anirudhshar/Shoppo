package com.network

import android.util.Log
import com.model.AddressDomainModel
import com.model.CartItemModel
import com.model.CartModel
import com.model.CartSummary
import com.model.CategoryListModel
import com.model.OrdersListModel
import com.model.ProductListModel
import com.model.Request.AddCartRequestModel
import com.model.UserDomainModel
import com.model.request.AddToCartRequest
import com.model.request.AddressDataModel
import com.model.request.LoginRequest
import com.model.request.RegisterRequest
import com.model.response.CartResponse
import com.model.response.CartSummaryResponse
import com.model.response.CategoryListResponse
import com.model.response.OrdersListResponse
import com.model.response.PlaceOrderResponse
import com.model.response.ProductListResponse
import com.model.response.UserAuthResponse
import com.model.response.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class NetworkServiceImpl(val client: HttpClient) : NetWorkService {

    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2"

    override suspend fun addProductToCart(request: AddCartRequestModel, userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/${userId}"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Post,
            body = AddToCartRequest.fromCartRequestModel(request),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )
    }

    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {
        val url = if (category != null) {
            "$baseUrl/products/category/$category"
        } else {
            "$baseUrl/products"
        }

        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { dataModels: ProductListResponse ->
                dataModels.toProductList()
            }
        )
    }

    override suspend fun getCategories(): ResultWrapper<CategoryListModel> {

        val url = "$baseUrl/categories"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { categories: CategoryListResponse ->
                categories.toCategoryList()
            },
        )
    }

    override suspend fun getCart(userId: Long): ResultWrapper<CartModel> {

        val url = "$baseUrl/cart/${userId}"

        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )
    }

    override suspend fun updateQuantity(cartItemModel: CartItemModel,userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/${userId}/${cartItemModel.id}"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Put,
            body = AddToCartRequest(
                productId = cartItemModel.id,
                quantity = cartItemModel.quantity,
            ),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )


    }

    override suspend fun deleteItem(cartItemId: Int, userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/$userId/$cartItemId"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Delete,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )


    }

    override suspend fun getCartSummary(userId: Long): ResultWrapper<CartSummary> {
        val url = "$baseUrl/checkout/$userId/summary"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { cartSummary: CartSummaryResponse ->
                cartSummary.toCartSummary()
            }
        )
    }


    override suspend fun placeOrder(address: AddressDomainModel, userId: Long): ResultWrapper<Long> {
        val dataModel = AddressDataModel.fromAddressDomainModel(address)
        val url = "$baseUrl/orders/$userId"
        return makeWebRequest(url = url,
            method = HttpMethod.Post,
            body = dataModel,
            mapper = { orderRes: PlaceOrderResponse ->
                orderRes.data.id
            })
    }

    override suspend fun getOrderList(userId: Long): ResultWrapper<OrdersListModel> {

        val url = "$baseUrl/orders/${userId}"
        return makeWebRequest(url = url,
            method = HttpMethod.Get,
            mapper = { orderRes:OrdersListResponse ->
                orderRes.toDomainResponse()
            }
        )
    }


    override suspend fun login(email: String, password: String): ResultWrapper<UserDomainModel> {
        val url = "$baseUrl/login"
        Log.d("login", email)
        Log.d("login", password)
        return makeWebRequest(url = url,
            method = HttpMethod.Post,
            body = LoginRequest(email, password),
            mapper = { user: UserAuthResponse ->
                user.data.toDomainModel()
            }
        )


    }

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): ResultWrapper<UserDomainModel> {
        val url = "$baseUrl/signup"
        Log.d("reg", email)
        Log.d("reg", password)
        Log.d("reg", name)
        return makeWebRequest(url = url,
            method = HttpMethod.Post,
            body = RegisterRequest(email, password, name),
            mapper = { user: UserAuthResponse ->
                user.data.toDomainModel()
            })
    }

    suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method
                // Apply query parameters
                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }
                // Apply headers
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                // Set body for POST, PUT, etc.
                if (body != null) {
                    setBody(body)
                }

                // Set content type
                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

}