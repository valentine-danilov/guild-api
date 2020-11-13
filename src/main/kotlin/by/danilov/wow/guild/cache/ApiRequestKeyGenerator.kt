package by.danilov.wow.guild.cache

import org.springframework.cache.interceptor.KeyGenerator
import java.lang.reflect.Method

class ApiRequestKeyGenerator : KeyGenerator {
    override fun generate(target: Any, method: Method, vararg params: Any?): Any {
        var paramsKeyPart = ""
        for (param in params) {
            if (param is String) {
                paramsKeyPart += "_$param"
            } else if (param is List<*>) {
                paramsKeyPart += "_$param"
            }
        }
        return "${target.javaClass.simpleName}_${method.name}_$paramsKeyPart"
    }
}
