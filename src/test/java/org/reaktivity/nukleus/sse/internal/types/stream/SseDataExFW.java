// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.StringFW;

public final class SseDataExFW extends Flyweight {
  public static final int FIELD_OFFSET_TIMESTAMP = 0;

  private static final int FIELD_SIZE_TIMESTAMP = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_ID = FIELD_OFFSET_TIMESTAMP + FIELD_SIZE_TIMESTAMP;

  public static final int FIELD_OFFSET_TYPE = 0;

  private final StringFW idRO = new StringFW();

  private final StringFW typeRO = new StringFW();

  public long timestamp() {
    return buffer().getLong(offset() + FIELD_OFFSET_TIMESTAMP);
  }

  public StringFW id() {
    return idRO;
  }

  public StringFW type() {
    return typeRO;
  }

  @Override
  public SseDataExFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    idRO.wrap(buffer, offset + FIELD_OFFSET_ID, maxLimit);
    typeRO.wrap(buffer, idRO.limit() + FIELD_OFFSET_TYPE, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return typeRO.limit();
  }

  @Override
  public String toString() {
    return String.format("SSE_DATA_EX [timestamp=%d, id=%s, type=%s]", timestamp(), idRO.asString(), typeRO.asString());
  }

  public static final class Builder extends Flyweight.Builder<SseDataExFW> {
    private static final int INDEX_TIMESTAMP = 0;

    private static final int INDEX_ID = 1;

    private static final int INDEX_TYPE = 2;

    private static final int FIELD_COUNT = 3;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "timestamp",
      "id",
      "type"
    };

    private final StringFW.Builder idRW = new StringFW.Builder();

    private final StringFW.Builder typeRW = new StringFW.Builder();

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new SseDataExFW());
    }

    public Builder timestamp(long value) {
      checkFieldNotSet(INDEX_TIMESTAMP);
      checkFieldsSet(0, INDEX_TIMESTAMP);
      int newLimit = limit() + FIELD_SIZE_TIMESTAMP;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_TIMESTAMP);
      limit(newLimit);
      return this;
    }

    private StringFW.Builder id() {
      checkFieldNotSet(INDEX_ID);
      checkFieldsSet(0, INDEX_ID);
      return idRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder id(String value) {
      StringFW.Builder idRW = id();
      idRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_ID);
      limit(idRW.build().limit());
      return this;
    }

    public Builder id(StringFW value) {
      StringFW.Builder idRW = id();
      idRW.set(value);
      fieldsSet.set(INDEX_ID);
      limit(idRW.build().limit());
      return this;
    }

    public Builder id(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder idRW = id();
      idRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_ID);
      limit(idRW.build().limit());
      return this;
    }

    private StringFW.Builder type() {
      checkFieldNotSet(INDEX_TYPE);
      checkFieldsSet(0, INDEX_TYPE);
      return typeRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder type(String value) {
      StringFW.Builder typeRW = type();
      typeRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_TYPE);
      limit(typeRW.build().limit());
      return this;
    }

    public Builder type(StringFW value) {
      StringFW.Builder typeRW = type();
      typeRW.set(value);
      fieldsSet.set(INDEX_TYPE);
      limit(typeRW.build().limit());
      return this;
    }

    public Builder type(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder typeRW = type();
      typeRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_TYPE);
      limit(typeRW.build().limit());
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
    public SseDataExFW build() {
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
