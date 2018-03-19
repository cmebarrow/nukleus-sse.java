// TODO: license
package org.reaktivity.nukleus.sse.internal.types;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

public final class HttpHeaderFW extends Flyweight {
  public static final int FIELD_OFFSET_REPRESENTATION = 0;

  private static final int FIELD_SIZE_REPRESENTATION = BitUtil.SIZE_OF_BYTE;

  public static final int FIELD_OFFSET_NAME = FIELD_OFFSET_REPRESENTATION + FIELD_SIZE_REPRESENTATION;

  public static final int FIELD_OFFSET_VALUE = 0;

  private final StringFW nameRO = new StringFW();

  private final String16FW valueRO = new String16FW();

  public byte representation() {
    return buffer().getByte(offset() + FIELD_OFFSET_REPRESENTATION);
  }

  public StringFW name() {
    return nameRO;
  }

  public String16FW value() {
    return valueRO;
  }

  @Override
  public HttpHeaderFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    nameRO.wrap(buffer, offset + FIELD_OFFSET_NAME, maxLimit);
    valueRO.wrap(buffer, nameRO.limit() + FIELD_OFFSET_VALUE, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return valueRO.limit();
  }

  @Override
  public String toString() {
    return String.format("HTTP_HEADER [representation=%d, name=%s, value=%s]", representation(), nameRO.asString(), valueRO.asString());
  }

  public static final class Builder extends Flyweight.Builder<HttpHeaderFW> {
    private static final int INDEX_REPRESENTATION = 0;

    private static final byte DEFAULT_REPRESENTATION = 0;

    private static final int INDEX_NAME = 1;

    private static final int INDEX_VALUE = 2;

    private static final int FIELD_COUNT = 3;

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_REPRESENTATION);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "representation",
      "name",
      "value"
    };

    private final StringFW.Builder nameRW = new StringFW.Builder();

    private final String16FW.Builder valueRW = new String16FW.Builder();

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new HttpHeaderFW());
    }

    public Builder representation(byte value) {
      checkFieldNotSet(INDEX_REPRESENTATION);
      checkFieldsSet(0, INDEX_REPRESENTATION);
      int newLimit = limit() + FIELD_SIZE_REPRESENTATION;
      checkLimit(newLimit, maxLimit());
      buffer().putByte(limit(), value);
      fieldsSet.set(INDEX_REPRESENTATION);
      limit(newLimit);
      return this;
    }

    private StringFW.Builder name() {
      checkFieldNotSet(INDEX_NAME);
      if (!fieldsSet.get(INDEX_REPRESENTATION)) {
        representation(DEFAULT_REPRESENTATION);
      }
      checkFieldsSet(0, INDEX_NAME);
      return nameRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder name(String value) {
      StringFW.Builder nameRW = name();
      nameRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_NAME);
      limit(nameRW.build().limit());
      return this;
    }

    public Builder name(StringFW value) {
      StringFW.Builder nameRW = name();
      nameRW.set(value);
      fieldsSet.set(INDEX_NAME);
      limit(nameRW.build().limit());
      return this;
    }

    public Builder name(DirectBuffer buffer, int offset, int length) {
      StringFW.Builder nameRW = name();
      nameRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_NAME);
      limit(nameRW.build().limit());
      return this;
    }

    private String16FW.Builder value() {
      checkFieldNotSet(INDEX_VALUE);
      checkFieldsSet(0, INDEX_VALUE);
      return valueRW.wrap(buffer(), limit(), maxLimit());
    }

    public Builder value(String value) {
      String16FW.Builder valueRW = value();
      valueRW.set(value, StandardCharsets.UTF_8);
      fieldsSet.set(INDEX_VALUE);
      limit(valueRW.build().limit());
      return this;
    }

    public Builder value(String16FW value) {
      String16FW.Builder valueRW = value();
      valueRW.set(value);
      fieldsSet.set(INDEX_VALUE);
      limit(valueRW.build().limit());
      return this;
    }

    public Builder value(DirectBuffer buffer, int offset, int length) {
      String16FW.Builder valueRW = value();
      valueRW.set(buffer, offset, length);
      fieldsSet.set(INDEX_VALUE);
      limit(valueRW.build().limit());
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
    public HttpHeaderFW build() {
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
