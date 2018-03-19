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

    private int lastFieldSet = -1;

    private final StringFW.Builder idRW = new StringFW.Builder();

    private final StringFW.Builder typeRW = new StringFW.Builder();


    public Builder() {
      super(new SseDataExFW());
    }

    public Builder timestamp(long value) {
      assert lastFieldSet == INDEX_TIMESTAMP - 1;
      int newLimit = limit() + FIELD_SIZE_TIMESTAMP;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_TIMESTAMP;
      limit(newLimit);
      return this;
    }

    private StringFW.Builder id() {
      assert lastFieldSet == INDEX_ID - 1;
      return idRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder id(String value) {
      StringFW.Builder idRW = id();
      idRW.set(value, StandardCharsets.UTF_8);
      lastFieldSet = INDEX_ID;
      limit(idRW.build().limit());
      return this;
    }

    public Builder id(StringFW value) {
      StringFW.Builder idRW = id();
      idRW.set(value);
      lastFieldSet = INDEX_ID;
      limit(idRW.build().limit());
      return this;
    }

    public Builder id(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder idRW = id();
      idRW.set(buffer, offset, length);
      lastFieldSet = INDEX_ID;
      limit(idRW.build().limit());
      return this;
    }

    private StringFW.Builder type() {
      assert lastFieldSet == INDEX_TYPE - 1;
      return typeRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder type(String value) {
      StringFW.Builder typeRW = type();
      typeRW.set(value, StandardCharsets.UTF_8);
      lastFieldSet = INDEX_TYPE;
      limit(typeRW.build().limit());
      return this;
    }

    public Builder type(StringFW value) {
      StringFW.Builder typeRW = type();
      typeRW.set(value);
      lastFieldSet = INDEX_TYPE;
      limit(typeRW.build().limit());
      return this;
    }

    public Builder type(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder typeRW = type();
      typeRW.set(buffer, offset, length);
      lastFieldSet = INDEX_TYPE;
      limit(typeRW.build().limit());
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
    public SseDataExFW build() {
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
