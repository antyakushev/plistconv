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
package com.gs.plistconv.proc;

import com.gs.plist4j.PlistException;
import com.gs.plist4j.binary.BinaryPlistFile;
import com.gs.plist4j.primitives.PlistValue;
import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.LogExceptions;
import com.jcabi.log.Logger;
import org.takes.Request;
import org.takes.rq.RqPrint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kirill Chernyavskiy
 */
@Immutable
public final class PcBinary implements PcRequest {

    @Override
    @LogExceptions
    public PlistValue act(Request request) throws IOException {
        File tmp = File.createTempFile("pcbinary-", ".plist");
        try {
            try (OutputStream os = new FileOutputStream(tmp)) {
                new RqPrint(request).printBody(os);
            }
            return new BinaryPlistFile(tmp).read();
        } finally {
            if (!tmp.delete()) {
                Logger.warn(this, "Failed to delete temporary file");
            }
        }
    }
}
