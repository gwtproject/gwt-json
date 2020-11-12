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

import static java.util.Objects.nonNull;

import elemental2.core.JsArray;
import elemental2.core.JsObject;
import jsinterop.base.Js;

class JSONValueFactory {

  static JSONValue create(Object value) {
    String type = Js.typeof(value);
    switch (type) {
      case "boolean":
        return JSONBoolean.getInstance((Boolean) value);
      case "number":
        return new JSONNumber((Double) value);
      case "string":
        return new JSONString((String) value);
      case "object":
        return createObject(value);
      case "function":
        return createObject(value);
      case "undefined":
        return null;
    }
    throw new JSONException(
        "Unexpected typeof result '" + type + "'; please report this bug to the GWT team");
  }

  private static JSONValue createObject(Object o) {
    if (Js.isFalsy(o)) {
      return JSONNull.getInstance();
    }

    JsObject jsObject = Js.cast(o);
    Object v = nonNull(jsObject.valueOf()) ? jsObject.valueOf() : jsObject;
    if (!Js.isTripleEqual(v, jsObject)) {
      return create(v);
    } else if (JsArray.isArray(o)) {
      return new JSONArray((JsArray<Object>) Js.cast(o));
    } else {
      return new JSONObject((JsObject) Js.cast(o));
    }
  }
}
