package ch.szclsb.test.ffm.api;

import java.lang.foreign.Arena;

public interface FfmApiProvider {
    FfmApi getApi(Arena session);
}
