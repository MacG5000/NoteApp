package com.task.noteapp

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import androidx.viewbinding.ViewBinding
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import java.lang.reflect.Field
import java.lang.reflect.Modifier

@RunWith(MockitoJUnitRunner::class)
abstract class BaseUnitTest {

    @get:Rule
    val overrideSchedulersRule = RxSchedulerOverrideRule()

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun updateInaccessibleFieldValue(clazz: Any, fieldName: String, newValue: Any) =
        (
                clazz.takeIf { it is Class<*> } as? Class<*>
                    ?: clazz.javaClass
                ).getDeclaredField(fieldName).let { field ->
                field.isAccessible = true
                Field::class.java.getDeclaredField("modifiers").also { modifiers ->
                    modifiers.isAccessible = true
                    modifiers.setInt(field, field.modifiers and Modifier.FINAL.inv())
                }
                field.set(clazz, newValue)
            }

    @Suppress("SwallowedException")
    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun updateAccessibleFieldValue(
        clazz: Any,
        fieldName: String,
        newValue: Any?
    ) {
        val f = try {
            clazz.javaClass.getField(fieldName)
        } catch (noField: NoSuchFieldException) {
            clazz.javaClass.getDeclaredField(fieldName)
        }
        f.isAccessible = true
        f[clazz] = newValue
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getInaccessibleFieldValue(clazz: Any, fieldName: String) =
        (
                clazz.takeIf { it is Class<*> } as? Class<*>
                    ?: clazz.javaClass
                ).getDeclaredField(fieldName).apply {
                isAccessible = true
            }.get(clazz) as T

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun updateInaccessibleSuperFieldValue(clazz: Any, fieldName: String, newValue: Any) =
        (
                clazz.takeIf { it is Class<*> }
                    ?: clazz
                ).javaClass.superclass?.getDeclaredField(fieldName)?.let { field ->
                field.isAccessible = true
                Field::class.java.getDeclaredField("modifiers").also { modifiers ->
                    modifiers.isAccessible = true
                    modifiers.setInt(field, field.modifiers and Modifier.FINAL.inv())
                }
                field.set(clazz, newValue)
            }

    @After
    fun tearDownMockito() {
        Mockito.validateMockitoUsage()
    }

    fun mockBinding(binding: ViewBinding) {
        binding.javaClass.fields.forEach {
            if (it.type != String::class.java) {
                updateAccessibleFieldValue(binding, it.name, Mockito.mock(it.type))
            }
        }
    }

    class RxSchedulerOverrideRule : TestRule {

        /**
         * @return statement after evaluation
         * JUnit Android SDK dependency on AndroidSchedulers.mainThread() must be removed for Rx unit testing, so Schedulers.trampoline added instead;
         */
        override fun apply(base: Statement, description: Description): Statement {
            return object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
                    try {
                        base.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                    }
                }
            }
        }
    }

    class ExpectedException(val expectedMessage: String) : TestRule {

        override fun apply(base: Statement, description: Description?): Statement {
            return object : Statement() {
                override fun evaluate() {
                    try {
                        base.evaluate()
                    } catch (e: Exception) {
                        if (expectedMessage != e.localizedMessage) {
                            throw e
                        }
                    }
                }
            }
        }
    }
}