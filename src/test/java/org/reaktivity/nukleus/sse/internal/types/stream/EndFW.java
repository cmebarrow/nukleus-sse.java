// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.OctetsFW;

public final class EndFW extends Flyweight {
  public static final int FIELD_OFFSET_STREAM_ID = 0;

  private static final int FIELD_SIZE_STREAM_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TIMESTAMP = FIELD_OFFSET_STREAM_ID + FIELD_SIZE_STREAM_ID;

  private static final int FIELD_SIZE_TIMESTAMP = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TRACE = FIELD_OFFSET_TIMESTAMP + FIELD_SIZE_TIMESTAMP;

  private static final int FIELD_SIZE_TRACE = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_AUTHORIZATION = FIELD_OFFSET_TRACE + FIELD_SIZE_TRACE;

  private static final int FIELD_SIZE_AUTHORIZATION = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_EXTENSION = FIELD_OFFSET_AUTHORIZATION + FIELD_SIZE_AUTHORIZATION;

  public static final int TYPE_ID = 0x00000003;

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

  public OctetsFW extension() {
    return extensionRO;
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public EndFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    extensionRO.wrap(buffer, offset + FIELD_OFFSET_EXTENSION, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return extensionRO.limit();
  }

  @Override
  public String toString() {
    return String.format("END [streamId=%d, timestamp=%d, trace=%d, authorization=%d, extension=%s]", streamId(), timestamp(), trace(), authorization(), extension());
  }

  public static final class Builder extends Flyweight.Builder<EndFW> {
    private static final int INDEX_STREAM_ID = 0;

    private static final int INDEX_TIMESTAMP = 1;

    private static final long DEFAULT_TIMESTAMP = 0;

    private static final int INDEX_TRACE = 2;

    private static final long DEFAULT_TRACE = 0;

    private static final int INDEX_AUTHORIZATION = 3;

    private static final long DEFAULT_AUTHORIZATION = 0;

    private static final int INDEX_EXTENSION = 4;

    private static final int FIELD_COUNT = 5;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_TIMESTAMP);
        set(INDEX_TRACE);
        set(INDEX_AUTHORIZATION);
        set(INDEX_EXTENSION);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "streamId",
      "timestamp",
      "trace",
      "authorization",
      "extension"
    };

    private final OctetsFW.Builder extensionRW = new OctetsFW.Builder();

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new EndFW());
    }

    public Builder streamId(long value) {
      checkFieldNotSet(INDEX_STREAM_ID);
      checkFieldsSet(0, INDEX_STREAM_ID);
      int newLimit = limit() + FIELD_SIZE_STREAM_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_STREAM_ID);
      limit(newLimit);
      return this;
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

    public Builder trace(long value) {
      checkFieldNotSet(INDEX_TRACE);
      if (!fieldsSet.get(INDEX_TIMESTAMP)) {
        timestamp(DEFAULT_TIMESTAMP);
      }
      checkFieldsSet(0, INDEX_TRACE);
      int newLimit = limit() + FIELD_SIZE_TRACE;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_TRACE);
      limit(newLimit);
      return this;
    }

    public Builder authorization(long value) {
      checkFieldNotSet(INDEX_AUTHORIZATION);
      if (!fieldsSet.get(INDEX_TRACE)) {
        trace(DEFAULT_TRACE);
      }
      checkFieldsSet(0, INDEX_AUTHORIZATION);
      int newLimit = limit() + FIELD_SIZE_AUTHORIZATION;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_AUTHORIZATION);
      limit(newLimit);
      return this;
    }

    private OctetsFW.Builder extension() {
      checkFieldNotSet(INDEX_EXTENSION);
      if (!fieldsSet.get(INDEX_AUTHORIZATION)) {
        authorization(DEFAULT_AUTHORIZATION);
      }
      checkFieldsSet(0, INDEX_EXTENSION);
      return extensionRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder extension(OctetsFW value) {
      OctetsFW.Builder extensionRW = extension();
      extensionRW.set(value);
      limit(extensionRW.build().limit());
      fieldsSet.set(INDEX_EXTENSION);
      return this;
    }

    public Builder extension(Consumer<OctetsFW.Builder> mutator) {
      OctetsFW.Builder extensionRW = extension();
      mutator.accept(extensionRW);
      limit(extensionRW.build().limit());
      fieldsSet.set(INDEX_EXTENSION);
      return this;
    }

    public Builder extension(DirectBuffer buffer, int offset, int length) {
      OctetsFW.Builder extensionRW = extension();
      extensionRW.set(buffer, offset, length);
      limit(extensionRW.build().limit());
      fieldsSet.set(INDEX_EXTENSION);
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
    public EndFW build() {
      if (!fieldsSet.get(INDEX_EXTENSION)) {
        extension(b -> { });
      }
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
