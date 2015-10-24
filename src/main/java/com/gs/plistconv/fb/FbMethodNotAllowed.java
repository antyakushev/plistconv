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
package com.gs.plistconv.fb;

import com.jcabi.aspects.Immutable;
import org.takes.facets.fallback.Fallback;
import org.takes.facets.fallback.FbWrap;
import org.takes.misc.Opt;
import org.takes.rq.RqMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Kirill Chernyavskiy
 */
@Immutable
public final class FbMethodNotAllowed extends FbWrap {

    public FbMethodNotAllowed(final Fallback fb, final String... allowed) {
        this(fb, Collections.unmodifiableCollection(Arrays.asList(allowed)));
    }

    public FbMethodNotAllowed(final Fallback fb, final Collection<String> allowed) {
        super(
            req -> {
                if (!allowed.contains(new RqMethod.Base(req).method())) {
                    return fb.route(req);
                } else {
                    return new Opt.Empty<>();
                }
            }
        );
    }
}
