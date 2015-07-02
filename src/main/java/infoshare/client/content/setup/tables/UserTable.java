package infoshare.client.content.setup.tables;

import com.vaadin.ui.Table;
import infoshare.client.content.MainLayout;
import infoshare.domain.User;
import infoshare.services.users.Impl.UserServiceImpl;
import infoshare.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by hashcode on 2015/06/24.
 */
public class UserTable extends Table {

    private final MainLayout main;
    @Autowired
    private UserService userService = new UserServiceImpl();

    public UserTable(MainLayout main) {
        this.main = main;
        setSizeFull();

        addContainerProperty("Username", String.class, null);
        addContainerProperty("First Name", String.class, null);
        addContainerProperty("Last Name", String.class, null);
        addContainerProperty("Enabled", Boolean.class, null);
        List<User> users= userService.findAll(); // From REST API
        for (User user : users) {
            addItem(new Object[]{
                    user.getUsername(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.isEnable()
                    }, user.getId());
        }
//         Allow selecting items from the table.
        setNullSelectionAllowed(false);
//
        setSelectable(true);
        // Send changes in selection immediately to server.
        setImmediate(true);
    }


}