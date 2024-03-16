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

import dev.luin.file.server.core.file.FSFile;
import dev.luin.file.server.core.file.Md5Checksum;
import dev.luin.file.server.core.file.VirtualPath;
import io.vavr.collection.Seq;
import java.time.Instant;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import nl.logius.digikoppeling.gb._2010._10.ChecksumType;
import nl.logius.digikoppeling.gb._2010._10.DataReference;
import nl.logius.digikoppeling.gb._2010._10.DataReference.Content;
import nl.logius.digikoppeling.gb._2010._10.DataReference.Lifetime;
import nl.logius.digikoppeling.gb._2010._10.DataReference.Transport;
import nl.logius.digikoppeling.gb._2010._10.DataReference.Transport.Location;
import nl.logius.digikoppeling.gb._2010._10.DatetimeType;
import nl.logius.digikoppeling.gb._2010._10.ExternalDataReference;
import nl.logius.digikoppeling.gb._2010._10.GbProfile;
import nl.logius.digikoppeling.gb._2010._10.UrlType;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExternalDataReferenceBuilder
{
	@NonNull
	DatatypeFactory datatypeFactory;
	@NonNull
	Url baseUrl;

	public ExternalDataReferenceBuilder(@NonNull final Url baseUrl) throws DatatypeConfigurationException
	{
		this.datatypeFactory = DatatypeFactory.newInstance();
		this.baseUrl = baseUrl;
	}

	public ExternalDataReference build(@NonNull final Seq<FSFile> fsFiles)
	{
		val result = new ExternalDataReference();
		result.setProfile(GbProfile.DIGIKOPPELING_GB_1_0);
		result.getDataReference().addAll(fsFiles.map(this::createDataReference).asJava());
		return result;
	}

	private DataReference createDataReference(final FSFile fsFile)
	{
		val result = new DataReference();
		result.setContextId(fsFile.getMd5Checksum().getValue());
		result.setLifetime(createLifetime(fsFile));
		result.setContent(createContent(fsFile));
		result.setTransport(createTransport(fsFile.getVirtualPath()));
		return result;
	}

	private Lifetime createLifetime(final FSFile fsFile)
	{
		val result = new Lifetime();
		result.setCreationTime(createTime(fsFile.getValidTimeFrame().getStartDate()));
		result.setExpirationTime(createTime(fsFile.getValidTimeFrame().getEndDate()));
		return result;
	}

	private DatetimeType createTime(final Instant date)
	{
		if (date == null)
			return null;
		val result = new DatetimeType();
		result.setType("xs:dateTime");
		result.setValue(toGregorianCalendar(date));
		return result;
	}

	private XMLGregorianCalendar toGregorianCalendar(final Instant date)
	{
		val cal = new GregorianCalendar();
		cal.setTimeInMillis(date.toEpochMilli());
		return datatypeFactory.newXMLGregorianCalendar(cal);
	}

	private Content createContent(final FSFile fsFile)
	{
		val result = new Content();
		result.setFilename(fsFile.getName().getValue());
		result.setContentType(fsFile.getContentType().getValue());
		result.setSize(fsFile.getFileLength().toBigInteger());
		result.setChecksum(createMD5Checksum(fsFile.getMd5Checksum()));
		return result;
	}

	private ChecksumType createMD5Checksum(final Md5Checksum md5checksum)
	{
		val result = new ChecksumType();
		result.setType("MD5");
		result.setValue(md5checksum.getValue());
		return result;
	}

	private Transport createTransport(final VirtualPath virtualPath)
	{
		val result = new Transport();
		result.setLocation(createLocation(virtualPath));
		return result;
	}

	private Location createLocation(final VirtualPath virtualPath)
	{
		val result = new Location();
		result.setSenderUrl(createUrl(virtualPath));
		return result;
	}

	private UrlType createUrl(final VirtualPath virtualPath)
	{
		val result = new UrlType();
		result.setType("xs:anyURI");
		result.setValue(baseUrl.append(virtualPath).getValue());
		return result;
	}

}
