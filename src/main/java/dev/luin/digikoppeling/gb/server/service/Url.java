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

import static dev.luin.file.server.core.ValueObject.isNotNull;

import dev.luin.file.server.core.ValueObject;
import dev.luin.file.server.core.file.VirtualPath;
import io.vavr.control.Try;
import java.net.URL;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

//@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode
@ToString
public class Url implements ValueObject<String>
{
	URL value;

	public Url(String url)
	{
		this(
				Try.success(url)
						.flatMap(isNotNull())
						.map(u -> u.contains("://0.0.0.0:") ? u.replace("://0.0.0.0:", "://localhost:") : u)
						.mapTry(URL::new)
						.getOrElseThrow(e -> new IllegalArgumentException("Url is invalid", e)));
	}

	public Url(URL url)
	{
		value = url;
	}

	public Url append(@NonNull VirtualPath virtualPath)
	{
		return new Url(value.toExternalForm() + "/" + virtualPath.getValue());
	}

	public String getValue()
	{
		return value.toExternalForm();
	}
}
