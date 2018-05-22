package com.johnathangilday;

import com.johnathangilday.jaxrs.NameNotFoundExceptionMapper;
import lombok.AllArgsConstructor;

/**
 * JAX-RS knows how to map this exception with the {@link NameNotFoundExceptionMapper}
 */
@AllArgsConstructor
public class NameNotFoundException extends RuntimeException {
    public final int id;

    public NameNotFoundException(int id) {
        this.id = id;
    }
}
