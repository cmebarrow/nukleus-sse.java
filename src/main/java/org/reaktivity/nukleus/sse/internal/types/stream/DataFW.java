// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.OctetsFW;

public final class DataFW extends Flyweight {
  public static final int FIELD_OFFSET_STREAM_ID = 0;

  private static final int FIELD_SIZE_STREAM_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TIMESTAMP = FIELD_OFFSET_STREAM_ID + FIELD_SIZE_STREAM_ID;

  private static final int FIELD_SIZE_TIMESTAMP = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TRACE = FIELD_OFFSET_TIMESTAMP + FIELD_SIZE_TIMESTAMP;

  private static final int FIELD_SIZE_TRACE = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_AUTHORIZATION = FIELD_OFFSET_TRACE + FIELD_SIZE_TRACE;

  private static final int FIELD_SIZE_AUTHORIZATION = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_FLAGS = FIELD_OFFSET_AUTHORIZATION + FIELD_SIZE_AUTHORIZATION;

  private static final int FIELD_SIZE_FLAGS = BitUtil.SIZE_OF_BYTE;

  public static final int FIELD_OFFSET_GROUP_ID = FIELD_OFFSET_FLAGS + FIELD_SIZE_FLAGS;

  private static final int FIELD_SIZE_GROUP_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_PADDING = FIELD_OFFSET_GROUP_ID + FIELD_SIZE_GROUP_ID;

  private static final int FIELD_SIZE_PADDING = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_LENGTH = FIELD_OFFSET_PADDING + FIELD_SIZE_PADDING;

  private static final int FIELD_SIZE_LENGTH = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_PAYLOAD = FIELD_OFFSET_LENGTH + FIELD_SIZE_LENGTH;

  public static final int FIELD_OFFSET_EXTENSION = 0;

  public static final int TYPE_ID = 0x00000002;

  private OctetsFW payloadRO = new OctetsFW();

  private final OctetsFW extensionRO = new OctetsFW();

  public long streamId() {
    return buffer().getLong(offset() + FIELD_OFFSET_STREAM_ID);
  }

  public long timestamp() {
    return buffer().getLong(offset() + FIELD_OFFSET_TIMESTAMP);
  }

  public long trace() {
    return buffer().getLong(offset() + FIELD_OFFSET_TRACE);
  }

  public long authorization() {
    return buffer().getLong(offset() + FIELD_OFFSET_AUTHORIZATION);
  }

  public int flags() {
    return buffer().getByte(offset() + FIELD_OFFSET_FLAGS) & 0xFF;
  }

  public long groupId() {
    return buffer().getLong(offset() + FIELD_OFFSET_GROUP_ID);
  }

  public int padding() {
    return buffer().getInt(offset() + FIELD_OFFSET_PADDING);
  }

  public int length() {
    return buffer().getInt(offset() + FIELD_OFFSET_LENGTH);
  }

  public OctetsFW payload() {
    return length() == -1 ? null: payloadRO;
  }

