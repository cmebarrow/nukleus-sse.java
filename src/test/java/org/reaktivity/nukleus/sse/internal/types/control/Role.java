// TODO: license
package org.reaktivity.nukleus.sse.internal.types.control;

public enum Role {
  SERVER,

  CLIENT,

  PROXY;

  public static Role valueOf(int ordinal) {
    switch (ordinal) {
      case 0: {
        return SERVER;
      }
      case 1: {
        return CLIENT;
      }
      case 2: {
        return PROXY;
      }
    }
    throw new IllegalArgumentException(String.format("Unrecognized value: %d", ordinal));
  }
}
