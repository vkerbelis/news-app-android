package com.example.newsapp

import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun testAddition_isCorrect() {
        assertEquals("Simple algebra not working", 4, (2 + 2).toLong())
    }
}