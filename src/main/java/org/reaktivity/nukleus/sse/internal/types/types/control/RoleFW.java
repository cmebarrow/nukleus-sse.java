// TODO: license
package org.reaktivity.nukleus.sse.internal.types.control;

import org.agrona.BitUtil;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;

public final class RoleFW extends Flyweight {
  private static final int FIELD_OFFSET_VALUE = 0;

  private static final int FIELD_SIZE_VALUE = BitUtil.SIZE_OF_BYTE;

  @Override
  public int limit() {
    return maxLimit() == offset() ? offset() : offset() + FIELD_SIZE_VALUE;
  }

  public Role get() {
    if (maxLimit() == offset()) {
      return null;
    }
    return Role.valueOf(buffer().getByte(offset() + FIELD_OFFSET_VALUE));
  }

  @Override
  public RoleFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public String toString() {
    return maxLimit() == offset() ? "null" : get().toString();
  }

  public static final class Builder extends Flyweight.Builder<RoleFW> {
    private boolean valueSet;

    public Builder() {
      super(new RoleFW());
    }

    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      super.wrap(buffer, offset, maxLimit);
      return this;
    }

    public Builder set(RoleFW value) {
      int newLimit = offset() + value.sizeof();
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(offset(), value.buffer(), value.offset(), value.sizeof());
      limit(newLimit);
      valueSet = true;
      return this;
    }

    public Builder set(Role value) {
      MutableDirectBuffer buffer = buffer();
      int offset = offset();
      int newLimit = offset + BitUtil.SIZE_OF_BYTE;
      checkLimit(newLimit, maxLimit());
      buffer.putByte(offset, (byte) value.ordinal());
      limit(newLimit);
      valueSet = true;
      return this;
    }

    @Override
    public RoleFW build() {
      if (!valueSet) {
        throw new IllegalStateException("Role not set");
      }
      return super.build();
    }
  }
}
