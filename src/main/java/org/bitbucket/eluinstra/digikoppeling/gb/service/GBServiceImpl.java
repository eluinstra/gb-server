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

import java.util.ArrayList;
import java.util.Optional;

import org.bitbucket.eluinstra.digikoppeling.gb.common.ExternalDataReferenceBuilder;
import org.bitbucket.eluinstra.fs.core.file.FSFile;
import org.bitbucket.eluinstra.fs.core.file.FSFileDAO;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.FieldDefaults;
import nl.logius.digikoppeling.gb._2010._10.ExternalDataReference;

@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class GBServiceImpl implements GBService
{
	@NonNull
	FSFileDAO fsFileDAO;
	ExternalDataReferenceBuilder externalDataReferenceBuilder;

	@Override
	public ExternalDataReference getExternalDataReference(String...paths) throws GBServiceException
	{
		val files = new ArrayList<FSFile>();
		for (val path: paths)
		{
			Optional<FSFile> fsFile = fsFileDAO.findFileByVirtualPath(path);
			files.add(fsFile.orElseThrow(() -> new GBServiceException(path + " not found!")));
		}
		return externalDataReferenceBuilder.build(files);
	}

}
