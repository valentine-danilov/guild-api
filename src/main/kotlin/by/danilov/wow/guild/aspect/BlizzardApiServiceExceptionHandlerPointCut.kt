package by.danilov.wow.guild.aspect

import com.github.kittinunf.fuel.core.FuelError
import io.javalin.http.HttpResponseException
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
open class BlizzardApiServiceExceptionHandlerPointCut {

    @AfterThrowing(pointcut = "execution(* by.danilov.wow.guild.service.blizzard.BlizzardApiService.*(..))", throwing = "exception")
    fun wrapException(exception: FuelError) {
        throw HttpResponseException(exception.response.statusCode, exception.response.responseMessage)
    }

}
