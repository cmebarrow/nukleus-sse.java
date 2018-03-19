// TODO: license
package org.reaktivity.nukleus.sse.internal.types.control;

import java.util.BitSet;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;

public final class UnroutedFW extends Flyweight {
  public static final int FIELD_OFFSET_CORRELATION_ID = 0;

  private static final int FIELD_SIZE_CORRELATION_ID = BitUtil.SIZE_OF_LONG;

  public static final int TYPE_ID = 0x40000002;

  public long correlationId() {
    return buffer().getLong(offset() + FIELD_OFFSET_CORRELATION_ID);
  }

  public int typeId() {
    return TYPE_ID;
  }

  @Override
  public UnroutedFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return offset() + FIELD_OFFSET_CORRELATION_ID + FIELD_SIZE_CORRELATION_ID;
  }

  @Override
  public String toString() {
    return String.format("UNROUTED [correlationId=%d]", correlationId());
  }

  public static final class Builder extends Flyweight.Builder<UnroutedFW> {
    private static final int INDEX_CORRELATION_ID = 0;

    private static final int FIELD_COUNT = 1;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "correlationId"
    };

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new UnroutedFW());
    }

    public Builder correlationId(long value) {
      checkFieldNotSet(INDEX_CORRELATION_ID);
      checkFieldsSet(0, INDEX_CORRELATION_ID);
      int newLimit = limit() + FIELD_SIZE_CORRELATION_ID;
      checkLimit(newLimit, maxLimit());
      buffer().putLong(limit(), value);
      fieldsSet.set(INDEX_CORRELATION_ID);
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
    public UnroutedFW build() {
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
