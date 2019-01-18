/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.docker.client.messages.swarm;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.List;


@AutoValue
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public abstract class UpdateConfig {
  public enum UpdateOrder {
    START_FIRST("start-first"),
    STOP_FIRST("stop-first");

    private final String value;

    UpdateOrder(final String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }
  }


  @Nullable
  @JsonProperty("Parallelism")
  public abstract Long parallelism();

  @Nullable
  @JsonProperty("Delay")
  public abstract Long delay();

  @Nullable
  @JsonProperty("FailureAction")
  public abstract String failureAction();

  @Nullable
  @JsonProperty("UpdateOrder")
  public abstract UpdateOrder updateOrder();

  abstract UpdateConfig.Builder toBuilder();

  @JsonCreator
  public static UpdateConfig create(
      @JsonProperty("Parallelism") final Long parallelism,
      @JsonProperty("Delay") final Long delay,
      @JsonProperty("FailureAction") final String failureAction) {
    return new AutoValue_UpdateConfig(parallelism, delay, failureAction);
  }

  public UpdateConfig startFirst() {
    return toBuilder().updateOrder(UpdateOrder.START_FIRST).build();
  }

  public UpdateConfig stopFirst() {
    return toBuilder().updateOrder(UpdateOrder.STOP_FIRST).build();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder parallelism(Long parallelism);

    public abstract Builder delay(Long delay);

    public abstract Builder failureAction(String failureAction);

    public abstract Builder updateOrder(UpdateOrder updateOrder);

    public abstract UpdateConfig build();

  }

  public static UpdateConfig.Builder builder() {
    return new AutoValue_UpdateConfig.Builder();
  }

  @JsonCreator
  static UpdateConfig create(
          @JsonProperty("Parallelism") final Long parallelism,
          @JsonProperty("Delay") final Long delay,
          @JsonProperty("FailureAction") final String failureAction,
          @JsonProperty("UdpateOrder") final UpdateOrder updateOrder) {
    final Builder builder = builder()
            .parallelism(parallelism)
            .delay(delay)
            .failureAction(failureAction)
            .updateOrder(updateOrder);

    return builder.build();
  }
}
