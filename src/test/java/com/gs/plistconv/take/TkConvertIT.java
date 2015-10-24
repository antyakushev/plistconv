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
package com.gs.plistconv.take;

import com.gs.plist4j.binary.BinaryPlistFile;
import com.gs.plist4j.primitives.PlistPrimitive;
import com.gs.plist4j.primitives.PlistValue;
import com.gs.plist4j.xml.XmlPlistFile;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.RestResponse;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.takes.http.FtRemote;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * @author Kirill Chernyavskiy
 */
public final class TkConvertIT {
    private static class BinaryMatcher extends TypeSafeMatcher<byte[]> {

        private final PlistValue value;

        private BinaryMatcher(PlistValue value) {
            this.value = value;
        }

        @Override
        protected boolean matchesSafely(byte[] item) {
            File tmp;
            try {
                tmp = File.createTempFile("TkMainIT#XmlMatcher-", ".tmp");
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file");
            }
            try (OutputStream os = new FileOutputStream(tmp)) {
                IOUtils.write(item, os);
                PlistValue read = new BinaryPlistFile(tmp).read();
                return value.equals(read);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                tmp.delete();
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("binary plist match value");
        }
    }

    private static class XmlMatcher extends TypeSafeMatcher<byte[]> {

        private final PlistValue value;

        private XmlMatcher(PlistValue value) {
            this.value = value;
        }

        @Override
        protected boolean matchesSafely(byte[] item) {
            File tmp;
            try {
                tmp = File.createTempFile("TkMainIT#XmlMatcher-", ".tmp");
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file");
            }
            try (OutputStream os = new FileOutputStream(tmp)) {
                IOUtils.write(item, os);
                return value.equals(new XmlPlistFile(tmp).read());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                tmp.delete();
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("xml match plist value");
        }
    }

    private File tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("TkMainIT-", ".tmp");
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    //plist4j bug
   // @Test
    public void convertXmlToBinary() throws IOException {
        HashMap<String, PlistValue> targetWrap = new HashMap<>();
        targetWrap.put("key1", new PlistPrimitive(42));
        targetWrap.put("key2", new PlistPrimitive(true));
        targetWrap.put("key3", new PlistPrimitive("qwe"));
        PlistPrimitive target = new PlistPrimitive(targetWrap);
        new XmlPlistFile(tempFile).write(target);
        byte[] data;
        try (InputStream is = new FileInputStream(tempFile)) {
            data = IOUtils.toByteArray(is);
        }
        new FtRemote(new TkConvert()).exec(
            home -> new JdkRequest(home)
                .method("POST")
                .uri()
                .path("/convert")
                .queryParam("inp", "xml")
                .queryParam("out", "bin")
                .back()
                .body()
                .set(data)
                .back()
                .fetch()
                .as(RestResponse.class)
                .assertBinary(new BinaryMatcher(target))
                .assertStatus(HttpURLConnection.HTTP_OK)
        );
    }

    @Test
    public void convertBinaryToXml() throws IOException {
        HashMap<String, PlistValue> targetWrap = new HashMap<>();
        targetWrap.put("key2", new PlistPrimitive(true));
        targetWrap.put("key3", new PlistPrimitive("qwe"));
        PlistPrimitive target = new PlistPrimitive(targetWrap);
        new BinaryPlistFile(tempFile).write(target);
        byte[] data;
        try (InputStream is = new FileInputStream(tempFile)) {
            data = IOUtils.toByteArray(is);
        }
        new FtRemote(new TkConvert()).exec(
            home -> new JdkRequest(home)
                .method("POST")
                .uri()
                .path("/convert")
                .queryParam("inp", "bin")
                .queryParam("out", "xml")
                .back()
                .body()
                .set(data)
                .back()
                .fetch()
                .as(RestResponse.class)
                .assertBinary(new XmlMatcher(target))
                .assertStatus(HttpURLConnection.HTTP_OK)
        );
    }

    @Test
    public void convertWithInvalidInputOrOutputTypeReturns415Error() throws IOException {
        new FtRemote(new TkConvert()).exec(
            home -> new JdkRequest(home)
                .method("POST")
                .uri()
                .path("/convert")
                .queryParam("inp", "THIS_IS_UNSUPPORTED")
                .queryParam("out", "xml")
                .back()
                .fetch()
                .as(RestResponse.class)
                .assertStatus(HttpURLConnection.HTTP_UNSUPPORTED_TYPE)
        );
        new FtRemote(new TkConvert()).exec(
            home -> new JdkRequest(home)
                .method("POST")
                .uri()
                .path("/convert")
                .queryParam("inp", "xml")
                .queryParam("out", "THIS_IS_UNSUPPORTED")
                .back()
                .fetch()
                .as(RestResponse.class)
                .assertStatus(HttpURLConnection.HTTP_UNSUPPORTED_TYPE)
        );
    }

    @Test
    public void onlyPostMethodAllowed() throws IOException {
        new FtRemote(new TkConvert()).exec(
            home -> new JdkRequest(home)
            .method("GET")
            .uri().path("/convert").back()
            .fetch()
            .as(RestResponse.class)
            .assertStatus(HttpURLConnection.HTTP_BAD_METHOD)
        );
    }
}
