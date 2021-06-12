package by.danilov.wow.guild.aspect

import com.github.kittinunf.fuel.core.FuelError
import feign.FeignException
import io.javalin.http.HttpResponseException
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.w3c.dom.css.Counter

@Aspect
@Component
open class BlizzardApiServiceExceptionHandlerPointCut {

    @AfterThrowing(pointcut = "execution(* by.danilov.wow.guild.service.blizzard.BlizzardApiServiceImpl.*(..))", throwing = "exception")
    fun wrapFeignException(exception: FeignException) {
        throw HttpResponseException(exception.status(), exception.message ?: "Error")
    }

    @AfterThrowing(pointcut = "execution(* by.danilov.wow.guild.service.blizzard.BlizzardApiServiceImpl.*(..))", throwing = "exception")
    fun wrapFuelException(exception: FuelError) {
        throw HttpResponseException(exception.response.statusCode, exception.response.responseMessage)
    }

}
