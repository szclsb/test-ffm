package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.api.FfmApi;
import ch.szclsb.test.ffm.api.ForeignFactory;

import java.lang.foreign.Arena;
import java.nio.file.Path;

public interface FfmServiceProvider {
    ForeignFactory getFactory(Arena factorySession);

    FfmApi getApi(Path apiDllPath, Arena apiSession, ForeignFactory factory);
}
