/*
 *   Copyright 2020 Leon Latsch
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package dev.leonlatsch.photok

import dev.leonlatsch.photok.other.empty
import dev.leonlatsch.photok.security.EncryptionManager
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EncryptionManagerTests {

    private val validPassword = "abc123"

    var encryptionManager: EncryptionManager = EncryptionManager()

    @Test
    fun initializeTest() {
        encryptionManager.initialize(validPassword)

        assertTrue(encryptionManager.isReady)
    }

    @Test
    fun initializeFailTest() {
        encryptionManager.initialize(String.empty)

        assertFalse(encryptionManager.isReady)
    }
}