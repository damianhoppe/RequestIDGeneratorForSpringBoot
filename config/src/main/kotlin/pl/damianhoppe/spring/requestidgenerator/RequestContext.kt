package pl.damianhoppe.spring.requestidgenerator

private class DataAdapter {
    val inheritableThreadLocal = object: InheritableThreadLocal<MutableMap<String, String>>() {
        override fun childValue(parentValue: MutableMap<String, String>?): MutableMap<String, String> {
            if(parentValue == null)
                return mutableMapOf()
            return parentValue.toMutableMap()
        }

        override fun initialValue(): MutableMap<String, String> {
            return mutableMapOf()
        }
    }
}

/**
 * Allows to manage and access request data saved within a current thread
 * in which it is handled.
 *
 * Controlled by request id generator, so data is not managed for requests not
 * supported by the generator.
 * They are not cleared after the generator finishes processing the request,
 * which may lead to access to this data in next requests.
 */
class RequestContext {
    companion object {
        private const val REQUEST_ID_KEY: String = "RequestId"

        private val dataAdapter = DataAdapter()

        /**
         * Associates specific key with specific value for request handled in current thread.
         */
        fun put(key: String, value: String) {
            dataAdapter.inheritableThreadLocal.get()[key] = value
        }

        /**
         * Returns the value corresponding to the given key for request handled in current thread,
         * or null if such a key is not present.
         */
        fun get(key: String): String? {
            return dataAdapter.inheritableThreadLocal.get()[key]
        }

        /**
         * Removes the specified key and its corresponding value for request handled in current thread.
         */
        fun remove(key: String): String? {
            return dataAdapter.inheritableThreadLocal.get().remove(key)
        }

        /**
         * Returns request id for current request handled in current thread.
         */
        val requestId: String?
            get() {
                return dataAdapter.inheritableThreadLocal.get()[REQUEST_ID_KEY]
            }

        /**
         * Sets request id for current request handled in current thread.
         */
        fun setRequestId(requestId: String) {
            dataAdapter.inheritableThreadLocal.get()[REQUEST_ID_KEY] = requestId
        }

        /**
         * Removes request id for current request handled in current thread.
         */
        fun clearRequestId() {
            dataAdapter.inheritableThreadLocal.get().remove(REQUEST_ID_KEY)
        }
        /**
         * Removes data for current request handled in current thread.
         */
        fun clear() {
            dataAdapter.inheritableThreadLocal.get().clear()
        }
    }
}