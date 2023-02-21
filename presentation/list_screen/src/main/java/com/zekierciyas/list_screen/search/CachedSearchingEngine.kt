package com.zekierciyas.list_screen.search

import com.zekierciyas.cache.model.satellite_list.SatelliteListItem
import kotlin.math.pow

/** HelperClass for the algorithm of Rabin-Karp string matching algorithm compares the given pattern against
 * all positions in the given text.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Rabin%E2%80%93Karp_algorithm">link</a>
 */
class CachedSearchingEngine {

    private var givenDataList: List<SatelliteListItem> = arrayListOf()
    private val prime = 101
    private var cacheSize: Int = 5
    private val cacheMap = LinkedHashMap<String, List<SatelliteListItem>>(cacheSize, 0.75f, true)


    /** @param searchKey: Key to search from [givenDataList] */
    fun search(searchKey: String): List<SatelliteListItem> {
        return searchFilter(searchKey)
    }

    /** Init list of data that will be searching on */
    fun init(givenDataList: List<SatelliteListItem>) = apply {
        this.givenDataList = givenDataList
    }

    /** Max cache size for getting reesult from previous searches*/
    fun cacheSize(cacheSize: Int = 5) = apply {
        this.cacheSize = cacheSize
    }

    private fun calculateHash(str: String): Long {
        var hash = 0L
        for (i in str.indices) {
            hash += str[i].toInt() * prime.toDouble().pow(i).toLong()
        }
        return hash
    }

    private fun generateHashes(searchKey: String): Pair<Long, Long> {
        val searchKeyHash = calculateHash(searchKey)
        val x = prime.toDouble().pow(searchKey.length - 1).toLong()
        return Pair(searchKeyHash, x)
    }

    private fun recalculateHash(hash: Long, x: Long, oldChar: Char, newChar: Char): Long {
        var newHash = hash - oldChar.toInt() * x
        newHash = newHash * prime + newChar.toInt()
        return newHash
    }

    private fun searchFilter(searchKey: String): List<SatelliteListItem> {
        if (cacheMap.containsKey(searchKey)) {
            return cacheMap[searchKey]!!
        }

        val filteredList = mutableListOf<SatelliteListItem>()
        val (searchKeyHash, x) = generateHashes(searchKey)


        for (item in givenDataList) {
            val data = item.name.toLowerCase()
            var dataHash = calculateHash(data.substring(0, searchKey.length))
            var i = 0

            while (i <= data.length - searchKey.length) {
                if (searchKeyHash == dataHash) {
                    if (searchKey == data.substring(i, i + searchKey.length)) {
                        filteredList.add(item)
                        break
                    }
                }

                if (i < data.length - searchKey.length) {
                    dataHash = recalculateHash(dataHash, x, data[i], data[i + searchKey.length])
                }
                i++
            }
        }

        if (cacheMap.size >= cacheSize) {
            cacheMap.remove(cacheMap.keys.first())
        }
        cacheMap[searchKey] = filteredList

        return filteredList
    }
}
