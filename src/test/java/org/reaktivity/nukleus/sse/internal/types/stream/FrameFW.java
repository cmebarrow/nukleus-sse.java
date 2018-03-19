// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;

public final class FrameFW extends Flyweight {
  public static final int FIELD_OFFSET_STREAM_ID = 0;

  private static final int FIELD_SIZE_STREAM_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TIMESTAMP = FIELD_OFFSET_STREAM_ID + FIELD_SIZE_STREAM_ID;

  private static final int FIELD_SIZE_TIMESTAMP = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TRACE = FIELD_OFFSET_TIMESTAMP + FIELD_SIZE_TIMESTAMP;

  private static final int FIELD_SIZE_TRACE = BitUtil.SIZE_OF_LONG;

  public long streamId() {
    return buffer().getLong(offset() + FIELD_OFFSET_STREAM_ID);
  }

  public long timestamp() {
    return buffer().getLong(offset() + FIELD_OFFSET_TIMESTAMP);
  }

  public long trace() {
    return buffer().getLong(offset() + FIELD_OFFSET_TRACE);
  }

  @Override
  public FrameFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return offset() + FIELD_OFFSET_TRACE + FIELD_SIZE_TRACE;
  }

  @Override
  public String toString() {
    return String.format("FRAME [streamId=%d, timestamp=%d, trace=%d]", streamId(), timestamp(), trace());
  }

  public static final class Builder extends Flyweight.Builder<FrameFW> {
    private static final int INDEX_STREAM_ID = 0;

    private static final int INDEX_TIMESTAMP = 1;

    private static final long DEFAULT_TIMESTAMP = 0;

    private static final int INDEX_TRACE = 2;

    private static final long DEFAULT_TRACE = 0;

    private static final int FIELD_COUNT = 3;

    private int lastFieldSet = -1;


    public Builder() {
      super(new FrameFW());
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

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      lastFieldSet = -1;
      super.wrap(buffer, offset, maxLimit);
      limit(offset);
      return this;
    }

    @Override
    public FrameFW build() {
      if (lastFieldSet < INDEX_TRACE) {
        trace(DEFAULT_TRACE);
      }
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
