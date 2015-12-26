/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.gs.plistconv.gen;

import com.gs.plist4j.primitives.PlistValue;
import com.gs.plist4j.xml.XmlPlistOutput;
import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.LogExceptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kirill Chernyavskiy
 */
@Immutable
public final class GenPlXml implements GenStream {

    @Override
    @LogExceptions
    public InputStream act(PlistValue source) throws IOException {
        //TODO: why PipedOutputStream and PipedInputStream are not working??!
//        PipedInputStream input = new PipedInputStream();
//        try (PipedOutputStream output = new PipedOutputStream(input)) {
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        new XmlPlistOutput(tmp).write(source);
        return new ByteArrayInputStream(tmp.toByteArray());
//        }
//        return input;
    }
}
