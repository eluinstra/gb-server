package dev.luin.digikoppeling.gb.server.service;

import java.net.URL;

import dev.luin.file.server.core.ValueObject;
import dev.luin.file.server.core.file.VirtualPath;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

//@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Url implements ValueObject<String>
{
	URL value;

	public Url(String url)
	{
		this(Try.success(url.contains("://0.0.0.0:") ? url.replace("://0.0.0.0:","://localhost:") : url)
				.mapTry(u -> new URL(u))
				.getOrElseThrow(() -> new IllegalArgumentException("Url is invalid")));
	}

	public Url(URL url)
	{
		value = url;
	}

	public Url append(@NonNull VirtualPath virtualPath)
	{
		return new Url(value.toExternalForm() + virtualPath.getValue());
	}

	public String getValue()
	{
		return value.toExternalForm();
	}
}
