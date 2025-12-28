package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.api.FfmApi;

import java.lang.foreign.Arena;

public interface FfmApiProvider {
    FfmApi getApi(Arena session);
}
