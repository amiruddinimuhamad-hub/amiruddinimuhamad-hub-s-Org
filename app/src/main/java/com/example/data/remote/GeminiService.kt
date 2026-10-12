package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun generateDateIdeas(location: String = "Tokyo & London"): List<String> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackDateIdeas()
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val prompt = """
                Suggest 4 romantic, creative date ideas for a long-distance couple (one person in London, one person in Tokyo reuniting soon or doing a virtual date).
                Format each idea as a concise 1-sentence description starting with an emoji.
                Return ONLY the 4 bullet lines.
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (response.isSuccessful && responseBody.isNotBlank()) {
                val root = JSONObject(responseBody)
                val candidates = root.optJSONArray("candidates")
                val content = candidates?.optJSONObject(0)?.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                val text = parts?.optJSONObject(0)?.optString("text") ?: ""

                val lines = text.lines()
                    .map { it.trim().removePrefix("- ").removePrefix("* ") }
                    .filter { it.isNotBlank() }

                if (lines.isNotEmpty()) return@withContext lines.take(4)
            }
        } catch (e: Exception) {
            Log.e("GeminiService", "Error generating date ideas: ${e.message}")
        }

        getFallbackDateIdeas()
    }

    suspend fun generatePersonalizedQuote(): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackQuote()
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val prompt = "Write one tender, poetic quote (max 20 words) for long-distance partners separated by 5,420 miles under the same moon. Do not include quotes or author names."

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (response.isSuccessful && responseBody.isNotBlank()) {
                val root = JSONObject(responseBody)
                val candidates = root.optJSONArray("candidates")
                val content = candidates?.optJSONObject(0)?.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                val text = parts?.optJSONObject(0)?.optString("text")?.trim() ?: ""
                if (text.isNotBlank()) {
                    return@withContext text.replace("\"", "")
                }
            }
        } catch (e: Exception) {
            Log.e("GeminiService", "Error generating quote: ${e.message}")
        }

        getFallbackQuote()
    }

    private fun getFallbackDateIdeas(): List<String> = listOf(
        "🍵 Midnight Matcha & Sunrise Tea: Brew matcha in Tokyo at 8am while brewing London chamomile at midnight together on video.",
        "🎨 Dual Museum Tour: Virtually walk through the Mori Art Museum while swapping voice notes about our favorite exhibits.",
        "🎧 Synchronized Starlight Walk: Put earphones in and listen to the same Lo-fi playlist while gazing at the same waxing gibbous moon.",
        "🍜 Secret Recipe Challenge: Order the exact same ramen broth bowl to our doors and rate who slurps first."
    )

    private fun getFallbackQuote(): String {
        val quotes = listOf(
            "Distance is not about miles, it's just the space between our next memories.",
            "Two cities, eight hours apart, yet whispering to the exact same night sky.",
            "Every mile between London and Tokyo is just another reason to hold on tighter.",
            "You are my favorite notification in every time zone."
        )
        return quotes.random()
    }
}
