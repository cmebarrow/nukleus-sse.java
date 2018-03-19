// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.StringFW;

public final class SseBeginExFW extends Flyweight {
  public static final int FIELD_OFFSET_PATH_INFO = 0;

  public static final int FIELD_OFFSET_LAST_EVENT_ID = 0;

  private final StringFW pathInfoRO = new StringFW();

  private final StringFW lastEventIdRO = new StringFW();

  public StringFW pathInfo() {
    return pathInfoRO;
  }

  public StringFW lastEventId() {
    return lastEventIdRO;
  }

  @Override
  public SseBeginExFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    pathInfoRO.wrap(buffer, offset + FIELD_OFFSET_PATH_INFO, maxLimit);
    lastEventIdRO.wrap(buffer, pathInfoRO.limit() + FIELD_OFFSET_LAST_EVENT_ID, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return lastEventIdRO.limit();
  }

  @Override
  public String toString() {
    return String.format("SSE_BEGIN_EX [pathInfo=%s, lastEventId=%s]", pathInfoRO.asString(), lastEventIdRO.asString());
  }

  public static final class Builder extends Flyweight.Builder<SseBeginExFW> {
    private static final int INDEX_PATH_INFO = 0;

    private static final int INDEX_LAST_EVENT_ID = 1;

    private static final int FIELD_COUNT = 2;

    private int lastFieldSet = -1;

    private final StringFW.Builder pathInfoRW = new StringFW.Builder();

    private final StringFW.Builder lastEventIdRW = new StringFW.Builder();


    public Builder() {
      super(new SseBeginExFW());
    }

    private StringFW.Builder pathInfo() {
      assert lastFieldSet == INDEX_PATH_INFO - 1;
      return pathInfoRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder pathInfo(String value) {
      StringFW.Builder pathInfoRW = pathInfo();
      pathInfoRW.set(value, StandardCharsets.UTF_8);
      lastFieldSet = INDEX_PATH_INFO;
      limit(pathInfoRW.build().limit());
      return this;
    }

    public Builder pathInfo(StringFW value) {
      StringFW.Builder pathInfoRW = pathInfo();
      pathInfoRW.set(value);
      lastFieldSet = INDEX_PATH_INFO;
      limit(pathInfoRW.build().limit());
      return this;
    }

    public Builder pathInfo(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder pathInfoRW = pathInfo();
      pathInfoRW.set(buffer, offset, length);
      lastFieldSet = INDEX_PATH_INFO;
      limit(pathInfoRW.build().limit());
      return this;
    }

    private StringFW.Builder lastEventId() {
      assert lastFieldSet == INDEX_LAST_EVENT_ID - 1;
      return lastEventIdRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder lastEventId(String value) {
      StringFW.Builder lastEventIdRW = lastEventId();
      lastEventIdRW.set(value, StandardCharsets.UTF_8);
      lastFieldSet = INDEX_LAST_EVENT_ID;
      limit(lastEventIdRW.build().limit());
      return this;
    }

    public Builder lastEventId(StringFW value) {
      StringFW.Builder lastEventIdRW = lastEventId();
      lastEventIdRW.set(value);
      lastFieldSet = INDEX_LAST_EVENT_ID;
      limit(lastEventIdRW.build().limit());
      return this;
    }

    public Builder lastEventId(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder lastEventIdRW = lastEventId();
      lastEventIdRW.set(buffer, offset, length);
      lastFieldSet = INDEX_LAST_EVENT_ID;
      limit(lastEventIdRW.build().limit());
      return this;
    }

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      lastFieldSet = -1;
      super.wrap(buffer, offset, maxLimit);
      limit(offset);
      return this;
    }

    @Override
    public SseBeginExFW build() {
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
