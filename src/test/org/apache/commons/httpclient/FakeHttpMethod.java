/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//httpclient/src/test/org/apache/commons/httpclient/FakeHttpMethod.java,v 1.2 2004/10/31 14:04:13 olegk Exp $
 * $Revision: 155418 $
 * $Date: 2005-02-26 08:01:52 -0500 (Sat, 26 Feb 2005) $
 * ====================================================================
 *
 *  Copyright 1999-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * 
 */


package org.apache.commons.httpclient;

/** 
 * For test-nohost testing purposes only.
 *
 * @author <a href="mailto:jsdever@apache.org">Jeff Dever</a>
 */
public class FakeHttpMethod extends HttpMethodBase{

    public FakeHttpMethod(){
        super();
    }

    public FakeHttpMethod(String path){
        super(path);
    }

    public String getName() {
        return "Simple";
    }
    
    public void addResponseHeader(final Header header) {
        getResponseHeaderGroup().addHeader(header);
    }

    public String generateRequestLine(
        final HttpConnection connection ,final HttpVersion version) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection may not be null");
        }
        if (version == null) {
            throw new IllegalArgumentException("HTTP version may not be null");
        }
        return HttpMethodBase.generateRequestLine(connection, 
          this.getName(), this.getPath(), this.getQueryString(), version.toString());
    }
    
}
