// TODO: license
package org.reaktivity.nukleus.sse.internal.types.control;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.StringFW;

public final class SseRouteExFW extends Flyweight {
  public static final int FIELD_OFFSET_PATH_INFO = 0;

  private final StringFW pathInfoRO = new StringFW();

  public StringFW pathInfo() {
    return pathInfoRO;
  }

  @Override
  public SseRouteExFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    pathInfoRO.wrap(buffer, offset + FIELD_OFFSET_PATH_INFO, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return pathInfoRO.limit();
  }

  @Override
  public String toString() {
    return String.format("SSE_ROUTE_EX [pathInfo=%s]", pathInfoRO.asString());
  }

  public static final class Builder extends Flyweight.Builder<SseRouteExFW> {
    private static final int INDEX_PATH_INFO = 0;

    private static final int FIELD_COUNT = 1;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "pathInfo"
    };

    private final StringFW.Builder pathInfoRW = new StringFW.Builder();

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new SseRouteExFW());
    }

    private StringFW.Builder pathInfo() {
      checkFieldNotSet(INDEX_PATH_INFO);
      checkFieldsSet(0, INDEX_PATH_INFO);
      return pathInfoRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder pathInfo(String value) {
      StringFW.Builder pathInfoRW = pathInfo();
      pathInfoRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_PATH_INFO);
      limit(pathInfoRW.build().limit());
      return this;
    }

    public Builder pathInfo(StringFW value) {
      StringFW.Builder pathInfoRW = pathInfo();
      pathInfoRW.set(value);
      fieldsSet.set(INDEX_PATH_INFO);
      limit(pathInfoRW.build().limit());
      return this;
    }

    public Builder pathInfo(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder pathInfoRW = pathInfo();
      pathInfoRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_PATH_INFO);
      limit(pathInfoRW.build().limit());
      return this;
    }

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      fieldsSet.clear();
      super.wrap(buffer, offset, maxLimit);
      limit(offset);
      return this;
    }

    @Override
    public SseRouteExFW build() {
      checkFieldsSet(0, FIELD_COUNT);
      fieldsSet.clear();
      return super.build();
    }

    private void checkFieldNotSet(int index) {
      if (fieldsSet.get(index)) {
        throw new IllegalStateException(String.format("Field \"%s\" has already been set", FIELD_NAMES[index]));
      }
    }

    private void checkFieldsSet(int fromIndex, int toIndex) {
      int fieldNotSet = fromIndex - 1;
      do {
        fieldNotSet = fieldsSet.nextClearBit(fieldNotSet + 1);
      } while (fieldNotSet < toIndex && FIELDS_WITH_DEFAULTS.get(fieldNotSet));
      if (fieldNotSet < toIndex) {
        throw new IllegalStateException(String.format("Required field \"%s\" is not set", FIELD_NAMES[fieldNotSet]));
      }
    }
  }
}
