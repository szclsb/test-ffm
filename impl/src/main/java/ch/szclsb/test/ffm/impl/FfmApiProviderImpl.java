package ch.szclsb.test.ffm.impl;

import ch.szclsb.test.ffm.api.FfmApi;
import ch.szclsb.test.ffm.api.FfmApiProvider;

import java.lang.foreign.Arena;

public class FfmApiProviderImpl implements FfmApiProvider {
    @Override
    public FfmApi getApi(Arena session) {
        return new FfmApiImpl(session);
    }
}
