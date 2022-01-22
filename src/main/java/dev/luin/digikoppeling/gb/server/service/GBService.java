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

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import nl.logius.digikoppeling.gb._2010._10.ExternalDataReference;

@WebService(name = "GBService", targetNamespace="http://luin.dev/digikoppeling/gb/server/1.0", serviceName = "GBService", endpointInterface = "GBServiceSoapBinding", portName = "GBServicePort")
public interface GBService
{
  @WebResult(name="external-data-reference", targetNamespace="http://www.logius.nl/digikoppeling/gb/2010/10")
	ExternalDataReference getExternalDataReference(@WebParam(name="path") @XmlElement(required=true) String...paths) throws GBServiceException;
}
