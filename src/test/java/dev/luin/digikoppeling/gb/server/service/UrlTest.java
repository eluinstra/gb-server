/*
 * Copyright 2020 E.Luinstra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.luin.digikoppeling.gb.server.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(Lifecycle.PER_CLASS)
public class UrlTest
{
	@Test
	void testValue()
	{
		val url = new Url("https://example.com/files");
		assertThat(url.getValue()).isEqualTo("https://example.com/files");
	}

	@Test
	void testZeroDotZeroReplacedWithLocalhost()
	{
		val url = new Url("http://0.0.0.0:8080/files");
		assertThat(url.getValue()).isEqualTo("http://localhost:8080/files");
	}

	@ParameterizedTest
	@ValueSource(strings = { "https://example.com", "http://localhost:8443/a/b" })
	void testValid(String url)
	{
		assertDoesNotThrow(() -> new Url(url));
	}

	@Test
	void testInvalid()
	{
		assertThrows(IllegalArgumentException.class, () -> new Url("not a url"));
		assertThrows(IllegalArgumentException.class, () -> new Url((String)null));
	}

	@Test
	void testEquality()
	{
		assertThat(new Url("https://example.com/a")).isEqualTo(new Url("https://example.com/a"));
		assertThat(new Url("https://example.com/a")).isNotEqualTo(new Url("https://example.com/b"));
	}
}
