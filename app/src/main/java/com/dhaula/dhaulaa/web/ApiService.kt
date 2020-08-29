package com.dhaula.dhaulaa.web

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.widget.Toast
import com.dhaula.dhaulaa.MApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.dhaula.dhaulaa.web.ApiCallback
import com.paytag.web.ApiException
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * [ApiService]
 */
abstract class ApiService<U, T : ApiRequest?, L : ApiResponse?> {
    var context: Context? = null
        private set

    constructor(context: Context) {
        this.context = context
    }

    constructor() {
        context = MApplication.getContext()
    }

    fun execute(request: T?, callback: ApiCallback<L?>) {
        if (!isNetworkAvailable) {
            callback!!.onComplete()
            Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show()
            return
        }
        if (callback == null) { //            throw new ApiException(ApiException.Kind.NULL, "Callback may not be null");
        } else if (aPI == null) {
            callback.onComplete()
            callback.onFailure(
                ApiException(
                    ApiException.Kind.NULL,
                    "API Interface may not be null"
                )
            )
        } else if (request == null) {
            callback.onComplete()
            callback.onFailure(ApiException(ApiException.Kind.NULL, "Request may not be null"))
            callback.onFailure(ApiException(ApiException.Kind.NULL, "Request may not be null"))
        } else if (!request.isValid) {
            callback.onComplete()
            callback.onFailure(ApiException(ApiException.Kind.INVALID_REQUEST, "Invalid request"))
        } else {
            val baseUrl = request.baseUrl
            if (TextUtils.isEmpty(baseUrl)) {
                callback.onComplete()
                callback.onFailure(ApiException(ApiException.Kind.NULL, "Base Url may not be null"))
                return
            }
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            val cache = context?.cacheDir?.let { Cache(it, cacheSize.toLong()) }
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(unsafeOkHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            Thread(Runnable {
                val call =
                    onExecute(retrofit.create(aPI), request)
                call?.enqueue(callback)
            }).start()
        }
    }

    val okHttp: OkHttpClient.Builder
        get() {
            val builder = OkHttpClient.Builder()
                .connectTimeout(
                    readTimeoutInSeconds.toLong(),
                    TimeUnit.SECONDS
                )
                .readTimeout(readTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
            builder.addInterceptor(interceptor)
            return builder
        }

    // Create a trust manager that does not validate certificate chains
    private val unsafeOkHttpClient:
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
            OkHttpClient.Builder
        private get() = try { // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslSocketFactory,
                (trustAllCerts[0] as X509TrustManager)
            )
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            builder.connectTimeout(
                    readTimeoutInSeconds.toLong(),
                    TimeUnit.SECONDS
                )
                .readTimeout(
                    readTimeoutInSeconds.toLong(),
                    TimeUnit.SECONDS
                )
            builder.addInterceptor(interceptor)
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    private val interceptor: HttpLoggingInterceptor
        private get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return interceptor
        }

    private val gson: Gson?
        private get() {
            if (sGson != null) {
                return sGson
            }
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setLenient()
            return gsonBuilder.create().also { sGson = it }
        }

    protected abstract val aPI: Class<U>?

    /**
     * @param api     to pass data in request
     * @param request to send on server
     */
    protected abstract fun onExecute(api: U, request: T): Call<L?>?

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    companion object {
        val readTimeoutInSeconds = 60

        /**
         * Use to build Gson object for serialization and deserialization of pojo
         */
        private var sGson: Gson? = null
    }
}

/*private fun <T, L : ApiResponse?> Call<T>?.enqueue(callback: ApiCallback<L>) {

}*/
