/*
 * Copyright © 2019 The GWT Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gwtproject.json.client;

import elemental2.core.Global;
import elemental2.core.JsArray;
import elemental2.core.JsObject;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import org.gwtproject.core.client.JavaScriptObject;

/** Represents a JSON object. A JSON object consists of a set of properties. */
public class JSONObject extends JSONValue {

  private final JsPropertyMap<Object> propertyMap;

  private final JsObject jsObject;

  public JSONObject() {
    this(new JsObject());
  }

  /**
   * Creates a new JSONObject from the supplied JavaScript value.
   *
   * <p>use {@link #JSONObject(JsObject)} instead
   */
  @Deprecated
  public JSONObject(JavaScriptObject jsValue) {
    this((JsObject) Js.cast(jsValue));
  }

  /** Creates a new JSONObject from the supplied JavaScript value. */
  public JSONObject(JsObject jsValue) {
    jsObject = jsValue;
    propertyMap = Js.asPropertyMap(jsObject);
  }

  /** Returns the underlying JavaScript object that this object wraps. */
  public JavaScriptObject getJavaScriptObject() {
    return Js.cast(jsObject);
  }

  /**
   * Tests whether or not this JSONObject contains the specified property.
   *
   * @param key the property to search for
   * @return <code>true</code> if the JSONObject contains the specified property
   */
  public boolean containsKey(String key) {
    return propertyMap.has(key);
  }

  /**
   * Returns <code>true</code> if <code>other</code> is a {@link JSONObject} wrapping the same
   * underlying object.
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof JSONObject)) {
      return false;
    }

    return jsObject.equals(((JSONObject) other).jsObject);
  }

  /**
   * Gets the JSONValue associated with the specified property.
   *
   * @param key the property to access
   * @return the value of the specified property, or <code>null</code> if the property does not
   *     exist
   * @throws NullPointerException if key is <code>null</code>
   */
  public JSONValue get(String key) {
    if (key == null) {
      throw new NullPointerException();
    }
    return get0(key);
  }

  @Override
  public int hashCode() {
    return jsObject.hashCode();
  }

  /** Returns <code>this</code>, as this is a JSONObject. */
  @Override
  public JSONObject isObject() {
    return this;
  }

  /** Returns the set of properties defined on this JSONObject. The returned set is immutable. */
  public Set<String> keySet() {
    final String[] keys = computeKeys();
    return new AbstractSet<String>() {
      @Override
      public boolean contains(Object o) {
        return (o instanceof String) && containsKey((String) o);
      }

      @Override
      public Iterator<String> iterator() {
        return Arrays.asList(keys).iterator();
      }

      @Override
      public int size() {
        return keys.length;
      }
    };
  }

  /**
   * Assign the specified property to the specified value in this JSONObject. If the property
   * already has an associated value, it is overwritten.
   *
   * @param key the property to assign
   * @param jsonValue the value to assign
   * @return the previous value of the property, or <code>null</code> if the property did not exist
   * @throws NullPointerException if key is <code>null</code>
   */
  public JSONValue put(String key, JSONValue jsonValue) {
    if (key == null) {
      throw new NullPointerException();
    }
    JSONValue previous = get(key);
    put0(key, jsonValue);
    return previous;
  }

  /** Determines the number of properties on this object. */
  public int size() {
    // Must always recheck due to foreign changes. :(
    return computeSize();
  }

  /**
   * Converts a JSONObject into a JSON representation that can be used to communicate with a JSON
   * service.
   *
   * @return a JSON string representation of this JSONObject instance
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{");
    boolean first = true;
    String[] keys = computeKeys();
    for (String key : keys) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(Global.JSON.stringify(key));
      sb.append(":");
      sb.append(get(key));
    }
    sb.append("}");
    return sb.toString();
  }

  @Override
  Object getUnwrapper() {
    return jsObject;
  }

  private String[] computeKeys() {
    JsArray<String> keys = JsObject.keys(jsObject);
    String[] computedKeys = new String[0];
    for (int i = 0; i < keys.length; i++) {
      String key = keys.getAt(i);
      if (jsObject.hasOwnProperty(key)) {
        computedKeys[computedKeys.length] = key;
      }
    }
    return computedKeys;
  }

  private int computeSize() {
    return computeKeys().length;
  }

  private JSONValue get0(String key) {
    if (propertyMap.has(key)) return JSONValueFactory.create(propertyMap.get(key));
    return null;
  }

  private void put0(String key, JSONValue value) {
    if (Js.isTruthy(value)) {
      propertyMap.set(key, value.getUnwrapper());
    } else {
      propertyMap.delete(key);
    }
  }
}
