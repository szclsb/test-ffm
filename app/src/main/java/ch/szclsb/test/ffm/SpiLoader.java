package ch.szclsb.test.ffm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class SpiLoader implements AutoCloseable {
    private final URLClassLoader classLoader;

    public SpiLoader() throws IOException, URISyntaxException {
        var directory = Path.of(SpiLoader.class.getClassLoader().getResource("providers").toURI());
        try (var fileStream = Files.list(directory)) {
            var urls = fileStream
                    .map(path -> {
                        try {
                            return path.toUri().toURL();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(URL[]::new);
            this.classLoader = new URLClassLoader(urls);
        }
    }

    public List<FfmApiProvider> providers() {
        var result = new ArrayList<FfmApiProvider>();
        var providers = ServiceLoader.load(FfmApiProvider.class, classLoader);
        for (var provider : providers) {
            result.add(provider);
        }
        if (result.isEmpty()) {
            throw new ProviderNotFoundException(FfmApiProvider.class.getName());
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        this.classLoader.close();
    }
}
