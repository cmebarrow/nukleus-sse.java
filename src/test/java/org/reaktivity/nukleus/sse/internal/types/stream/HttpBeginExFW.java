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

    @SuppressWarnings("serial")
    private static final BitSet FIELDS_WITH_DEFAULTS = new BitSet(FIELD_COUNT)  {
        {
        set(INDEX_HEADERS);
      }
    }
    ;

    private static final String[] FIELD_NAMES = {
      "headers"
    };

    private final ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW = new ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>(new HttpHeaderFW.Builder(), new HttpHeaderFW());

    private final BitSet fieldsSet = new BitSet(FIELD_COUNT);

    public Builder() {
      super(new HttpBeginExFW());
    }

    public Builder headers(Consumer<ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW>> mutator) {
      checkFieldNotSet(INDEX_HEADERS);
      checkFieldsSet(0, INDEX_HEADERS);
      ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW = this.headersRW.wrap(buffer(), limit(), maxLimit());
      mutator.accept(headersRW);
      limit(headersRW.build().limit());
      fieldsSet.set(INDEX_HEADERS);
      return this;
    }

    public Builder headersItem(Consumer<HttpHeaderFW.Builder> mutator) {
      checkFieldsSet(0, INDEX_HEADERS);
      if (!fieldsSet.get(INDEX_HEADERS)) {
        ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW = this.headersRW.wrap(buffer(), limit(), maxLimit());
      }
      headersRW.item(mutator);
      limit(headersRW.build().limit());
      fieldsSet.set(INDEX_HEADERS);
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
    public HttpBeginExFW build() {
      if (!fieldsSet.get(INDEX_HEADERS)) {
        headers(b -> { });
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
