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

import javax.xml.ws.WebFault;

@WebFault(name="GBServiceException", targetNamespace="http://bitbucket.org/eluinstra/digikoppeling/gb/1.0")
public class GBServiceException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public GBServiceException()
	{
		super();
	}

	public GBServiceException(String message, Throwable cause)
	{
		super(message,cause);
	}

	public GBServiceException(String message)
	{
		super(message);
	}

	public GBServiceException(Throwable cause)
	{
		super(cause);
	}
}
