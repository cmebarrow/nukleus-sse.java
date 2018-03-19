// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;

public final class HttpDataExFW extends Flyweight {
  @Override
  public HttpDataExFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return offset();
  }

  @Override
  public String toString() {
    return "HTTP_DATA_EX";
  }

  public static final class Builder extends Flyweight.Builder<HttpDataExFW> {
    private static final int FIELD_COUNT = 0;

    private int lastFieldSet = -1;

    public Builder() {
      super(new HttpDataExFW());
    }

    @Override
    public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit) {
      lastFieldSet = -1;
      super.wrap(buffer, offset, maxLimit);
      limit(offset);
      return this;
    }

    @Override
    public HttpDataExFW build() {
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
