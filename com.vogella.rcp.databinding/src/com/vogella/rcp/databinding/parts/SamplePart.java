package com.vogella.rcp.databinding.parts;

import javax.annotation.PostConstruct;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SamplePart {

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void createPartControl(Composite parent) {
		// create the Person model with programming skills
		Person person = new Person();
		person.setName("John");
		person.setProgrammingSkills(new String[] { "Java", "JavaScript", "Groovy" });
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(parent);
		Label programmingSkillsLabel = new Label(parent, SWT.NONE);
		programmingSkillsLabel.setText("Programming Skills");
		GridDataFactory.swtDefaults().applyTo(programmingSkillsLabel);
		Text programmingSkillsText = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(programmingSkillsText);
		// Do the actual binding and conversion
		DataBindingContext dbc = new DataBindingContext();

		// define converters
		IConverter convertToStringArray =
				IConverter.create(String.class, String[].class, (o1) -> ((String) o1).split(","));
		IConverter convertToString =
				IConverter.create(String[].class, String.class, (o1) -> convert((String[])o1));;

		// create the observables, which should be bound
		IObservableValue<Text> programmingSkillsTarget = WidgetProperties.text(SWT.Modify).observe(programmingSkillsText);
		IObservableValue<Person> programmingSkillsModel = BeanProperties.value("programmingSkills").observe(person);

		// bind observables together with the appropriate UpdateValueStrategies
		dbc.bindValue(programmingSkillsTarget, programmingSkillsModel,
				UpdateValueStrategy.create(convertToStringArray),
				UpdateValueStrategy.create(convertToString));

		// button to check the data model
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("Show data model");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (String string : person.getProgrammingSkills()) {
					System.out.println(string);

				}
			}
		});

	}

	private static String convert(String[] fromObject) {
		String[] stringArray = fromObject;
		StringBuilder sb = new StringBuilder();
		int length = stringArray.length;
		for (int i = 0; i < length; i++) {
			String string = stringArray[i];
			sb.append(string);
			if (i + 1 < length) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}