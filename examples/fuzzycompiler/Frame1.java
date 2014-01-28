/*
	This simple extension of the java.awt.Frame class
	contains all the elements necessary to act as the
	main window of an application.
 */
 
 /** 
  * This example entitled FuzzyCompiler takes a simple set of 
  * fuzzy rules and fires them )when a Start Button is pressed)
  * with a large number of inputs. This results in an array 
  * that in effect holds the 'compiled' results of the rule 
  * firings. This could lead to doing inferencing by table lookup
  * where one gets a set of inputs and finds the area in the table
  * in which the inputs lie and interpolates using the 4 
  * surrounding values giving extremely fast results.
  * <p>
  * In this example we have 2 antecedent fuzzy variables and 
  * 1 conclusion fuzzy variable. They are:
  * <pre>
  * Antecedent Fuzzy Variables:
  *
  * temperature 0 100 degrees C
  *    terms: low, medium, high
  *
  * pressure 0 500 kPa
  *    terms: low, medium, high
  *
  * Conclusion Fuzzy Variable:
  *
  * throttle 0 1 units
  *    terms: verylow, low, midlow, medium, midhigh, high 
  *
  * the 9 rules can be written as:
  *
  * if  temperature is low
  * and pressure is low
  * then set throttle to high
  *
  * if  temperature is low
  * and pressure is medium
  * then set throttle to medium
  *
  * if  temperature is low
  * and pressure is high
  * then set throttle to midlow
  *
  * if  temperature is medium
  * and pressure is low
  * then set throttle to midhigh
  *
  * if  temperature is medium
  * and pressure is medium
  * then set throttle to midlow
  *
  * if  temperature is medium
  * and pressure is high
  * then set throttle to low
  *
  * if  temperature is high
  * and pressure is low
  * then set throttle to midlow
  *
  * if  temperature is high
  * and pressure is medium
  * then set throttle to low
  *
  * if  temperature is high
  * and pressure is high
  * then set throttle to verylow
  *</pre>
  * On startup the main routine executes and the fuzzy variables and 
  * rules are defined. There are 2 radio buttons that allow the table to 
  * be created using one of two fuzzy inferencing methods:
  * <p>
  * Mamdani minimum inference relation with max-min composition
  * or
  * Larsen product inference relation with max-min composition
  * <p>
  * When the Start button is pressed the are fired for a set of
  * values for temperature (from 5 to 95 by 5) and pressure
  * (from 10 to 490 by 10). For each pair of values the 9 rules
  * are considered for firing and those that do fire have their 
  * fuzzy value outputs combined (union). This final fuzzy output
  * is defuzzified and the value is stored in a table. After all
  * pairs have been dealt with the table is displayed in the 
  * text area. 
  */
package examples.fuzzycompiler;


import java.awt.*;

import nrc.fuzzy.*;
import java.text.*;

public class Frame1 extends Frame
{
    // some fuzzy Rules that are used in various places
    FuzzyRule lowTempLowPressure = new FuzzyRule();
    FuzzyRule lowTempMediumPressure = new FuzzyRule();
    FuzzyRule lowTempHighPressure = new FuzzyRule();
    FuzzyRule mediumTempLowPressure = new FuzzyRule();
    FuzzyRule mediumTempMediumPressure = new FuzzyRule();
    FuzzyRule mediumTempHighPressure = new FuzzyRule();
    FuzzyRule highTempLowPressure = new FuzzyRule();
    FuzzyRule highTempMediumPressure = new FuzzyRule();
    FuzzyRule highTempHighPressure = new FuzzyRule();
    FuzzyRule fuzzyRules[] = {lowTempLowPressure, lowTempMediumPressure,
		lowTempHighPressure, mediumTempLowPressure,
		mediumTempMediumPressure, mediumTempHighPressure,
		highTempLowPressure, highTempMediumPressure,
		highTempHighPressure };
    FuzzyVariable temperature;
    FuzzyVariable pressure;
    FuzzyVariable throttle;
    
