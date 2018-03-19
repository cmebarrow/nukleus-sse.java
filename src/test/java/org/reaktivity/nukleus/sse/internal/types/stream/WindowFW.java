// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;

public final class WindowFW extends Flyweight {
  public static final int FIELD_OFFSET_STREAM_ID = 0;

  private static final int FIELD_SIZE_STREAM_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TIMESTAMP = FIELD_OFFSET_STREAM_ID + FIELD_SIZE_STREAM_ID;

  private static final int FIELD_SIZE_TIMESTAMP = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TRACE = FIELD_OFFSET_TIMESTAMP + FIELD_SIZE_TIMESTAMP;

  private static final int FIELD_SIZE_TRACE = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_CREDIT = FIELD_OFFSET_TRACE + FIELD_SIZE_TRACE;

  private static final int FIELD_SIZE_CREDIT = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_PADDING = FIELD_OFFSET_CREDIT + FIELD_SIZE_CREDIT;

  private static final int FIELD_SIZE_PADDING = BitUtil.SIZE_OF_INT;

  public static final int FIELD_OFFSET_GROUP_ID = FIELD_OFFSET_PADDING + FIELD_SIZE_PADDING;

  private static final int FIELD_SIZE_GROUP_ID = BitUtil.SIZE_OF_LONG;

  public static final int TYPE_ID = 0x40000002;

  public long streamId() {
    return buffer().getLong(offset() + FIELD_OFFSET_STREAM_ID);
  }

  public long timestamp() {
    return buffer().getLong(offset() + FIELD_OFFSET_TIMESTAMP);
  }

  public long trace() {
    return buffer().getLong(offset() + FIELD_OFFSET_TRACE);
  }

  public int credit() {
    return buffer().getInt(offset() + FIELD_OFFSET_CREDIT);
  }

  public int padding() {
    return buffer().getInt(offset() + FIELD_OFFSET_PADDING);
  }

  public long groupId() {
    return buffer().getLong(offset() + FIELD_OFFSET_GROUP_ID);
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public WindowFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return offset() + FIELD_OFFSET_GROUP_ID + FIELD_SIZE_GROUP_ID;
  }

  @Override
  public String toString() {
    return String.format("WINDOW [streamId=%d, timestamp=%d, trace=%d, credit=%d, padding=%d, groupId=%d]", streamId(), timestamp(), trace(), credit(), padding(), groupId());
  }

  public static final class Builder extends Flyweight.Builder<WindowFW> {
    private static final int INDEX_STREAM_ID = 0;

    private static final int INDEX_TIMESTAMP = 1;

    private static final long DEFAULT_TIMESTAMP = 0;

    private static final int INDEX_TRACE = 2;

    private static final long DEFAULT_TRACE = 0;

    private static final int INDEX_CREDIT = 3;

    private static final int INDEX_PADDING = 4;

    private static final int INDEX_GROUP_ID = 5;

    private static final int FIELD_COUNT = 6;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_TIMESTAMP);
        set(INDEX_TRACE);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "streamId",
      "timestamp",
      "trace",
      "credit",
      "padding",
      "groupId"
    };

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new WindowFW());
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

    public Builder credit(int value) {
      checkFieldNotSet(INDEX_CREDIT);
      if (!fieldsSet.get(INDEX_TRACE)) {
        trace(DEFAULT_TRACE);
      }
      checkFieldsSet(0, INDEX_CREDIT);
      int newLimit = limit() + FIELD_SIZE_CREDIT;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), value);
      fieldsSet.set(INDEX_CREDIT);
      limit(newLimit);
      return this;
    }

    public Builder padding(int value) {
      checkFieldNotSet(INDEX_PADDING);
      checkFieldsSet(0, INDEX_PADDING);
      int newLimit = limit() + FIELD_SIZE_PADDING;
      checkLimit(newLimit, maxLimit());
      buffer().putInt(limit(), value);
      fieldsSet.set(INDEX_PADDING);
      limit(newLimit);
      return this;
    }

    public Builder groupId(long value) {
      checkFieldNotSet(INDEX_GROUP_ID);
      checkFieldsSet(0, INDEX_GROUP_ID);
      int newLimit = limit() + FIELD_SIZE_GROUP_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_GROUP_ID);
      limit(newLimit);
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
    public WindowFW build() {
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
