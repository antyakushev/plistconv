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
package com.gs.plistconv;

import com.gs.plistconv.fork.FkInp;
import com.gs.plistconv.fork.FkOut;
import com.gs.plistconv.gen.GenBinary;
import com.gs.plistconv.gen.GenPlXml;
import com.gs.plistconv.proc.PcBinary;
import com.gs.plistconv.proc.PcPlXml;
import com.gs.plistconv.take.TkConv;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCLI;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Kirill Chernyavskiy
 */
public final class App {

    public static void main(String[] args) throws IOException {

        new FtCLI(
            new TkFork(
                new FkRegex(
                    "/convert",
                    new TkFallback(
                        new TkFork(
                            new FkInp(
                                Pattern.compile("^bin$"),
                                new TkFork(
                                    new FkOut(
                                        Pattern.compile("^xml$"),
                                        new TkConv(
                                            new PcBinary(),
                                            new GenPlXml()
                                        )
                                    )
                                )
                            ),
                            new FkInp(
                                Pattern.compile("^xml$"),
                                new TkFork(
                                    new FkOut(
                                        Pattern.compile("^bin$"),
                                        new TkConv(
                                            new PcPlXml(),
                                            new GenBinary()
                                        )
                                    )
                                )
                            )
                        ),
                        new FbChain()
                    )
                )
            ),
            args
        ).start(Exit.NEVER);
    }
}
