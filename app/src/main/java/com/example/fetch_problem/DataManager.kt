package com.example.fetch_problem

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.net.URL

/**
 * Manages and processes contact group data by reading from a JSON source and organizing it into a map.
 */
class DataManager {
    // A mutable map to store contact groups keyed by their list ID.
    private val dataInMap: MutableMap<Int, MutableList<ContactGroup>> = mutableMapOf()

    /**
     * Reads contact group data from a JSON source and populates the map with non-empty contact groups.
     */
    fun readJson() = runBlocking {
        launch(Dispatchers.IO) {
            val contactGroups = fetchContactGroups()
            contactGroups.forEach {
                if (it.name != null && it.name.isNotBlank()) {
                    if (dataInMap.containsKey(it.listId)) {
                        dataInMap[it.listId]!!.add(it)
                    } else {
                        dataInMap[it.listId] = mutableListOf(it)
                    }
                }
            }
        }
    }

    /**
     * Retrieves the map of contact groups organized by their list ID.
     *
     * @return An immutable copy of the map.
     */
    fun getDataInMap(): Map<Int, MutableList<ContactGroup>> = dataInMap.toMap()

    /**
     * Fetches contact group data from a specified JSON URL.
     */
    private suspend fun fetchContactGroups(): List<ContactGroup> =
        withContext(Dispatchers.IO) {
            URL(Configs.JSON_URL).openStream().use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    val contactGroupListType = object : TypeToken<List<ContactGroup>>() {}.type
                    Gson().fromJson(reader, contactGroupListType)
                }
            }
        }
}
