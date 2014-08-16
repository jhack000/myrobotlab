package org.myrobotlab.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.myrobotlab.logging.LoggerFactory;
import org.myrobotlab.service.GUIService;
import org.myrobotlab.service._TemplateService;
import org.slf4j.Logger;

/**
 * based on _TemplateServiceGUI
 */
/**
 *
 * @author LunDev (github), Ma. Vo. (MyRobotlab)
 */
public class ServoOrchestratorGUI extends ServiceGUI implements ActionListener,
		ItemListener, ListSelectionListener {

	static final long serialVersionUID = 1L;
	public final static Logger log = LoggerFactory
			.getLogger(ServoOrchestratorGUI.class.getCanonicalName());

	public int sizex;
	public int sizey;
	
	public ServoOrchestratorGUI_middlemiddle_main middlemiddle_ref;

	public JTextField middleright_name_textfield;
	public JTextField middleright_min_textfield;
	public JTextField middleright_max_textfield;
	public JTextField middleright_startvalue_textfield;
	JButton middleright_update_button;
	public JList middleright_arduino_list;
	public JList middleright_pin_list;
	public JButton middleright_attach_button;

	JButton bottommiddleleft_update_button;

	public JTextField bottommiddlerighttop_textfield_1;
	public JTextField bottommiddlerighttop_textfield_2;
	public JTextField bottommiddlerighttop_textfield_3;
	
	public JButton bottommiddlerightbottom_button_1;
	public JButton bottommiddlerightbottom_button_2;
	public JButton bottommiddlerightbottom_button_3;
	public JButton bottommiddlerightbottom_button_4;
	public JButton bottommiddlerightbottom_button_5;
	public JButton bottommiddlerightbottom_button_6;
	public JButton bottommiddlerightbottom_button_7;
	public JButton bottommiddlerightbottom_button_8;

	JCheckBox bottomright_click_checkbox;

	public ServoOrchestratorGUI(final String boundServiceName,
			final GUIService myService, final JTabbedPane tabs) {

		super(boundServiceName, myService, tabs);
		myService.send(boundServiceName, "setsoguireference",
				ServoOrchestratorGUI.this);
	}

	public void init() {
		myService.send(boundServiceName, "setmiddlemiddlesize");

		middlemiddle_ref = new ServoOrchestratorGUI_middlemiddle_main(
				ServoOrchestratorGUI.this);

		// --------------------------------------------------
		JButton addPanelButton = new JButton("ADD");
		addPanelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				middlemiddle_ref.externalcall_addPanel();
			}
		});

		// --------------------------------------------------
		JPanel top = new JPanel();

		// only for testing
		// TODO - (re-)move
		top.add(addPanelButton, BorderLayout.CENTER);

		JPanel middlebottom = new JPanel();

		JPanel middle = new JPanel();

		JPanel middleright = new JPanel();
		middleright.setLayout(new BoxLayout(middleright, BoxLayout.Y_AXIS));

		JLabel middleright_name_label = new JLabel("CHANNEL-NAME:");
		middleright.add(middleright_name_label);

		middleright_name_textfield = new JTextField("Name of the Channel");
		middleright.add(middleright_name_textfield);

		// TODO - add type list (change(-r))
		JPanel middleright_min_panel = new JPanel();
		
		JLabel middleright_min_label = new JLabel("MIN:");
		middleright_min_panel.add(middleright_min_label);

		middleright_min_textfield = new JTextField("min");
		middleright_min_textfield.setColumns(5);
		middleright_min_panel.add(middleright_min_textfield);

		middleright.add(middleright_min_panel);
		
		JPanel middleright_max_panel = new JPanel();
		
		JLabel middleright_max_label = new JLabel("MAX:");
		middleright_max_panel.add(middleright_max_label);

		middleright_max_textfield = new JTextField("max");
		middleright_max_textfield.setColumns(5);
		middleright_max_panel.add(middleright_max_textfield);

		middleright.add(middleright_max_panel);
		
		JPanel middleright_startvalue_panel = new JPanel();
		
		JLabel middleright_startvalue_label = new JLabel("START-VALUE:");
		middleright_startvalue_panel.add(middleright_startvalue_label);

		middleright_startvalue_textfield = new JTextField("startvalue");
		middleright_startvalue_textfield.setColumns(5);
		middleright_startvalue_panel.add(middleright_startvalue_textfield);

		middleright.add(middleright_startvalue_panel);
		
		middleright_update_button = new JButton("UPDATE");
		middleright.add(middleright_update_button);
		middleright_update_button.addActionListener(this);
		
		middleright_arduino_list = new JList();
		myService.send(boundServiceName, "set_middleright_arduino_list_items");
		middleright_arduino_list.setVisibleRowCount(3);
		middleright_arduino_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		middleright_arduino_list.addListSelectionListener(this);
		JScrollPane middleright_arduino_list_scrollpane = new JScrollPane(middleright_arduino_list);
		middleright_arduino_list_scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		middleright_arduino_list_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		middleright.add(middleright_arduino_list_scrollpane);
		
		middleright_pin_list = new JList();
		String[] middleright_pin_list_items = new String[53];
		for (int i = 0; i < 53; i++) {
			if (i == 0) {
				middleright_pin_list_items[i] = "          ";
			}
			middleright_pin_list_items[i] = (i+1)+"";
		}
		middleright_pin_list.setListData(middleright_pin_list_items);
		middleright_pin_list.setVisibleRowCount(3);
		middleright_pin_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		middleright_pin_list.addListSelectionListener(this);
		JScrollPane middleright_pin_list_scrollpane = new JScrollPane(middleright_pin_list);
		middleright_pin_list_scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		middleright_pin_list_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		middleright.add(middleright_pin_list_scrollpane);

		middleright_attach_button = new JButton("Attach");
		middleright.add(middleright_attach_button);
		middleright_attach_button.addActionListener(this);
		
		JPanel middlemiddlemiddleleft = new JPanel();

		JPanel middlemiddle = middlemiddle_ref.externalcall_getmiddlemiddle();

		JPanel middleleft = new JPanel();

		JSplitPane splitpane_middlemiddle_middleleft = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, middlemiddle, middleleft);
		splitpane_middlemiddle_middleleft.setOneTouchExpandable(true);
		// splitpane_middlemiddle_middleleft.setDividerLocation(700);

		middlemiddlemiddleleft.add(splitpane_middlemiddle_middleleft);

		JSplitPane splitpane_middleright_middlemiddlemiddleleft = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, middleright,
				middlemiddlemiddleleft);
		splitpane_middleright_middlemiddlemiddleleft
				.setOneTouchExpandable(true);
		// splitpane_middleright_middlemiddlemiddleleft.setDividerLocation(100);

		middle.add(splitpane_middleright_middlemiddlemiddleleft);

		JPanel bottom = new JPanel();

		JPanel bottomleft = new JPanel();

		JTextArea bottomleft_log_textarea = new JTextArea(5, 20);
		bottomleft_log_textarea.setEditable(false);
		bottomleft_log_textarea.setLineWrap(true);
		JScrollPane bottomleft_log_scrollpane = new JScrollPane(
				bottomleft_log_textarea);
		bottomleft_log_scrollpane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		bottomleft_log_scrollpane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		bottomleft.add(bottomleft_log_scrollpane);

		JPanel bottommiddlebottomright = new JPanel();

		JPanel bottommiddle = new JPanel();

		JPanel bottommiddleleft = new JPanel();
		bottommiddleleft.setLayout(new BoxLayout(bottommiddleleft,
				BoxLayout.Y_AXIS));

		JPanel bottommiddlelefttop = new JPanel();
		bottommiddlelefttop.setLayout(new BoxLayout(bottommiddlelefttop,
				BoxLayout.X_AXIS));

		JLabel bottommiddlelefttop_label_1 = new JLabel("L");
		bottommiddlelefttop.add(bottommiddlelefttop_label_1);

		JTextField bottommiddlelefttop_textfield_1 = new JTextField("1");
		bottommiddlelefttop.add(bottommiddlelefttop_textfield_1);

		JLabel bottommiddlelefttop_label_2 = new JLabel(".");
		bottommiddlelefttop.add(bottommiddlelefttop_label_2);

		JTextField bottommiddlelefttop_textfield_2 = new JTextField("1");
		bottommiddlelefttop.add(bottommiddlelefttop_textfield_2);

		JLabel bottommiddlelefttop_label_3 = new JLabel(".");
		bottommiddlelefttop.add(bottommiddlelefttop_label_3);

		JTextField bottommiddlelefttop_textfield_3 = new JTextField("00");
		bottommiddlelefttop.add(bottommiddlelefttop_textfield_3);

		bottommiddleleft.add(bottommiddlelefttop);

		JPanel bottommiddleleftbottom = new JPanel();
		bottommiddleleftbottom.setLayout(new BoxLayout(bottommiddleleftbottom,
				BoxLayout.X_AXIS));

		JLabel bottommiddleleftbottom_label_1 = new JLabel("R");
		bottommiddleleftbottom.add(bottommiddleleftbottom_label_1);

		JTextField bottommiddleleftbottom_textfield_1 = new JTextField("1");
		bottommiddleleftbottom.add(bottommiddleleftbottom_textfield_1);

		JLabel bottommiddleleftbottom_label_2 = new JLabel(".");
		bottommiddleleftbottom.add(bottommiddleleftbottom_label_2);

		JTextField bottommiddleleftbottom_textfield_2 = new JTextField("1");
		bottommiddleleftbottom.add(bottommiddleleftbottom_textfield_2);

		JLabel bottommiddleleftbottom_label_3 = new JLabel(".");
		bottommiddleleftbottom.add(bottommiddleleftbottom_label_3);

		JTextField bottommiddleleftbottom_textfield_3 = new JTextField("00");
		bottommiddleleftbottom.add(bottommiddleleftbottom_textfield_3);

		bottommiddleleft.add(bottommiddleleftbottom);

		bottommiddleleft_update_button = new JButton("UPDATE");
		bottommiddleleft.add(bottommiddleleft_update_button);
		bottommiddleleft_update_button.addActionListener(this);

		JPanel bottommiddleright = new JPanel();
		bottommiddleright.setLayout(new BoxLayout(bottommiddleright,
				BoxLayout.Y_AXIS));

		JPanel bottommiddlerighttop = new JPanel();
		bottommiddlerighttop.setLayout(new BoxLayout(bottommiddlerighttop,
				BoxLayout.X_AXIS));

		JLabel bottommiddlerighttop_label_1 = new JLabel("POS");
		bottommiddlerighttop.add(bottommiddlerighttop_label_1);

		bottommiddlerighttop_textfield_1 = new JTextField("1");
		bottommiddlerighttop.add(bottommiddlerighttop_textfield_1);

		JLabel bottommiddlerighttop_label_2 = new JLabel(".");
		bottommiddlerighttop.add(bottommiddlerighttop_label_2);

		bottommiddlerighttop_textfield_2 = new JTextField("1");
		bottommiddlerighttop.add(bottommiddlerighttop_textfield_2);

		JLabel bottommiddlerighttop_label_3 = new JLabel(".");
		bottommiddlerighttop.add(bottommiddlerighttop_label_3);

		bottommiddlerighttop_textfield_3 = new JTextField("00");
		bottommiddlerighttop.add(bottommiddlerighttop_textfield_3);

		bottommiddleright.add(bottommiddlerighttop);

		JPanel bottommiddlerightbottom = new JPanel();
		bottommiddlerightbottom.setLayout(new BoxLayout(
				bottommiddlerightbottom, BoxLayout.X_AXIS));

		bottommiddlerightbottom_button_1 = new JButton();
		try {
			bottommiddlerightbottom_button_1
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_1.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_1.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_1.setBorder(null);
		bottommiddlerightbottom_button_1.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_1);

		bottommiddlerightbottom_button_2 = new JButton();
		try {
			bottommiddlerightbottom_button_2
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_2.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_2.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_2.setBorder(null);
		bottommiddlerightbottom_button_2.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_2);

		bottommiddlerightbottom_button_3 = new JButton();
		try {
			bottommiddlerightbottom_button_3
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_3.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_3.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_3.setBorder(null);
		bottommiddlerightbottom_button_3.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_3);

		bottommiddlerightbottom_button_4 = new JButton();
		try {
			bottommiddlerightbottom_button_4
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_4.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_4.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_4.setBorder(null);
		bottommiddlerightbottom_button_4.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_4);

		bottommiddlerightbottom_button_5 = new JButton();
		try {
			bottommiddlerightbottom_button_5
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_5.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_5.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_5.setBorder(null);
		bottommiddlerightbottom_button_5.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_5);

		bottommiddlerightbottom_button_6 = new JButton();
		try {
			bottommiddlerightbottom_button_6
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_6.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_6.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_6.setBorder(null);
		bottommiddlerightbottom_button_6.addActionListener(this);
		bottommiddlerightbottom_button_6.setEnabled(false);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_6);

		bottommiddlerightbottom_button_7 = new JButton();
		try {
			bottommiddlerightbottom_button_7
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_7.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_7.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_7.setBorder(null);
		bottommiddlerightbottom_button_7.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_7);

		bottommiddlerightbottom_button_8 = new JButton();
		try {
			bottommiddlerightbottom_button_8
					.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\Marvin\\Desktop\\temp\\ServoOrchestrator_8.png"))));
		} catch (IOException ex) {
		}
		bottommiddlerightbottom_button_8.setMargin(new Insets(0, 0, 0, 0));
		// bottommiddlerightbottom_button_8.setBorder(null);
		bottommiddlerightbottom_button_8.addActionListener(this);
		bottommiddlerightbottom.add(bottommiddlerightbottom_button_8);

		bottommiddleright.add(bottommiddlerightbottom);

		JSplitPane splitpane_bottommiddleleft_bottommiddleright = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, bottommiddleleft,
				bottommiddleright);
		splitpane_bottommiddleleft_bottommiddleright
				.setOneTouchExpandable(true);

		bottommiddle.add(splitpane_bottommiddleleft_bottommiddleright);

		JPanel bottomright = new JPanel();
		bottomright.setLayout(new BoxLayout(bottomright, BoxLayout.Y_AXIS));

		bottomright_click_checkbox = new JCheckBox("CLICK");
		bottomright_click_checkbox.addItemListener(this);
		bottomright.add(bottomright_click_checkbox);

		JSplitPane splitpane_bottommiddle_bottomright = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, bottommiddle, bottomright);
		splitpane_bottommiddle_bottomright.setOneTouchExpandable(true);

		bottommiddlebottomright.add(splitpane_bottommiddle_bottomright);

		JSplitPane splitpane_bottomleft_bottommiddlebottomright = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, bottomleft,
				bottommiddlebottomright);
		splitpane_bottomleft_bottommiddlebottomright
				.setOneTouchExpandable(true);

		bottom.add(splitpane_bottomleft_bottommiddlebottomright);

		JSplitPane splitpane_middle_bottom = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, middle, bottom);
		splitpane_middle_bottom.setOneTouchExpandable(true);
		// splitpane_middle_bottom.setDividerLocation(700);

		middlebottom.add(splitpane_middle_bottom);

		JSplitPane splitpane_top_middlebottom = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, top, middlebottom);
		splitpane_top_middlebottom.setOneTouchExpandable(true);
		// splitpane_top_middlebottom.setDividerLocation(50);

		// splitpane_top_middlebottom.pack();
		display.add(splitpane_top_middlebottom);
	}

	public void getState(_TemplateService template) {
		// I think I should do something with this ...
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

			}
		});
	}

	@Override
	public void attachGUI() {
		// commented out subscription due to this class being used for
		// un-defined gui's

		// subscribe("publishState", "getState", _TemplateService.class);
		// send("publishState");
	}

	@Override
	public void detachGUI() {
		// commented out subscription due to this class being used for
		// un-defined gui's

		// unsubscribe("publishState", "getState", _TemplateService.class);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();

		// Button - Events
		if (o == middleright_update_button) {
			myService.send(boundServiceName, "middleright_update_button");
		} else if (o == middleright_attach_button) {
			myService.send(boundServiceName, "middleright_attach_button");
		} else if (o == bottommiddleleft_update_button) {
			// TODO - add functionality
		} else if (o == bottommiddlerightbottom_button_1) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_1");
		} else if (o == bottommiddlerightbottom_button_2) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_2");
		} else if (o == bottommiddlerightbottom_button_3) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_3");
		} else if (o == bottommiddlerightbottom_button_4) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_4");
		} else if (o == bottommiddlerightbottom_button_5) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_5");
		} else if (o == bottommiddlerightbottom_button_6) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_6");
		} else if (o == bottommiddlerightbottom_button_7) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_7");
		} else if (o == bottommiddlerightbottom_button_8) {
			myService.send(boundServiceName, "bottommiddlerightbottom_button_8");
		}

		myService.send(boundServiceName, "publishState");
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object o = ie.getSource();

		// CheckBox - Events
		if (o == bottomright_click_checkbox) {
			// TODO - add functionality
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		Object o = lse.getSource();
		
		//List - Events
		if (o == middleright_arduino_list) {
			myService.send(boundServiceName, "middleright_arduino_list");
		} else if (o == middleright_pin_list) {
			//TODO - add functionality
		}
	}

	public void externalcall_loadsettings(int pos) {
		myService.send(boundServiceName, "externalcall_loadsettings", pos);
	}
}