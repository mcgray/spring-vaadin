package ua.com.mcgray.springvaadin;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ua.com.mcgray.springvaadin.domain.Person;

import com.vaadin.Application;
import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Configurable(preConstruction = true)
public class MyVaadinApplication extends Application {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void init() {
		Window w = new Window("JPA app skeleton");

		// Simple crud for Person entities, REFACTOR based on your own needs

		VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
		w.setContent(verticalSplitPanel);
		final Table table = new Table("Persons", JPAContainerFactory.make(Person.class, entityManagerFactory.createEntityManager()));
		table.setSizeFull();
		table.setVisibleColumns(new Object[] { "firstName", "lastName", "boss" });
		verticalSplitPanel.setSplitPosition(30);
		verticalSplitPanel.setFirstComponent(table);

		final Form form = new BeanValidationForm<Person>(Person.class);
		form.setFormFieldFactory(DefaultFieldFactory.get());
		form.setVisibleItemProperties(new Object[] { "firstName", "lastName", "phoneNumber", "street", "zipCode", "city" });
		form.setWriteThrough(false);
		// form.getField("boss").setReadOnly(true);
		form.getFooter().addComponent(new Button("Save", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				form.commit();
			}
		}));
		form.getFooter().setMargin(true);
		form.setVisible(false);
		form.getLayout().setMargin(true);
		verticalSplitPanel.setSecondComponent(form);

		table.setSelectable(true);
		table.setImmediate(true);
		table.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() == null) {
					form.setValue(null);
					form.setVisible(false);
				} else {
					form.setValue(table.getItem(event.getProperty().getValue()));
					form.setVisible(true);
				}
			}
		});

		table.addActionHandler(new Handler() {
			private final Action NEW = new Action("New");
			private final Action REMOVE = new Action("Remove");
			private final Action[] actions = new Action[] { NEW, REMOVE };

			@Override
			public Action[] getActions(Object target, Object sender) {
				return actions;
			}

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (action == NEW) {
					Object newItemId = table.addItem();
					table.setValue(newItemId);
				} else if (action == REMOVE) {
					table.removeItem(target);
				}

			}
		});

		setMainWindow(w);
	}

}
