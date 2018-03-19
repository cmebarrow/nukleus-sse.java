// TODO: license
package org.reaktivity.nukleus.sse.internal.types.stream;

import java.util.BitSet;
import java.util.function.Consumer;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.sse.internal.types.Flyweight;
import org.reaktivity.nukleus.sse.internal.types.HttpHeaderFW;
import org.reaktivity.nukleus.sse.internal.types.ListFW;

public final class HttpEndExFW extends Flyweight {
  public static final int FIELD_OFFSET_TRAILERS = 0;

  private final ListFW<HttpHeaderFW> trailersRO = new ListFW<HttpHeaderFW>(new HttpHeaderFW());

  public ListFW<HttpHeaderFW> trailers() {
    return trailersRO;
  }

  @Override
  public HttpEndExFW wrap(DirectBuffer buffer, int offset, int maxLimit) {
    super.wrap(buffer, offset, maxLimit);
    trailersRO.wrap(buffer, offset + FIELD_OFFSET_TRAILERS, maxLimit);
    checkLimit(limit(), maxLimit);
    return this;
  }

  @Override
  public int limit() {
    return trailersRO.limit();
  }

  @Override
  public String toString() {
    return String.format("HTTP_END_EX [trailers=%s]", trailers());
  }

  public static final class Builder extends Flyweight.Builder<HttpEndExFW> {
    private static final int INDEX_TRAILERS = 0;

    private static final int FIELD_COUNT = 1;

    private int lastFieldSet = -1;

    private final ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> trailersRW = new ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>(new HttpHeaderFW.Builder(), new HttpHeaderFW());


    public Builder() {
      super(new HttpEndExFW());
    }

    public Builder trailers(Consumer<ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>> mutator) {
      assert lastFieldSet == INDEX_TRAILERS - 1;
      ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> trailersRW = this.trailersRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(trailersRW);
      limit(trailersRW.build().limit());
      lastFieldSet = INDEX_TRAILERS;
      return this;
    }

    public Builder trailersItem(Consumer<HttpHeaderFW.Builder> mutator) {
      assert lastFieldSet == INDEX_TRAILERS - 1;
      if (lastFieldSet < INDEX_TRAILERS) {
        ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> trailersRW = this.trailersRW.wrap(buffer(), limit(), maxLimit());
      }
      trailersRW.item(mutator);
      limit(trailersRW.build().limit());
      lastFieldSet = INDEX_TRAILERS;
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
    public HttpEndExFW build() {
      if (lastFieldSet < INDEX_TRAILERS) {
        trailers(b -> { });
      }
      assert lastFieldSet == FIELD_COUNT - 1;
      lastFieldSet = -1;
      return super.build();
    }

  }
}
