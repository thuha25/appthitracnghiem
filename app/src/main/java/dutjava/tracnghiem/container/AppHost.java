package dutjava.tracnghiem.container;

import dutjava.tracnghiem.util.dependency_injection.Host;
import dutjava.tracnghiem.util.dependency_injection.HostBuilder;

public class AppHost {
    static public Host host = new HostBuilder().build();
}