	public Frame1()
	{
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		
		//{{INIT_CONTROLS
		setLayout(null);
		setBackground(new java.awt.Color(123,232,177));
		setSize(800,600);
		setVisible(false);
		add(resultTextArea);
		resultTextArea.setBackground(new java.awt.Color(252,245,133));
		resultTextArea.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
		resultTextArea.setBounds(10,10,780,520);
		MamdaniRadioButton.setCheckboxGroup(Group1);
		MamdaniRadioButton.setState(true);
		MamdaniRadioButton.setLabel("Mamdani Min & Max-Min");
		add(MamdaniRadioButton);
		MamdaniRadioButton.setBounds(10,535,200,30);
		LarsenRadioButton.setCheckboxGroup(Group1);
		LarsenRadioButton.setLabel("Larsen Product & Max-Min");
		add(LarsenRadioButton);
		LarsenRadioButton.setBounds(10,565,200,30);
		minimumCompileOperatorRadioButton.setCheckboxGroup(Group2);
		minimumCompileOperatorRadioButton.setState(true);
		minimumCompileOperatorRadioButton.setLabel("Minimum Antecedent Combine");
		add(minimumCompileOperatorRadioButton);
		minimumCompileOperatorRadioButton.setBounds(210,535,230,30);
		productCompileOperatorRadioButton.setCheckboxGroup(Group2);
		productCompileOperatorRadioButton.setLabel("Product Antecedent Combine");
		add(productCompileOperatorRadioButton);
		productCompileOperatorRadioButton.setBounds(210,565,230,30);
		unionGlobalContributionRadioButton.setCheckboxGroup(Group3);
		unionGlobalContributionRadioButton.setState(true);
		unionGlobalContributionRadioButton.setLabel("Union Global Contribution");
		add(unionGlobalContributionRadioButton);
		unionGlobalContributionRadioButton.setBounds(440,535,200,30);
		sumGlobalContributionRadioButton.setCheckboxGroup(Group3);
		sumGlobalContributionRadioButton.setLabel("Sum Global Contribution");
		add(sumGlobalContributionRadioButton);
		sumGlobalContributionRadioButton.setBounds(440,565,200,30);
		StartButton.setLabel("Start");
		add(StartButton);
		StartButton.setBackground(java.awt.Color.lightGray);
		StartButton.setBounds(650,555,100,30);
		setTitle("Fuzzy Compiler example");
		//}}
		
		//{{INIT_MENUS
		menu1.setLabel("File");
		menu1.add(miExit);
		miExit.setLabel("Exit");
		mainMenuBar.add(menu1);
		menu3.setLabel("Help");
		menu3.add(miAbout);
		miAbout.setLabel("About..");
		mainMenuBar.add(menu3);
		mainMenuBar.setHelpMenu(menu3);
		//$$ mainMenuBar.move(0,420);
		setMenuBar(mainMenuBar);
		//}}
		
		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		miAbout.addActionListener(lSymAction);
		miExit.addActionListener(lSymAction);
		StartButton.addActionListener(lSymAction);
		//}}
	}
	
	public Frame1(String title)
	{
		this();
		setTitle(title);
	}
	
    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
    public void setVisible(boolean b)
	{
		if(b)
		{
			setLocation(50, 50);
		}	
		super.setVisible(b);
	}
	
	static public void main(String args[])
	{   Frame1 f = new Frame1();
		f.setVisible(true);
		f.init();
	}
	
