package co.lopun.playground.dataTypes

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.list.apply.ap
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.*

sealed class ValidationError(val msg: String) {
    data class DoesNotContain(val value: String) : ValidationError("Did not contain $value")
    data class MaxLength(val value: Int) : ValidationError("Exceeded length of $value")
    data class NotAnEmail(val reasons: Nel<ValidationError>) : ValidationError("Not a valid email")
}

data class FormField(val label: String, val value: String)
data class Email(val value: String)

sealed class Rules<F>(A: ApplicativeError<F, Nel<ValidationError>>) : ApplicativeError<F, Nel<ValidationError>> by A {

    private fun FormField.contains(needle: String): Kind<F, FormField> =
        if (value.contains(needle, false)) just(this)
        else raiseError(ValidationError.DoesNotContain(needle).nel())

    private fun FormField.maxLength(maxLength: Int): Kind<F, FormField> =
        if (value.length <= maxLength) just(this)
        else raiseError(ValidationError.MaxLength(maxLength).nel())

    fun FormField.validateEmail(): Kind<F, Email> =
        map(
            contains("@"),
            maxLength(250),
            { Email(value) }
        ).handleErrorWith { raiseError(ValidationError.NotAnEmail(it).nel()) }

    object ErrorAccumulationStrategy :
        Rules<ValidatedPartialOf<Nel<ValidationError>>>(Validated.applicativeError(NonEmptyList.semigroup()))

    object FailFastStrategy :
        Rules<EitherPartialOf<Nel<ValidationError>>>(Either.applicativeError())

    companion object {
        infix fun <A> failFast(f: FailFastStrategy.() -> A): A = f(FailFastStrategy)
        infix fun <A> accumulateErrors(f: ErrorAccumulationStrategy.() -> A): A = f(ErrorAccumulationStrategy)
    }

}

// 에러 객체를 그대로 받아서 후치라를 하고싶을때 이방식으로 진행
fun accumulateErrorsStrategy() {
    Rules accumulateErrors {
        listOf(
            FormField("Invalid Email Domain Label", "nowhere.com"),
            FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this accumulates N errors
            FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
    // [Invalid(e=NonEmptyList(all=[NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@)]))])), Invalid(e=NonEmptyList(all=[NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@), MaxLength(value=250)]))])), Valid(a=Email(value=getlost@nowhere.com))]
}

// 결과값이 중요한 로직일 경우 아래 방식으로 진행
fun failFastStrategy() {
    Rules failFast {
        listOf(
            FormField("Invalid Email Domain Label", "nowhere.com"),
            FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this fails fast
            FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
    // [Left(a=NonEmptyList(all=[NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@)]))])), Left(a=NonEmptyList(all=[NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@)]))])), Right(b=Email(value=getlost@nowhere.com))]
}

