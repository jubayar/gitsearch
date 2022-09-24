package net.red.green.core.network

interface ApiFactory {
    fun <T> create(service: Class<T>): T
}
