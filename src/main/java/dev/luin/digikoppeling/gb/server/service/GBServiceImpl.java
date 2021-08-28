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
package dev.luin.digikoppeling.gb.server.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dev.luin.file.server.core.file.FileSystem;
import dev.luin.file.server.core.file.VirtualPath;
import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nl.logius.digikoppeling.gb._2010._10.ExternalDataReference;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class GBServiceImpl implements GBService
{
	@NonNull
	FileSystem fileSystem;
	@NonNull
	ExternalDataReferenceBuilder externalDataReferenceBuilder;

	@GET
	@Path("externalDataReference/{paths}")
	@Override
	public ExternalDataReference getExternalDataReference(@PathParam("paths") String...paths) throws GBServiceException
	{
		log.debug("getExternalDataReference {}",paths);
		val files = List.of(paths)
				.map(p -> new VirtualPath(p))
				//.map(p -> fileService.getFile(path).orElseThrow(() -> new GBServiceException(p + " not found!")))
				.map(p -> fileSystem.findFile(p).<GBServiceException>getOrElseThrow(() -> new GBServiceException(p + " not found!")));
		return externalDataReferenceBuilder.build(files);
	}
}
