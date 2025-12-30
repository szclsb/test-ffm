package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.api.FfmApi;
import ch.szclsb.test.ffm.api.ForeignFactory;
import ch.szclsb.test.ffm.impl.FfmApiImpl;
import ch.szclsb.test.ffm.impl.ForeignFactoryImpl;

import java.lang.foreign.Arena;
import java.nio.file.Path;

public class FfmServiceProviderImpl implements FfmServiceProvider {
    @Override
    public ForeignFactory getFactory(Arena factorySession) {
        return new ForeignFactoryImpl(factorySession);
    }

    @Override
    public FfmApi getApi(Path apiDllPath, Arena apiSession, ForeignFactory factory) {
        return new FfmApiImpl(apiDllPath, apiSession, factory);
    }
}