  public OctetsFW extension() {
    return extensionRO;
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public DataFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    payloadRO.wrap(buffer, offset + FIELD_OFFSET_PAYLOAD, offset + FIELD_OFFSET_PAYLOAD + (length() == -1 ? 0 : (int) length()));
    extensionRO.wrap(buffer, payloadRO.limit() + FIELD_OFFSET_EXTENSION, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return extensionRO.limit();
  }

  @Override
  public String toString() {
    return String.format("DATA [streamId=%d, timestamp=%d, trace=%d, authorization=%d, flags=%d, groupId=%d, padding=%d, length=%d, payload=%s, extension=%s]", streamId(), timestamp(), trace(), authorization(), flags(), groupId(), padding(), length(), payload(), extension());
  }

  public static final class Builder extends Flyweight.Builder<DataFW> {
    private static final int INDEX_STREAM_ID = 0;

    private static final int INDEX_TIMESTAMP = 1;

    private static final long DEFAULT_TIMESTAMP = 0;

    private static final int INDEX_TRACE = 2;

    private static final long DEFAULT_TRACE = 0;

    private static final int INDEX_AUTHORIZATION = 3;

    private static final long DEFAULT_AUTHORIZATION = 0;

    private static final int INDEX_FLAGS = 4;

    private static final int DEFAULT_FLAGS = 1;

    private static final int INDEX_GROUP_ID = 5;

    private static final int INDEX_PADDING = 6;

    private static final int DEFAULT_LENGTH = 0;

    private static final int INDEX_PAYLOAD = 7;

    private static final int INDEX_EXTENSION = 8;

    private static final int FIELD_COUNT = 9;

    private int lastFieldSet = -1;

    private int dynamicOffsetLength;

    private final OctetsFW.Builder payloadRW = new OctetsFW.Builder();

    private final OctetsFW.Builder extensionRW = new OctetsFW.Builder();


    public Builder() {
      super(new DataFW());
    }

    public Builder streamId(long value) {
      assert lastFieldSet == INDEX_STREAM_ID - 1;
      int newLimit = limit() + FIELD_SIZE_STREAM_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_STREAM_ID;
      limit(newLimit);
      return this;
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

    public Builder trace(long value) {
      if (lastFieldSet < INDEX_TIMESTAMP) {
        timestamp(DEFAULT_TIMESTAMP);
      }
      assert lastFieldSet == INDEX_TRACE - 1;
      int newLimit = limit() + FIELD_SIZE_TRACE;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_TRACE;
      limit(newLimit);
      return this;
    }

    public Builder authorization(long value) {
      if (lastFieldSet < INDEX_TRACE) {
        trace(DEFAULT_TRACE);
      }
      assert lastFieldSet == INDEX_AUTHORIZATION - 1;
      int newLimit = limit() + FIELD_SIZE_AUTHORIZATION;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_AUTHORIZATION;
      limit(newLimit);
      return this;
    }

    public Builder flags(int value) {
      if (value < 0) {
        throw new IllegalArgumentException(String.format("Value %d too low for field \"flags\"", value));
      }
      if (value > 0XFF) {
        throw new IllegalArgumentException(String.format("Value %d too high for field \"flags\"", value));
      }
      if (lastFieldSet < INDEX_AUTHORIZATION) {
        authorization(DEFAULT_AUTHORIZATION);
      }
      assert lastFieldSet == INDEX_FLAGS - 1;
      int newLimit = limit() + FIELD_SIZE_FLAGS;
      checkLimit(newLimit, maxLimit());
      buffer().putByte(limit(), (byte)(value & 0xFF));
      lastFieldSet = INDEX_FLAGS;
      limit(newLimit);
      return this;
    }

    public Builder groupId(long value) {
      if (lastFieldSet < INDEX_FLAGS) {
        flags(DEFAULT_FLAGS);
      }
      assert lastFieldSet == INDEX_GROUP_ID - 1;
      int newLimit = limit() + FIELD_SIZE_GROUP_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_GROUP_ID;
      limit(newLimit);
      return this;
    }

    public Builder padding(int value) {
      assert lastFieldSet == INDEX_PADDING - 1;
      int newLimit = limit() + FIELD_SIZE_PADDING;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), value);
      lastFieldSet = INDEX_PADDING;
      limit(newLimit);
      return this;
    }

    private Builder length(int value) {
      int newLimit = limit() + FIELD_SIZE_LENGTH;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), value);
      dynamicOffsetLength = limit();
      limit(newLimit);
      return this;
    }

    private OctetsFW.Builder payload() {
      assert lastFieldSet == INDEX_PAYLOAD - 1;
      length(DEFAULT_LENGTH);
      return payloadRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder payload(OctetsFW value) {
      int size$;
      int newLimit;
      OctetsFW.Builder payloadRW = payload();
      if (value == null) {
        size$ = -1;
        newLimit = limit();
      } else {
        payloadRW.set(value);
        newLimit = payloadRW.build().limit();
        size$ = newLimit - limit();
      }
      limit(dynamicOffsetLength);
      length(size$);
      limit(newLimit);
      lastFieldSet = INDEX_PAYLOAD;
      return this;
    }

    public Builder payload(Consumer<OctetsFW.Builder> mutator) {
      OctetsFW.Builder payloadRW = payload();
      mutator.accept(payloadRW);
      int newLimit = payloadRW.build().limit();
      int size$ = newLimit - limit();
      limit(dynamicOffsetLength);
      length(size$);
      limit(newLimit);
      lastFieldSet = INDEX_PAYLOAD;
      return this;
    }

    public Builder payload(DirectBuffer buffer, int offset, int length) {
      OctetsFW.Builder payloadRW = payload();
      payloadRW.set(buffer, offset, length);
      int newLimit = payloadRW.build().limit();
      int size$ = newLimit - limit();
      limit(dynamicOffsetLength);
      length(size$);
      limit(newLimit);
      lastFieldSet = INDEX_PAYLOAD;
      return this;
    }

    private OctetsFW.Builder extension() {
      if (lastFieldSet < INDEX_PAYLOAD) {
        payload(b -> { });
        int limit = limit();
        limit(dynamicOffsetLength);
        length(-1);
        limit(limit);
      }
      assert lastFieldSet == INDEX_EXTENSION - 1;
      return extensionRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder extension(OctetsFW value) {
      OctetsFW.Builder extensionRW = extension();
      extensionRW.set(value);
      limit(extensionRW.build().limit());
      lastFieldSet = INDEX_EXTENSION;
      return this;
    }

    public Builder extension(Consumer<OctetsFW.Builder> mutator) {
      OctetsFW.Builder extensionRW = extension();
      mutator.accept(extensionRW);
      limit(extensionRW.build().limit());
      lastFieldSet = INDEX_EXTENSION;
      return this;
    }

    public Builder extension(DirectBuffer buffer, int offset, int length) {
      OctetsFW.Builder extensionRW = extension();
      extensionRW.set(buffer, offset, length);
      limit(extensionRW.build().limit());
      lastFieldSet = INDEX_EXTENSION;
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
    public DataFW build() {
      if (lastFieldSet < INDEX_EXTENSION) {
        extension(b -> { });
      }
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
