package com.yobny.opensource.osgi.karafview.view.sample;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

/**
 * A generic label provider for viewers using an observable content provider
 */
public class ObservableColumnLabelProvider extends ColumnLabelProvider {
	public abstract static class CondiditionalTemplate {
		private String templateText;

		public CondiditionalTemplate(String templateText) {
			this.templateText = templateText;
		}

		public abstract boolean isTemplate(EObject element);
	}

	/**
	 * The attribute maps
	 */
	private final IObservableMap[] attributeMaps;

	private List<CondiditionalTemplate> templates = new ArrayList<CondiditionalTemplate>();

	private static StrSubstitutor substr = new StrSubstitutor(new EMFLookUp());

	/**
	 * Change listener to track changes
	 */
	private IMapChangeListener mapChangeListener = new IMapChangeListener() {
		@SuppressWarnings("unchecked")
		public void handleMapChange(MapChangeEvent event) {
			Set affectedElements = event.diff.getChangedKeys();
			LabelProviderChangedEvent newEvent = new LabelProviderChangedEvent(
					ObservableColumnLabelProvider.this, affectedElements
							.toArray());
			fireLabelProviderChanged(newEvent);
		}
	};

	/**
	 * @param attributeMap
	 * @param templateText
	 */
	public ObservableColumnLabelProvider(IObservableMap attributeMap,
			String templateText) {
		this(new IObservableMap[] { attributeMap }, templateText);
	}

	/**
	 * @param attributeMaps
	 * @param templateText
	 */
	public ObservableColumnLabelProvider(IObservableMap[] attributeMaps,
			String templateText) {
		this( attributeMaps, createList(templateText));

	}

	private static List<CondiditionalTemplate> createList(String templateText) {
		List<CondiditionalTemplate> l = new ArrayList<CondiditionalTemplate>();
		l.add(new CondiditionalTemplate(templateText) {

			@Override
			public boolean isTemplate(EObject element) {
				return true;
			}

		});
		return l;
	}

	public ObservableColumnLabelProvider(IObservableMap[] attributeMaps, List<CondiditionalTemplate> templates) {
		this.attributeMaps = attributeMaps;
		for (int i = 0; i < attributeMaps.length; i++) {
			attributeMaps[i].addMapChangeListener(mapChangeListener);
		}
		this.templates = templates;
	}

	public void dispose() {
		for (int i = 0; i < attributeMaps.length; i++) {
			attributeMaps[i].removeMapChangeListener(mapChangeListener);
		}
		super.dispose();
	}

	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		String templateText = null;

		for( CondiditionalTemplate ct: templates ) {
			if( ct.isTemplate((EObject) element) ) {
				templateText = ct.templateText;
				break;
			}
		}

		if (templateText != null && templateText.length() > 0) {
			Object[] elements = new Object[attributeMaps.length];
			for (int i = 0; i < elements.length; i++) {
				elements[i] = attributeMaps[i].get(element);
			}

			((EMFLookUp)substr.getVariableResolver()).elements = elements;
			return substr.replace(templateText);
		}

		return "";
	}

	private static class EMFLookUp extends StrLookup {
		public Object[] elements;

		public EMFLookUp() {

		}


		@Override
		public String lookup(String key) {
			String[] formatParts = key.split(",");
			String[] parts = formatParts[0].split("\\.");

			Object value = elements[Integer.parseInt(parts[0])];

			if( value instanceof EObject && parts.length > 1 ) {
				EClass eClass = ((EObject)value).eClass();
				for( int i = 1; i < parts.length; i++ ) {
					for( EStructuralFeature feature: eClass.getEAllStructuralFeatures() ) {
						if( feature.getName().equals(parts[i]) ) {
							value = ((EObject)value).eGet(feature);
							break;
						}
					}

					if( value instanceof EObject ) {
						eClass = ((EObject)value).eClass();
					} else {
						break;
					}
				}
			}

			if( formatParts.length > 1 ) {
				String formatString = "{0";
				formatString += ","+formatParts[1];

				if( formatParts.length > 2 ) {
					formatString += ","+formatParts[2];
				}
				formatString += "}";
				return MessageFormat.format(formatString, value);
			}
			return value != null ? value.toString() : "";
		}

	}
}