	public void init()
    {
		//SFunction.setDefaultNumberOfPoints(5);
		//ZFunction.setDefaultNumberOfPoints(5);
		try
		{   // define temperature variable
		    temperature = new FuzzyVariable("temperature", 0.0, 100.0, "Degrees C");
		    temperature.addTerm("low", new PIFuzzySet(20, 20));
		    temperature.addTerm("medium", new PIFuzzySet(50, 20));
		    temperature.addTerm("high", new PIFuzzySet(80, 20));
		    // define pressure variable
		    pressure = new FuzzyVariable("pressure", 0.0, 500.0, "kPa");
		    pressure.addTerm("low", new PIFuzzySet(100, 100));
		    pressure.addTerm("medium", new PIFuzzySet(250, 100));
		    pressure.addTerm("high", new PIFuzzySet(400, 100));
		    // define the throttle fuzzy variable
		    throttle = new FuzzyVariable("throttle", 0.0, 1.0, "units");
		    throttle.addTerm("verylow", new PIFuzzySet(.1, .1));
		    throttle.addTerm("low", new PIFuzzySet(.25, .15));
		    throttle.addTerm("midlow", new PIFuzzySet(.4, .1));
		    throttle.addTerm("medium", new PIFuzzySet(.55, .15));
		    throttle.addTerm("midhigh", new PIFuzzySet(.75, .1));
		    throttle.addTerm("high", new PIFuzzySet(.9, .1));
		    // define the 9 rules
		    lowTempLowPressure.addAntecedent(new FuzzyValue(temperature,"low"));
		    lowTempLowPressure.addAntecedent(new FuzzyValue(pressure,"low"));
		    lowTempLowPressure.addConclusion(new FuzzyValue(throttle,"high"));
		    lowTempMediumPressure.addAntecedent(new FuzzyValue(temperature,"low"));
		    lowTempMediumPressure.addAntecedent(new FuzzyValue(pressure,"medium"));
		    lowTempMediumPressure.addConclusion(new FuzzyValue(throttle,"medium"));
		    lowTempHighPressure.addAntecedent(new FuzzyValue(temperature,"low"));
		    lowTempHighPressure.addAntecedent(new FuzzyValue(pressure,"high"));
		    lowTempHighPressure.addConclusion(new FuzzyValue(throttle,"midlow"));
		    mediumTempLowPressure.addAntecedent(new FuzzyValue(temperature,"medium"));
		    mediumTempLowPressure.addAntecedent(new FuzzyValue(pressure,"low"));
		    mediumTempLowPressure.addConclusion(new FuzzyValue(throttle,"midhigh"));
		    mediumTempMediumPressure.addAntecedent(new FuzzyValue(temperature,"medium"));
		    mediumTempMediumPressure.addAntecedent(new FuzzyValue(pressure,"medium"));
		    mediumTempMediumPressure.addConclusion(new FuzzyValue(throttle,"midlow"));
		    mediumTempHighPressure.addAntecedent(new FuzzyValue(temperature,"medium"));
		    mediumTempHighPressure.addAntecedent(new FuzzyValue(pressure,"high"));
		    mediumTempHighPressure.addConclusion(new FuzzyValue(throttle,"low"));
		    highTempLowPressure.addAntecedent(new FuzzyValue(temperature,"high"));
		    highTempLowPressure.addAntecedent(new FuzzyValue(pressure,"low"));
		    highTempLowPressure.addConclusion(new FuzzyValue(throttle,"midlow"));
		    highTempMediumPressure.addAntecedent(new FuzzyValue(temperature,"high"));
		    highTempMediumPressure.addAntecedent(new FuzzyValue(pressure,"medium"));
		    highTempMediumPressure.addConclusion(new FuzzyValue(throttle,"low"));
		    highTempHighPressure.addAntecedent(new FuzzyValue(temperature,"high"));
		    highTempHighPressure.addAntecedent(new FuzzyValue(pressure,"high"));
		    highTempHighPressure.addConclusion(new FuzzyValue(throttle,"verylow"));
		}
		catch (FuzzyException e)
		{
		}
	}
	
