/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.topnewapp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
// Extension function for StateFlow to collect its value in tests
fun <T> StateFlow<T>.getOrAwaitValueTest(time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    val job = thread {
        try {
            data = value
        } catch (e: Exception) {
            fail("Error while getting StateFlow value: ${e.message}")
        } finally {
            latch.countDown()
        }
    }

    // Don't wait indefinitely
    if (!latch.await(time, timeUnit)) {
        fail("StateFlow value was never set.")
    }

    job.join()
    return data as T
}