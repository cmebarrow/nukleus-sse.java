// TODO: license
package org.reaktivity.nukleus.sse.internal.types;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

public final class OctetsFW extends Flyweight {
  public <T> T get(Flyweight.Visitor<T> visitor) {
    DirectBuffer buffer = buffer();
    int offset = offset();
    int limit = limit();
    return visitor.visit(buffer, offset, limit);
  }

  @Override
  public int limit() {
    return maxLimit();
  }

  @Override
  public OctetsFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public String toString() {
    return String.format("octets[%d]", sizeof());
  }

  public static final class Builder extends Flyweight.Builder<OctetsFW> {
    public Builder() {
      super(new OctetsFW());
    }

    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      super.wrap(buffer, offset, maxLimit);
      return this;
    }

    @Deprecated
    public Builder reset() {
      limit(offset());
      return this;
    }

    public Builder set(OctetsFW value) {
      int newLimit = offset() + value.sizeof();
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(offset(), value.buffer(), value.offset(), value.sizeof());
      limit(newLimit);
      return this;
    }

    public Builder set(DirectBuffer value, int offset, int length) {
      int newLimit = offset() + length;
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(offset(), value, offset, length);
      limit(newLimit);
      return this;
    }

    public Builder set(byte[] value) {
      int newLimit = offset() + value.length;
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(offset(), value);
      limit(newLimit);
      return this;
    }

    public Builder set(Flyweight.Builder.Visitor visitor) {
      int length = visitor.visit(buffer(), offset(), maxLimit());
      checkLimit(offset() + length, maxLimit());
      limit(offset() + length);
      return this;
    }

    public Builder put(OctetsFW value) {
      int newLimit = limit() + value.sizeof();
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(limit(), value.buffer(), value.offset(), value.sizeof());
      limit(newLimit);
      return this;
    }

    public Builder put(DirectBuffer value, int offset, int length) {
      int newLimit = limit() + length;
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(limit(), value, offset, length);
      limit(newLimit);
      return this;
    }

    public Builder put(byte[] value) {
      int newLimit = limit() + value.length;
      checkLimit(newLimit, maxLimit());
      buffer().putBytes(limit(), value);
      limit(newLimit);
      return this;
    }

    public Builder put(Flyweight.Builder.Visitor visitor) {
      int length = visitor.visit(buffer(), limit(), maxLimit());
      checkLimit(limit() + length, maxLimit());
      limit(limit() + length);
      return this;
    }
  }
}
