// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.HttpHeaderFW;
import org.reaktivity.nukleus.sse.internal.types.ListFW;

public final class HttpBeginExFW extends Flyweight {
  public static final int FIELD_OFFSET_HEADERS = 0;

  private final ListFW<HttpHeaderFW> headersRO = new ListFW<HttpHeaderFW>(new HttpHeaderFW());

  public ListFW<HttpHeaderFW> headers() {
    return headersRO;
  }

  @Override
  public HttpBeginExFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    headersRO.wrap(buffer, offset + FIELD_OFFSET_HEADERS, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return headersRO.limit();
  }

  @Override
  public String toString() {
    return String.format("HTTP_BEGIN_EX [headers=%s]", headers());
  }

  public static final class Builder extends Flyweight.Builder<HttpBeginExFW> {
    private static final int INDEX_HEADERS = 0;

    private static final int FIELD_COUNT = 1;

    private int lastFieldSet = -1;

    private final ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW = new ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>(new HttpHeaderFW.Builder(), new HttpHeaderFW());


    public Builder() {
      super(new HttpBeginExFW());
    }

    public Builder headers(Consumer<ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>> mutator) {
      assert lastFieldSet == INDEX_HEADERS - 1;
      ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW = this.headersRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(headersRW);
      limit(headersRW.build().limit());
      lastFieldSet = INDEX_HEADERS;
      return this;
    }

    public Builder headersItem(Consumer<HttpHeaderFW.Builder> mutator) {
      assert lastFieldSet == INDEX_HEADERS - 1;
      if (lastFieldSet < INDEX_HEADERS) {
        ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW = this.headersRW.wrap(buffer(), limit(), maxLimit());
      }
      headersRW.item(mutator);
      limit(headersRW.build().limit());
      lastFieldSet = INDEX_HEADERS;
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
    public HttpBeginExFW build() {
      if (lastFieldSet < INDEX_HEADERS) {
        headers(b -> { });
      }
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
