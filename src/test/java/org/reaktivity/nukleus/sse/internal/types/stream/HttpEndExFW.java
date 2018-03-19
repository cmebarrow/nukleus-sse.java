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

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_TRAILERS);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "trailers"
    };

    private final ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> trailersRW = new ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>(new HttpHeaderFW.Builder(), new HttpHeaderFW());

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new HttpEndExFW());
    }

    public Builder trailers(Consumer<ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>> mutator) {
      checkFieldNotSet(INDEX_TRAILERS);
      checkFieldsSet(0, INDEX_TRAILERS);
      ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> trailersRW = this.trailersRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(trailersRW);
      limit(trailersRW.build().limit());
      fieldsSet.set(INDEX_TRAILERS);
      return this;
    }

    public Builder trailersItem(Consumer<HttpHeaderFW.Builder> mutator) {
      checkFieldsSet(0, INDEX_TRAILERS);
      if (!fieldsSet.get(INDEX_TRAILERS)) {
        ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> trailersRW = this.trailersRW.wrap(buffer(), limit(), maxLimit());
      }
      trailersRW.item(mutator);
      limit(trailersRW.build().limit());
      fieldsSet.set(INDEX_TRAILERS);
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
    public HttpEndExFW build() {
      if (!fieldsSet.get(INDEX_TRAILERS)) {
        trailers(b -> { });
      }
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
