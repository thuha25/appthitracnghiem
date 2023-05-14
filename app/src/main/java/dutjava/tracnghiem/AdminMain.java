package dutjava.tracnghiem;

import dutjava.tracnghiem.container.AppHost;
import dutjava.tracnghiem.container.HostType;
import dutjava.tracnghiem.util.database.DBUtils;
import dutjava.tracnghiem.view.page.AdminPage;

public class AdminMain {
    public static void main(String[] args) {
        DBUtils.instance.SetUsernamePassword("root", "ComTMM0112");
        AdminPage form = AppHost.host.get(HostType.AdminPage);
        form.lateInit();
        form.setVisible(true);
    }
}
