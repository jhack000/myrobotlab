package org.myrobotlab.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * source modified from:
 * http://bryanesmith.com/docs/drag-and-drop-java-5/DragAndDropPanelsDemo.java
 */
/**
 *
 * @author LunDev (github), Ma. Vo. (MyRobotlab)
 */
public class ServoOrchestratorGUI_middlemiddle_panel extends JPanel implements
		Transferable {

	public String type;

	private static int counter = 0;

	int id = 0;

	JPanel timesection_panel;
	JLabel timesection_headline;
	JLabel timesection_id;

	JPanel channel_panel;
	JLabel channel_name;
	JButton channel_mute;
	JLabel channel_id;
	JButton channel_solo;
	JButton channel_settings;

	JPanel servo_panel;
	JTextField servo_start;
	JLabel servo_channelid;
	public JTextField servo_goal;
	JLabel servo_min;
	JLabel servo_id;
	JLabel servo_max;
	JButton servo_more;

	// JPanel stepper_panel;
	public ServoOrchestratorGUI_middlemiddle_panel(String mode) {
		type = mode;

		// Add the listener which will export this panel for dragging
		this.addMouseListener(new ServoOrchestratorGUI_middlemiddle_draggablemouselistener());

		// Add the handler, which negotiates between drop target and this
		// draggable panel
		this.setTransferHandler(new ServoOrchestratorGUI_middlemiddle_transferhandler());

		// Create the ID of this panel
		counter++;

		id = counter;

		// Style it a bit to set apart from container
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// "Timesection" - Panel
		timesection_panel = new JPanel();
		timesection_panel.setLayout(new GridBagLayout());

		timesection_headline = new JLabel("HEADLINE");
		timesection_id = new JLabel("ID##");

		// x y w h
		timesection_panel.add(timesection_headline,
				gridbaglayout_set(0, 0, 3, 1));
		timesection_panel.add(timesection_id, gridbaglayout_set(1, 2, 1, 1));

		this.add(timesection_panel);

		// "Channel" - Panel
		channel_panel = new JPanel();
		channel_panel.setLayout(new GridBagLayout());

		channel_name = new JLabel("NAME");
		channel_mute = new JButton("M");
		channel_id = new JLabel("ID##");
		channel_solo = new JButton("S");
		channel_settings = new JButton("SETTINGS");
		// TODO - add ActionListener for "mute"
		// TODO - add actionListener for "solo"

		// x y w h
		channel_panel.add(channel_name, gridbaglayout_set(0, 0, 3, 1));
		channel_panel.add(channel_mute, gridbaglayout_set(0, 1, 1, 1));
		channel_panel.add(channel_id, gridbaglayout_set(1, 1, 1, 1));
		channel_panel.add(channel_solo, gridbaglayout_set(2, 1, 1, 1));
		channel_panel.add(channel_settings, gridbaglayout_set(0, 2, 3, 1));

		this.add(channel_panel);

		// "Servo" - Panel
		servo_panel = new JPanel();
		servo_panel.setLayout(new GridBagLayout());

		servo_start = new JTextField("STAR");
		servo_channelid = new JLabel("CHID");
		servo_goal = new JTextField("GOAL");
		servo_min = new JLabel("MIN#");
		servo_id = new JLabel("ID##");
		servo_max = new JLabel("MAX#");
		servo_more = new JButton("MORE");
		// TODO - ActionListener for "more"

		// x y w h
		servo_panel.add(servo_start, gridbaglayout_set(0, 0, 1, 1));
		servo_panel.add(servo_channelid, gridbaglayout_set(1, 0, 1, 1));
		servo_panel.add(servo_goal, gridbaglayout_set(2, 0, 1, 1));
		servo_panel.add(servo_min, gridbaglayout_set(0, 1, 1, 1));
		servo_panel.add(servo_id, gridbaglayout_set(1, 1, 1, 1));
		servo_panel.add(servo_max, gridbaglayout_set(2, 1, 1, 1));
		servo_panel.add(servo_more, gridbaglayout_set(0, 2, 3, 1));

		this.add(servo_panel);

		switch (type) {
		case "timesection":
			timesection_panel.setVisible(true);
			channel_panel.setVisible(false);
			servo_panel.setVisible(false);
			break;
		case "channel":
			timesection_panel.setVisible(false);
			channel_panel.setVisible(true);
			servo_panel.setVisible(false);
			break;
		case "servo":
			timesection_panel.setVisible(false);
			channel_panel.setVisible(false);
			servo_panel.setVisible(true);
			break;
		}

		// stepper_panel = new JPanel();
		// //TODO - work it further
		// JLabel valueLabel = new JLabel(counter + "");
		// // valueLabel.setForeground(Color.WHITE);
		// // this.add(valueLabel);
		// servo_panel.add(valueLabel);
		//
		// JTextField tf = new JTextField(counter + "");
		// // this.add(tf);
		// stepper_panel.add(tf);
		//
		// this.add(stepper_panel);
		// TODO - Remove it
		// // Set a random background color so can easily distinguish
		// switch (random.nextInt(6)) {
		// case 0:
		// this.setBackground(Color.BLUE);
		// // servo_panel.setVisible(false);
		// // stepper_panel.setVisible(true);
		// break;
		// case 1:
		// this.setBackground(Color.DARK_GRAY);
		// // servo_panel.setVisible(true);
		// // stepper_panel.setVisible(false);
		// break;
		// case 2:
		// this.setBackground(Color.GREEN);
		// break;
		// case 3:
		// this.setBackground(Color.ORANGE);
		// break;
		// case 4:
		// this.setBackground(Color.RED);
		// break;
		// case 5:
		// this.setBackground(Color.BLACK);
		// break;
		// }

		// TODO - maybe remove ???
		// This won't take the entire width for easy drag and drop
		final Dimension d = new Dimension(130, 80);
		this.setPreferredSize(d);
		this.setMinimumSize(d);
	}

	/**
	 * <p>
	 * One of three methods defined by the Transferable interface.
	 * </p>
	 * <p>
	 * If multiple DataFlavor's are supported, can choose what Object to return.
	 * </p>
	 * <p>
	 * In this case, we only support one: the actual JPanel.
	 * </p>
	 * <p>
	 * Note we could easily support more than one. For example, if supports text
	 * and drops to a JTextField, could return the label's text or any arbitrary
	 * text.
	 * </p>
	 *
	 * @param flavor
	 * @return
	 */
	public Object getTransferData(DataFlavor flavor) {

		System.out
				.println("Step 7 of 7: Returning the data from the Transferable object. In this case, the actual panel is now transfered!");

		DataFlavor thisFlavor = null;

		try {
			thisFlavor = ServoOrchestratorGUI_middlemiddle_main
					.getDragAndDropPanelDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem lazy loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return null;
		}

		// For now, assume wants this class... see loadDnD
		if (thisFlavor != null && flavor.equals(thisFlavor)) {
			return ServoOrchestratorGUI_middlemiddle_panel.this;
		}

		return null;
	}

	/**
	 * <p>
	 * One of three methods defined by the Transferable interface.
	 * </p>
	 * <p>
	 * Returns supported DataFlavor. Again, we're only supporting this actual
	 * Object within the JVM.
	 * </p>
	 * <p>
	 * For more information, see the JavaDoc for DataFlavor.
	 * </p>
	 *
	 * @return
	 */
	public DataFlavor[] getTransferDataFlavors() {

		DataFlavor[] flavors = { null };

		System.out
				.println("Step 4 of 7: Querying for acceptable DataFlavors to determine what is available. Our example only supports our custom RandomDragAndDropPanel DataFlavor.");

		try {
			flavors[0] = ServoOrchestratorGUI_middlemiddle_main
					.getDragAndDropPanelDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem lazy loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return null;
		}

		return flavors;
	}

	/**
	 * <p>
	 * One of three methods defined by the Transferable interface.
	 * </p>
	 * <p>
	 * Determines whether this object supports the DataFlavor. In this case,
	 * only one is supported: for this object itself.
	 * </p>
	 *
	 * @param flavor
	 * @return True if DataFlavor is supported, otherwise false.
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {

		System.out
				.println("Step 6 of 7: Verifying that DataFlavor is supported.  Our example only supports our custom RandomDragAndDropPanel DataFlavor.");

		DataFlavor[] flavors = { null };
		try {
			flavors[0] = ServoOrchestratorGUI_middlemiddle_main
					.getDragAndDropPanelDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem lazy loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return false;
		}

		for (DataFlavor f : flavors) {
			if (f.equals(flavor)) {
				return true;
			}
		}

		return false;
	}

	// TODO - remove
	// //Getting the panel's ID
	// public int getid() {
	// return id;
	// }
	// TODO - remove
	// public void setpanel1() {
	// servo_panel.setVisible(true);
	// stepper_panel.setVisible(false);
	// }
	// TODO - remove
	// public void setpanel2() {
	// servo_panel.setVisible(false);
	// stepper_panel.setVisible(true);
	// }
	public GridBagConstraints gridbaglayout_set(int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 0, 0, 0);

		gbc.gridx = x;
		gbc.gridy = y;

		gbc.gridwidth = w;
		gbc.gridheight = h;

		return gbc;
	}
}