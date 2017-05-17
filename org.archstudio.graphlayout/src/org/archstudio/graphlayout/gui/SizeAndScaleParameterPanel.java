package org.archstudio.graphlayout.gui;

import org.archstudio.graphlayout.GraphLayoutParameters;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SizeAndScaleParameterPanel implements IGraphLayoutParameterPanel {

	protected Text tComponentWidth;
	protected Text tComponentHeight;
	
	protected Text tCoalAgentWidth;
	protected Text tCoalAgentHeight;
	protected Text tFunAgentWidth;
	protected Text tFunAgentHeight;
	protected Text tEncapsulatedUnitWidth;
	protected Text tEncapsulatedUnitHeight;
	
	
	protected Text tConnectorWidth;
	protected Text tConnectorHeight;

	protected Text tScale;

	@Override
	public void createPanel(Composite parent) {
		Group gPanel = new Group(parent, SWT.NONE);
		gPanel.setText("Size and Scale");
		gPanel.setLayout(new FillLayout());

		Composite cPanel = new Composite(gPanel, SWT.NONE);
		cPanel.setLayout(new GridLayout(2, false));

		Composite cSize = new Composite(cPanel, SWT.NONE);
		cSize.setLayout(new GridLayout(3, false));

		Label lDummy1 = new Label(cSize, SWT.NONE);
		lDummy1.setText("");

		Label lWidth = new Label(cSize, SWT.NONE);
		lWidth.setText("Rel Width");

		Label lHeight = new Label(cSize, SWT.NONE);
		lHeight.setText("Rel Height");

		Label lComponents = new Label(cSize, SWT.NONE);
		lComponents.setText("Components:");

		tComponentWidth = new Text(cSize, SWT.BORDER);
		tComponentWidth.setLayoutData(new GridData(40, SWT.DEFAULT));
		tComponentWidth.setText("1.0");

		tComponentHeight = new Text(cSize, SWT.BORDER);
		tComponentHeight.setLayoutData(new GridData(40, SWT.DEFAULT));
		tComponentHeight.setText("1.0");

		
		Label lCoalAgent = new Label(cSize, SWT.NONE);
		lCoalAgent.setText("CoalAgent:");

		tCoalAgentWidth = new Text(cSize, SWT.BORDER);
		tCoalAgentWidth.setLayoutData(new GridData(40, SWT.DEFAULT));
		tCoalAgentWidth.setText("1.0");

		tCoalAgentHeight = new Text(cSize, SWT.BORDER);
		tCoalAgentHeight.setLayoutData(new GridData(40, SWT.DEFAULT));
		tCoalAgentHeight.setText("1.0");
		
		
		Label lFunAgent = new Label(cSize, SWT.NONE);
		lFunAgent.setText("FunAgent:");

		tFunAgentWidth = new Text(cSize, SWT.BORDER);
		tFunAgentWidth.setLayoutData(new GridData(40, SWT.DEFAULT));
		tFunAgentWidth.setText("1.0");

		tFunAgentHeight = new Text(cSize, SWT.BORDER);
		tFunAgentHeight.setLayoutData(new GridData(40, SWT.DEFAULT));
		tFunAgentHeight.setText("1.0");
		
		
		Label lEncapsulatedUnit = new Label(cSize, SWT.NONE);
		lEncapsulatedUnit.setText("EncapsulatedUnit:");

		tEncapsulatedUnitWidth = new Text(cSize, SWT.BORDER);
		tEncapsulatedUnitWidth.setLayoutData(new GridData(40, SWT.DEFAULT));
		tEncapsulatedUnitWidth.setText("1.0");

		tEncapsulatedUnitHeight = new Text(cSize, SWT.BORDER);
		tEncapsulatedUnitHeight.setLayoutData(new GridData(40, SWT.DEFAULT));
		tEncapsulatedUnitHeight.setText("1.0");
		
		
		Label lConnectors = new Label(cSize, SWT.NONE);
		lConnectors.setText("Connectors:");

		tConnectorWidth = new Text(cSize, SWT.BORDER);
		tConnectorWidth.setLayoutData(new GridData(40, SWT.DEFAULT));
		tConnectorWidth.setText("1.0");

		tConnectorHeight = new Text(cSize, SWT.BORDER);
		tConnectorHeight.setLayoutData(new GridData(40, SWT.DEFAULT));
		tConnectorHeight.setText("1.0");

		Composite cScale = new Composite(cPanel, SWT.NONE);
		cScale.setLayout(new GridLayout(1, false));

		Label lScale = new Label(cScale, SWT.NONE);
		lScale.setText("Scale:");

		tScale = new Text(cScale, SWT.BORDER);
		tScale.setLayoutData(new GridData(45, SWT.DEFAULT));
		tScale.setText("100.0");
	}

	@Override
	public void loadParameters(GraphLayoutParameters params) {
		tComponentWidth.setText(Double.toString(params.getRelativeComponentWidth()));
		tComponentHeight.setText(Double.toString(params.getRelativeComponentHeight()));
		
		
		tCoalAgentWidth.setText(Double.toString(params.getRelativeCoalAgentWidth()));
		tCoalAgentHeight.setText(Double.toString(params.getRelativeCoalAgentHeight()));
		
		tFunAgentWidth.setText(Double.toString(params.getRelativeFunAgentWidth()));
		tFunAgentHeight.setText(Double.toString(params.getRelativeFunAgentHeight()));
		tEncapsulatedUnitWidth.setText(Double.toString(params.getRelativeEncapsulatedUnitWidth()));
		tEncapsulatedUnitHeight.setText(Double.toString(params.getRelativeEncapsulatedUnitHeight()));
		
		tConnectorWidth.setText(Double.toString(params.getRelativeConnectorWidth()));
		tConnectorHeight.setText(Double.toString(params.getRelativeConnectorHeight()));
		tScale.setText(Double.toString(params.getScale()));
	}

	@Override
	public void storeParameters(GraphLayoutParameters params) throws DataValidationException {
		double componentWidth = 0.0;
		try {
			componentWidth = Double.parseDouble(tComponentWidth.getText());
			if (componentWidth < 0) {
				throw new DataValidationException("Component width must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid component width");
		}

		double componentHeight = 0.0;
		try {
			componentHeight = Double.parseDouble(tComponentHeight.getText());
			if (componentHeight < 0) {
				throw new DataValidationException("Component height must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid component height");
		}

		
		double coalAgentWidth = 0.0;
		try {
			coalAgentWidth = Double.parseDouble(tCoalAgentWidth.getText());
			if (coalAgentWidth < 0) {
				throw new DataValidationException("CoalAgent width must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid coalAgent width");
		}

		double coalAgentHeight = 0.0;
		try {
			coalAgentHeight = Double.parseDouble(tCoalAgentHeight.getText());
			if (coalAgentHeight < 0) {
				throw new DataValidationException("coalAgent height must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid coalAgent height");
		}
		
		
		
		double funAgentWidth = 0.0;
		try {
			funAgentWidth = Double.parseDouble(tFunAgentWidth.getText());
			if (funAgentWidth < 0) {
				throw new DataValidationException("FunAgent width must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid funAgent width");
		}

		double funAgentHeight = 0.0;
		try {
			funAgentHeight = Double.parseDouble(tFunAgentHeight.getText());
			if (funAgentHeight < 0) {
				throw new DataValidationException("funAgent height must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid funAgent height");
		}
		
		
		double encapsulatedUnitWidth = 0.0;
		try {
			encapsulatedUnitWidth = Double.parseDouble(tEncapsulatedUnitWidth.getText());
			if (encapsulatedUnitWidth < 0) {
				throw new DataValidationException("EncapsulatedUnit width must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid encapsulatedUnit width");
		}

		double encapsulatedUnitHeight = 0.0;
		try {
			encapsulatedUnitHeight = Double.parseDouble(tEncapsulatedUnitHeight.getText());
			if (encapsulatedUnitHeight < 0) {
				throw new DataValidationException("encapsulatedUnit height must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid encapsulatedUnit height");
		}
		
		
		double connectorWidth = 0.0;
		try {
			connectorWidth = Double.parseDouble(tConnectorWidth.getText());
			if (connectorWidth < 0) {
				throw new DataValidationException("Connector width must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid connector width");
		}

		double connectorHeight = 0.0;
		try {
			connectorHeight = Double.parseDouble(tConnectorHeight.getText());
			if (connectorHeight < 0) {
				throw new DataValidationException("Connector height must be positive");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid connector height");
		}

		double scale = 0.0;
		try {
			scale = Double.parseDouble(tScale.getText());
			if (scale <= 0) {
				throw new DataValidationException("Scale must be positive and greater than zero");
			}
		}
		catch (NumberFormatException nfe) {
			throw new DataValidationException("Invalid scale");
		}

		params.setRelativeComponentWidth(componentWidth);
		params.setRelativeComponentHeight(componentHeight);
		
		params.setRelativeCoalAgentWidth(coalAgentWidth);
		params.setRelativeCoalAgentHeight(coalAgentHeight);
		
		params.setRelativeFunAgentWidth(funAgentWidth);
		params.setRelativeFunAgentHeight(funAgentHeight);
		params.setRelativeEncapsulatedUnitWidth(encapsulatedUnitWidth);
		params.setRelativeEncapsulatedUnitHeight(encapsulatedUnitHeight);
		
		params.setRelativeConnectorWidth(connectorWidth);
		params.setRelativeConnectorHeight(connectorHeight);
		params.setScale(scale);
	}
}
