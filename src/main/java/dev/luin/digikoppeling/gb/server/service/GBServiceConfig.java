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

import dev.luin.file.server.core.file.FileSystem;
import dev.luin.file.server.web.WebConfig;
import jakarta.xml.ws.Endpoint;

import javax.xml.datatype.DatatypeConfigurationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.apache.cxf.endpoint.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:dev/luin/digikoppeling/gb/server/default.properties"}, ignoreResourceNotFound = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GBServiceConfig extends WebConfig
{
	@Bean
	public GBService gbService(@Autowired FileSystem fileSystem, @Value("${server.baseUrl}") String baseUrl) throws DatatypeConfigurationException
	{
		return new GBServiceImpl(fileSystem, new ExternalDataReferenceBuilder(new Url(baseUrl + "/download")));
	}

	@Bean
	public Endpoint gbServiceEndpoint(GBService gbService)
	{
		return publishEndpoint(gbService, "/gb", "http://luin.dev/digikoppeling/gb/server/1.0", "GBService", "GBServicePort");
	}

	@Bean
	public Server createGBJAXRSServer(GBService gbService)
	{
		return createJAXRSServer(GBServiceImpl.class, gbService, "/gb");
	}
}
