package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.api.FfmApi;
import ch.szclsb.test.ffm.api.ForeignFactory;

import java.lang.foreign.Arena;

public interface FfmApiProvider {
    FfmApi getApi(Arena session);

    ForeignFactory getFactory(Arena session);
}
