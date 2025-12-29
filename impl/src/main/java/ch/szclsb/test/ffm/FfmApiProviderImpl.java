package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.api.FfmApi;
import ch.szclsb.test.ffm.api.ForeignFactory;
import ch.szclsb.test.ffm.impl.FfmApiImpl;
import ch.szclsb.test.ffm.impl.ForeignFactoryImpl;

import java.lang.foreign.Arena;
import java.nio.file.Path;

public class FfmApiProviderImpl implements FfmApiProvider {
    @Override
    public FfmApi getApi(Arena session, Path dllPath) {
        return new FfmApiImpl(session, dllPath);
    }

    @Override
    public ForeignFactory getFactory(Arena session) {
        return new ForeignFactoryImpl(session);
    }
}
