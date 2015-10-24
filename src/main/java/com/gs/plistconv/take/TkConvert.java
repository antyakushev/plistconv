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

import com.gs.plistconv.err.ErrMethodNotAllowed;
import com.gs.plistconv.err.ErrUnsupportedType;
import com.gs.plistconv.fb.FbMethodNotAllowed;
import com.gs.plistconv.fork.FkInOut;
import com.gs.plistconv.gen.GenBinary;
import com.gs.plistconv.gen.GenPlXml;
import com.gs.plistconv.proc.PcBinary;
import com.gs.plistconv.proc.PcPlXml;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.TkFork;
import org.takes.facets.fork.TkMethods;
import org.takes.misc.Opt;
import org.takes.tk.TkWrap;

/**
 * @author Kirill Chernyavskiy
 */
public final class TkConvert extends TkWrap {

    public TkConvert() {
        super(
            new TkFallback(
                new TkMethods(
                    new TkFork(
                        new FkInOut(
                            "^bin$",
                            "^xml$",
                            new TkPipe(
                                new PcBinary(),
                                new GenPlXml()
                            )
                        ),
                        new FkInOut(
                            "^xml$",
                            "^bin$",
                            new TkPipe(
                                new PcPlXml(),
                                new GenBinary()
                            )
                        )
                    ),
                    "POST"
                ),
                new FbChain(
                    new FbMethodNotAllowed(
                        req -> new Opt.Single<>(new ErrMethodNotAllowed()),
                        "POST"
                    ),
                    unsupportedType -> new Opt.Single<>(
                        new ErrUnsupportedType()
                    )
                )
            )
        );
    }
}
