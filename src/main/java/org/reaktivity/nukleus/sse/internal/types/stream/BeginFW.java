// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.OctetsFW;
import org.reaktivity.nukleus.sse.internal.types.StringFW;

public final class BeginFW extends Flyweight {
  public static final int FIELD_OFFSET_STREAM_ID = 0;

  private static final int FIELD_SIZE_STREAM_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TIMESTAMP = FIELD_OFFSET_STREAM_ID + FIELD_SIZE_STREAM_ID;

  private static final int FIELD_SIZE_TIMESTAMP = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_TRACE = FIELD_OFFSET_TIMESTAMP + FIELD_SIZE_TIMESTAMP;

  private static final int FIELD_SIZE_TRACE = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_AUTHORIZATION = FIELD_OFFSET_TRACE + FIELD_SIZE_TRACE;

  private static final int FIELD_SIZE_AUTHORIZATION = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_SOURCE = FIELD_OFFSET_AUTHORIZATION + FIELD_SIZE_AUTHORIZATION;

  public static final int FIELD_OFFSET_SOURCE_REF = 0;

  private static final int FIELD_SIZE_SOURCE_REF = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_CORRELATION_ID = FIELD_OFFSET_SOURCE_REF + FIELD_SIZE_SOURCE_REF;

  private static final int FIELD_SIZE_CORRELATION_ID = BitUtil.SIZE_OF_LONG;

  public static final int FIELD_OFFSET_EXTENSION = FIELD_OFFSET_CORRELATION_ID + FIELD_SIZE_CORRELATION_ID;

  public static final int TYPE_ID = 0x00000001;

  private final StringFW sourceRO = new StringFW();

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

  public StringFW source() {
    return sourceRO;
  }

  public long sourceRef() {
    return buffer().getLong(sourceRO.limit() + FIELD_OFFSET_SOURCE_REF);
  }

  public long correlationId() {
    return buffer().getLong(sourceRO.limit() + FIELD_OFFSET_CORRELATION_ID);
  }

  public OctetsFW extension() {
    return extensionRO;
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public BeginFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    sourceRO.wrap(buffer, offset + FIELD_OFFSET_SOURCE, maxLimit);
    extensionRO.wrap(buffer, sourceRO.limit() + FIELD_OFFSET_EXTENSION, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return extensionRO.limit();
  }

  @Override
  public String toString() {
    return String.format("BEGIN [streamId=%d, timestamp=%d, trace=%d, authorization=%d, source=%s, sourceRef=%d, correlationId=%d, extension=%s]", streamId(), timestamp(), trace(), authorization(), sourceRO.asString(), sourceRef(), correlationId(), extension());
  }

  public static final class Builder extends Flyweight.Builder<BeginFW> {
    private static final int INDEX_STREAM_ID = 0;

    private static final int INDEX_TIMESTAMP = 1;

    private static final long DEFAULT_TIMESTAMP = 0;

    private static final int INDEX_TRACE = 2;

    private static final long DEFAULT_TRACE = 0;

    private static final int INDEX_AUTHORIZATION = 3;

    private static final long DEFAULT_AUTHORIZATION = 0;

    private static final int INDEX_SOURCE = 4;

    private static final int INDEX_SOURCE_REF = 5;

    private static final int INDEX_CORRELATION_ID = 6;

    private static final int INDEX_EXTENSION = 7;

    private static final int FIELD_COUNT = 8;

    private int lastFieldSet = -1;

    private final StringFW.Builder sourceRW = new StringFW.Builder();

    private final OctetsFW.Builder extensionRW = new OctetsFW.Builder();


    public Builder() {
      super(new BeginFW());
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

    private StringFW.Builder source() {
      if (lastFieldSet < INDEX_AUTHORIZATION) {
        authorization(DEFAULT_AUTHORIZATION);
      }
      assert lastFieldSet == INDEX_SOURCE - 1;
      return sourceRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder source(String value) {
      StringFW.Builder sourceRW = source();
      sourceRW.set(value, StandardCharsets.UTF_8);
      lastFieldSet = INDEX_SOURCE;
      limit(sourceRW.build().limit());
      return this;
    }

    public Builder source(StringFW value) {
      StringFW.Builder sourceRW = source();
      sourceRW.set(value);
      lastFieldSet = INDEX_SOURCE;
      limit(sourceRW.build().limit());
      return this;
    }

    public Builder source(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder sourceRW = source();
      sourceRW.set(buffer, offset, length);
      lastFieldSet = INDEX_SOURCE;
      limit(sourceRW.build().limit());
      return this;
    }

    public Builder sourceRef(long value) {
      assert lastFieldSet == INDEX_SOURCE_REF - 1;
      int newLimit = limit() + FIELD_SIZE_SOURCE_REF;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_SOURCE_REF;
      limit(newLimit);
      return this;
    }

    public Builder correlationId(long value) {
      assert lastFieldSet == INDEX_CORRELATION_ID - 1;
      int newLimit = limit() + FIELD_SIZE_CORRELATION_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      lastFieldSet = INDEX_CORRELATION_ID;
      limit(newLimit);
      return this;
    }

    private OctetsFW.Builder extension() {
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
    public BeginFW build() {
      if (lastFieldSet < INDEX_EXTENSION) {
        extension(b -> { });
      }
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}