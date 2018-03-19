// TODO: license
package org.reaktivity.nukleus.sse.internal.types;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

public final class Varint32FW extends Flyweight {
  private int size;

  @Override
  public int limit() {
    return offset() + size;
  }

  public int value() {
    int value = 0;
    int i = 0;;
    int b;
    int pos  = offset();
    while (((b = buffer().getByte(pos++)) & 0x80) != 0) {
      value |= (b & 0x7F) << i;
      i += 7;
      if (i > 35) {
        throw new IllegalArgumentException("varint32 value too long");
      }
    }
    int unsigned = value  | (b << i);;
    int result = (((unsigned << 31) >> 31) ^ unsigned) >> 1;
    result = result ^ (unsigned & (1 << 31));
    return result;
  }

  @Override
  public Varint32FW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    size = length0();
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public String toString() {
    return Integer.toString(value());
  }

  private int length0() {
    int pos = offset();
    byte b = (byte) 0;
    final int maxPos = Math.max(pos + 5,  maxLimit());
    while (pos <= maxPos && ((b = buffer().getByte(pos)) & 0x80) != 0) {
      pos++;
    }
    int size = 1 + pos - offset();
    int mask = size < 5 ? 0x80 : 0xf0;
    if ((b & mask) != 0) {
      throw new IllegalArgumentException(String.format("varint32 value at offset %d exceeds 32 bits", offset()));
    }
    return size;
  }

  public static final class Builder extends Flyweight.Builder<Varint32FW> {
    private boolean valueSet;

    public Builder() {
      super(new Varint32FW());
    }

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      checkLimit(offset + 1, maxLimit);
      super.wrap(buffer, offset, maxLimit);
      this.valueSet = false;
      return this;
    }

    public Builder set(int value) {
      int zigzagged = (value << 1) ^ (value >> 31);
      int pos = offset();
      int bits = 1 + Integer.numberOfTrailingZeros(Integer.highestOneBit(zigzagged));
      int size = bits / 7;
      if (size * 7 < bits) {
        size++;
      }
      int newLimit = pos + size;
      checkLimit(newLimit, maxLimit());
      while ((zigzagged & 0xFFFFFF80) != 0) {
        buffer().putByte(pos++, (byte) ((zigzagged & 0x7F) | 0x80));
        zigzagged >>>= 7;
      }
      buffer().putByte(pos, (byte) (zigzagged & 0x7F));
      limit(newLimit);
      valueSet = true;
      return this;
    }

    @Override
    public Varint32FW build() {
      if (!valueSet) {
        throw new IllegalArgumentException("value not set");
      }
      return super.build();
    }
  }
}
