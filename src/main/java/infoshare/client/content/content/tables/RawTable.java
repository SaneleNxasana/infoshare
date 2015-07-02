package infoshare.client.content.content.tables;

import com.vaadin.ui.Table;
import infoshare.client.content.MainLayout;
import infoshare.domain.Content;
import infoshare.services.Content.ContentService;
import infoshare.services.Content.Impl.ContentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by hashcode on 2015/06/24.
 */
public class RawTable extends Table {

    @Autowired
    private ContentService contentService = new ContentServiceImp();

    private final MainLayout main;

    public RawTable(MainLayout mainApp){
        this.main = mainApp;
        setSizeFull();
        addContainerProperty("Title",String.class,null);
        addContainerProperty("Category",String.class,null);
        addContainerProperty("Creator",String.class,null);
        addContainerProperty("Source",String.class,null);
        addContainerProperty("Date Created",Date.class,null);
        List<Content> contents = contentService.findAll();

        for (Content content: contents){
            if(content.getContentType().equalsIgnoreCase("Raw")) {
                addItem(new Object[]{
                        content.getTitle(),
                        content.getCategory(),
                        content.getCreator(),
                        content.getSource(),
                        content.getDateCreated()
                }, content.getId());
            }
        }

         setNullSelectionAllowed(false);
         setSelectable(true);
         setImmediate(true);
    }

}
