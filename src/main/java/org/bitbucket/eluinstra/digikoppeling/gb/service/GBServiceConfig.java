/**
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
package org.bitbucket.eluinstra.digikoppeling.gb.service;

import javax.xml.datatype.DatatypeConfigurationException;

import org.bitbucket.eluinstra.digikoppeling.gb.common.ExternalDataReferenceBuilder;
import org.bitbucket.eluinstra.fs.core.file.FSFileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GBServiceConfig
{
	@Autowired
	FSFileDAO fsFileDAO;
	@Value("${fs.baseUrl}")
	String baseUrl;

	@Bean
	public GBService gbService() throws DatatypeConfigurationException
	{
		return new GBServiceImpl(fsFileDAO,new ExternalDataReferenceBuilder(baseUrl));
	}
}
