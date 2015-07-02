package infoshare.client.content.content.views;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import infoshare.client.content.MainLayout;
import infoshare.client.content.content.ContentMenu;
import infoshare.client.content.content.forms.RawAndEditForm;
import infoshare.client.content.content.models.RawAndEditModel;
import infoshare.client.content.content.tables.RawTable;
import infoshare.domain.Category;
import infoshare.domain.Content;
import infoshare.domain.ContentType;
import infoshare.services.Content.ContentService;
import infoshare.services.Content.Impl.ContentServiceImp;
import infoshare.services.ContentType.ContentTypeService;
import infoshare.services.ContentType.Impl.ContentTypeServiceImpl;
import infoshare.services.category.CategoryService;
import infoshare.services.category.Impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hashcode on 2015/06/24.
 */
public class RawView extends VerticalLayout implements
        Button.ClickListener,Property.ValueChangeListener{
    @Autowired
    private ContentService contentService = new ContentServiceImp();
    private ContentTypeService contentTypeService = new ContentTypeServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    private final MainLayout main;
    private final RawTable table;
    private final RawAndEditForm form;

    private Window popUp ;
    private Button editBtn;
    public RawView( MainLayout mainApp) {
        this.main = mainApp;
        this.table = new RawTable(main);
        this.form = new RawAndEditForm();
        this.popUp = modelWindow();
        editBtn = new Button("EDIT");

        setSizeFull();
        setSpacing(true);
        addComponent(editBtn);
        addComponent(table);
        addListeners();
        editBtn.setVisible(false);
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        final Button source = clickEvent.getButton();
        if(source==editBtn){
            EditButton();
        }else if (source ==form.popUpUpdateBtn){
            saveEditedForm(form.binder);
        }else if (source ==form.popUpCancelBtn){
            popUp.setModal(false);
            UI.getCurrent().removeWindow(popUp);
            table.setValue(null);
            getHome();
        }
    }
    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        final Property property = event.getProperty();
        if (property == table) {
            editBtn.setVisible(true);
        }
    }
    private Window modelWindow(){
        final Window popup = new Window("EDIT RAW CONTENT");
        popup.setWidth(80.0f, Unit.PERCENTAGE);
        popup.setContent(form);
        return popup;
    }
    private void loadComboBoxs(){
          for(Category category:categoryService.findAll()){
                if(category!= null)
                form.popUpCategoryCmb.addItem(category.getName());
            }

        for(ContentType category:contentTypeService.findAll()){
                if(category!= null)
                form.popUpContentTypeCmb.addItem(category.getContentTyeName());
            }

    }
    private void getHome() {
        main.content.setSecondComponent(new ContentMenu(main, "LANDING"));
    }
    private void EditButton(){
        loadComboBoxs();
        try {
            final Content content = contentService.find(table.getValue().toString());
            final RawAndEditModel bean = getModel(content);
            form.binder.setItemDataSource(new BeanItem<>(bean));
            UI.getCurrent().addWindow(popUp);
            popUp.setModal(true);
        }catch (Exception e){
            Notification.show("Select the row you wanna edit",
                    Notification.Type.HUMANIZED_MESSAGE);
        }
    }
    private void saveEditedForm(FieldGroup binder) {
        try {
            binder.commit();
            contentService.merge(updateEntity(binder));
            popUp.setModal(false);
            table.setValue(null);
            UI.getCurrent().removeWindow(popUp);
            getHome();
            Notification.show("Record EDITED!", Notification.Type.HUMANIZED_MESSAGE);
        } catch (FieldGroup.CommitException e) {
            Notification.show("Fill in all Fields!!", Notification.Type.HUMANIZED_MESSAGE);
            getHome();
        }
    }
    private Content updateEntity(FieldGroup binder){
        final RawAndEditModel bean = ((BeanItem<RawAndEditModel>) binder.getItemDataSource()).getBean();
        final Content content = new Content.Builder(bean.getTitle())
                .dateCreated(bean.getDateCreated())
                .creator(bean.getCreator())
                .source(bean.getSource())
                .category(bean.getCategory())
                .content(bean.getContent())
                .contentType(bean.getContentType())
                .id(bean.getId())
                .build();
        return content;
    }
    private RawAndEditModel getModel(Content val) {
        final RawAndEditModel model = new RawAndEditModel();
        final Content content = contentService.find(val.getId());
        model.setTitle(content.getTitle());
        model.setId(content.getId());
        model.setCategory(content.getCategory());
        model.setCreator(content.getCreator());
        model.setContent(content.getContent());
        model.setContentType(content.getContentType());
        model.setSource(content.getSource());
        return model;
    }
    public void addListeners(){
        form.popUpUpdateBtn.addClickListener((Button.ClickListener)this);
        form.popUpCancelBtn.addClickListener((Button.ClickListener) this);
        table.addValueChangeListener((Property.ValueChangeListener) this);
        editBtn.addClickListener((Button.ClickListener) this);
    }

}
