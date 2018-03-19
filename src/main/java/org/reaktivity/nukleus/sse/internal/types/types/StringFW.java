// TODO: license
package org.reaktivity.nukleus.sse.internal.types;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;

public final class StringFW extends Flyweight {
  private static final int FIELD_OFFSET_LENGTH = 0;

  private static final int FIELD_SIZE_LENGTH = BitUtil.SIZE_OF_BYTE;

  private final DirectBuffer valueRO = new UnsafeBuffer(0L, 0);

  @Override
  public int limit() {
    return maxLimit() == offset() ? offset() : offset() + FIELD_SIZE_LENGTH + Math.max(length0(), 0);
  }

  public String asString() {
    if (maxLimit() == offset() || length0() == -1) {
      return null;
    }
    return buffer().getStringWithoutLengthUtf8(offset() + FIELD_SIZE_LENGTH, length0());
  }

  @Override
  public StringFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    int length0 = length0();
    if (length0 != -1) {
      valueRO.wrap(buffer, offset + FIELD_SIZE_LENGTH, length0);
    }
    return this;
  }

  public DirectBuffer value() {
    return length0() == -1 ? null : valueRO;
  }

  @Override
  public String toString() {
    return maxLimit() == offset() ? "null" : String.format("\"%s\"", asString());
  }

  private int length0() {
    int length = buffer().getByte(offset() + FIELD_OFFSET_LENGTH) & 0xFF;
    return length == 255 ? -1 : length;
  }

  public static final class Builder extends Flyweight.Builder<StringFW> {
    private boolean valueSet;

    public Builder() {
      super(new StringFW());
    }

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      checkLimit(offset + FIELD_OFFSET_LENGTH + FIELD_SIZE_LENGTH, maxLimit);
      super.wrap(buffer, offset, maxLimit);
      this.valueSet = false;
      return this;
    }

    public Builder set(StringFW value) {
      if (value == null) {
        int newLimit = offset() + FIELD_SIZE_LENGTH;
        checkLimit(newLimit, maxLimit());
        buffer().putByte(offset(), (byte) -1);
        limit(newLimit);
      } else {
        int newLimit = offset() + value.sizeof();
        checkLimit(newLimit, maxLimit());
        buffer().putBytes(offset(), value.buffer(), value.offset(), value.sizeof());
        limit(newLimit);
      }
      valueSet = true;
      return this;
    }

    public Builder set(DirectBuffer srcBuffer, int srcOffset, int length) {
      checkLength(length);
      int offset = offset();
      int newLimit = offset + length + FIELD_SIZE_LENGTH;
      checkLimit(newLimit, maxLimit());
      buffer().putByte(offset, (byte) length);
      buffer().putBytes(offset + 1, srcBuffer, srcOffset, length);
      limit(newLimit);
      valueSet = true;
      return this;
    }

    public Builder set(String value, Charset charset) {
      if (value == null) {
        int newLimit = offset() + FIELD_SIZE_LENGTH;
        checkLimit(newLimit, maxLimit());
        buffer().putByte(offset(), (byte) -1);
        limit(newLimit);
      } else {
        byte[] charBytes = value.getBytes(charset);
        checkLength(charBytes.length);
        int newLimit = offset() + FIELD_SIZE_LENGTH + charBytes.length;
        checkLimit(newLimit, maxLimit());
        buffer().putByte(offset(), (byte) charBytes.length);
        buffer().putBytes(offset() + 1, charBytes);
        limit(newLimit);
      }
      valueSet = true;
      return this;
    }

    private static void checkLength(int length) {
      final int maxLength = 254;
      if (length > maxLength) {
        final String msg = String.format("length=%d is beyond maximum length=%d", length, maxLength);
        throw new IllegalArgumentException(msg);
      }
    }

    @Override
    public StringFW build() {
      if (!valueSet) {
        set(null, StandardCharsets.UTF_8);
      }
      return super.build();
    }
  }
}
