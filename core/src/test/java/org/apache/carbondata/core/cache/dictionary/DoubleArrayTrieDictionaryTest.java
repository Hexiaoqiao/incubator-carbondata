/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.carbondata.core.cache.dictionary;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class DoubleArrayTrieDictionaryTest {

  @Test
  public void testEmptyValue() {
    List<String> vals = new ArrayList<String>();
    vals.add("");
    vals.add("he");
    vals.add("hel");
    vals.add("her");
    vals.add("hello");

    testDoubleArrayTrieDictionary(vals, null);
  }

  @Test
  public void testSimpleTrie() {
      List<String> vals = new ArrayList<String>();
      vals.add("he");
      vals.add("hel");
      vals.add("her");
      vals.add("hello");

      List<String> notVals = new ArrayList<String>();
      notVals.add("");
      notVals.add("hi");
      notVals.add("h");
      notVals.add("helloworld");

      testDoubleArrayTrieDictionary(vals, notVals);
  }

  @Test
  public void testLongKey() {
    StringBuffer sb = new StringBuffer();
    String testKey = "hello";
    for(int i = 0; i < 10000; i++) {
      sb.append(testKey);
    }
    List<String> vals = new ArrayList<String>();
    vals.add(sb.toString());
    testDoubleArrayTrieDictionary(vals, null);
  }

  private static void testDoubleArrayTrieDictionary(List<String> items, List<String> notItems) {
      int baseId = DoubleArrayTrieDictionary.ENCODE_BASE_VALUE;
    DoubleArrayTrieDictionary dat = new DoubleArrayTrieDictionary();
    for(int i = 0; i < items.size(); i++) {
      dat.insert(items.get(i));
    }
    for(int i = 0; i < items.size(); i++) {
      int val = baseId + i;
      assertEquals(dat.getValue(items.get(i)), val);
    }
    if(null != notItems) {
      for (String noItem : notItems) {
        int val = dat.getValue(noItem);
        assertEquals(val, -1);
      }
    }
    int id = dat.getValue(null);
    assertEquals(id, -1);
  }
}