	public void addNotify()
	{
		// Record the size of the window prior to calling parents addNotify.
		Dimension d = getSize();
		
		super.addNotify();
	
		if (fComponentsAdjusted)
			return;
	
		// Adjust components according to the insets
		setSize(insets().left + insets().right + d.width, insets().top + insets().bottom + d.height);
		Component components[] = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(insets().left, insets().top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}
	
	// Used for addNotify check.
	boolean fComponentsAdjusted = false;
	
	//{{DECLARE_CONTROLS
	java.awt.TextArea resultTextArea = new java.awt.TextArea();
	java.awt.Checkbox MamdaniRadioButton = new java.awt.Checkbox();
	java.awt.CheckboxGroup Group1 = new java.awt.CheckboxGroup();
	java.awt.Checkbox LarsenRadioButton = new java.awt.Checkbox();
	java.awt.Checkbox minimumCompileOperatorRadioButton = new java.awt.Checkbox();
	java.awt.CheckboxGroup Group2 = new java.awt.CheckboxGroup();
	java.awt.Checkbox productCompileOperatorRadioButton = new java.awt.Checkbox();
	java.awt.Checkbox unionGlobalContributionRadioButton = new java.awt.Checkbox();
	java.awt.CheckboxGroup Group3 = new java.awt.CheckboxGroup();
	java.awt.Checkbox sumGlobalContributionRadioButton = new java.awt.Checkbox();
	java.awt.Button StartButton = new java.awt.Button();
	//}}
	
	//{{DECLARE_MENUS
	java.awt.MenuBar mainMenuBar = new java.awt.MenuBar();
	java.awt.Menu menu1 = new java.awt.Menu();
	java.awt.MenuItem miExit = new java.awt.MenuItem();
	java.awt.Menu menu3 = new java.awt.Menu();
	java.awt.MenuItem miAbout = new java.awt.MenuItem();
	//}}
	
	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowDeactivated(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == Frame1.this)
				Frame1_WindowDeactivated(event);
		}

		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == Frame1.this)
				Frame1_WindowClosing(event);
		}
	}
	
	void Frame1_WindowClosing(java.awt.event.WindowEvent event)
	{
		setVisible(false);	// hide the Frame
		dispose();			// free the system resources
		System.exit(0);		// close the application
	}
	
	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == miAbout)
				miAbout_Action(event);
			else if (object == miExit)
				miExit_Action(event);
			else if (object == StartButton)
				StartButton_ActionPerformed(event);
		}
	}
	
	void miAbout_Action(java.awt.event.ActionEvent event)
	{
		//{{CONNECTION
		// Action from About Create and show as modal
		(new AboutDialog(this, true)).setVisible(true);
		//}}
	}
	
	void miExit_Action(java.awt.event.ActionEvent event)
	{
		//{{CONNECTION
		// Action from Exit Create and show as modal
		(new QuitDialog(this, true)).setVisible(true);
		//}}
	}
	
    private static FuzzyRuleExecutor fre1 = new MamdaniMinMaxMinRuleExecutor();
    private static FuzzyRuleExecutor fre2 = new LarsenProductMaxMinRuleExecutor();
	private static AntecedentCombineOperator frco1 = new MinimumAntecedentCombineOperator();
	private static AntecedentCombineOperator frco2 = new ProductAntecedentCombineOperator();

	void StartButton_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
		// Create the table of values determined by the firing of the 
		// Fuzzy Rules with varying inputs
		int i, j, k;
		double results[][] = new double[19][49];
		FuzzyValue globalOutput = null;
		FuzzyValueVector fvv;
		FuzzyRuleExecutor fre;
		AntecedentCombineOperator frco;

		// make sure that the rule executor is set according to the radio buttons
		// NOTE: each rule will have its own rule executor -- setRuleExecutor clones the FuzzyRuleExecutor
		if (MamdaniRadioButton.getState() == true)
		   fre = fre1;
		else
		   fre = fre2;
		for (i=0; i<fuzzyRules.length; i++)
		   fuzzyRules[i].setRuleExecutor(fre);
		// make sure that the antecedent/input pair values combining operator
		// is set according to the radio buttons
		if (minimumCompileOperatorRadioButton.getState() == true)
		   frco = frco1;
		else
		   frco = frco2;
		for (i=0; i<fuzzyRules.length; i++)
		   fuzzyRules[i].setAntecedentCombineOperator(frco);
           
        resultTextArea.setText("Temp     5    10    15    20    25    30    35    40    45    50    55    60    65    70    75    80    85    90    95\nPressure\n");
        
        // fire the rules for values of temp from 5 to 95 (increments of 5)
        // and values of pressure from 10 to 490, storing the results of the rule firings
        // (i.e. the de-fuzzified result of the combination of all rules) in a
        // 2D array.
		for (i=0; i<19; i++)
		{   for (j=0; j<49; j++)
		    {   try
		        {
    		        double temp = i*5+5;
    		        double press = j*10+10;
    		        globalOutput = null;
    		        FuzzyValue tempFv = new FuzzyValue(this.temperature,new PIFuzzySet(temp, 0.5));
    		        FuzzyValue pressFv = new FuzzyValue(this.pressure,new PIFuzzySet(press, 0.5));
    		        FuzzyValue fv;
    		        // add inputs to rules and fire them and do global contribution
					for (k=0; k<fuzzyRules.length; k++)
					{
						this.fuzzyRules[k].removeAllInputs();
						this.fuzzyRules[k].addInput(tempFv);
						this.fuzzyRules[k].addInput(pressFv);
						if (this.fuzzyRules[k].testRuleMatching())
						{
							fvv = fuzzyRules[k].execute();
							fv = fvv.fuzzyValueAt(0);
							if (globalOutput == null)
							   globalOutput = fv;
							else
							   globalOutput = (unionGlobalContributionRadioButton.getState()) ? 
											  globalOutput.fuzzyUnion(fv) : globalOutput.fuzzySum(fv);
						}
					}
    		        
    		        results[i][j] = globalOutput.momentDefuzzify();
		        }
		        catch (FuzzyException e)
		        { System.out.println(e);
		          System.out.println("At "+i+","+j+" .../n"+ globalOutput);
		        }
		    }
		}
		
		// print out the results in the text area
        NumberFormat nf = NumberFormat.getInstance();
        NumberFormat nfint = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nfint.setMaximumFractionDigits(0);
        nfint.setMinimumFractionDigits(0);
        nfint.setMinimumIntegerDigits(3);
        StringBuffer sb = new StringBuffer(1000);
		for (j=0; j<49; j++)
		{   sb.append(nfint.format((j+1)*10) + "   ");
		    for (i=0; i<19; i++)
		        sb.append(nf.format(results[i][j])+"  ");
		    sb.append("\n");
		}
		resultTextArea.append(sb.toString());
		resultTextArea.setCaretPosition(0);
	}

	void Frame1_WindowDeactivated(java.awt.event.WindowEvent event)
	{
		// to do: code goes here.
	}
}

