/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.shiro.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;



@RunWith(Parameterized.class)
public class AntPathMatcherTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "/foo", "/foo", true },
                { "/foo", "/foo/bar", false },

                { "/foo/*", "/foo", false },
                { "/foo/*", "/foo/", true },
                { "/foo/*", "/foo.bar", false },
                { "/foo/*", "/foo/bar", true },
                { "/foo/*", "/foo/bar/", false },
                { "/foo/*", "/foo/bar/baz", false },
                { "/foo/*", "/foo/*", true },
                { "/foo/*", "/foo/*/bar", false },

                { "/foo/**", "/foo/**", true },
                { "/foo/**", "/foo/bar", true },
                { "/foo/**", "/foo.bar", false },
                { "/foo/**", "/foo/bar/baz", true },

                { null, "/foo/bar/baz", false },

                { "/foo/**", null, false },

                // https://issues.apache.org/jira/browse/SHIRO-582
                { "/foo", "/\\../\\../", false },
                { "/foo", "/\\../\\../foo", false } // TODO true
        });
    }

    @Parameterized.Parameter // first data value (0) is default
    public /* NOT private */ String pattern;

    @Parameterized.Parameter(1)
    public /* NOT private */ String path;

    @Parameterized.Parameter(2)
    public /* NOT private */ boolean fullMatch;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Test
    public void test() {
        assertEquals("r:" + pattern +  " p:" + path,
                fullMatch, antPathMatcher.match(pattern, path));
    }

}
