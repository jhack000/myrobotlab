package org.myrobotlab.scratch.elements;

import java.awt.Graphics;
import java.util.ArrayList;

import org.myrobotlab.scratch.BoundingBox;
import org.myrobotlab.scratch.DockingBox;
import org.myrobotlab.scratch.Renderable;
import org.myrobotlab.scratch.ScratchUtils;
import org.myrobotlab.scratch.elements.BooleanObject;
import org.myrobotlab.scratch.elements.voids.FunctionResultException;

/**
 * Das BooleanMethodObject ist das Hauptelement f[?]r alle boolschen
 * Ausdr[?]cke.
 * 
 * based on the Scratch-Interface in the "hamster-simulator"
 * -> http://www.java-hamster-modell.de/scratch.html
 * by J[ue]rgen Boger
 * this file HackZ
 * @author LunDev (github), Ma. Vo. (MyRobotLab)
 *
 */

public class BooleanMethodObject extends BooleanObject {
	public static int START_WIDTH = 12;
	public static int TEXT_SPACING = 6;
	public static int MIN_WIDTH = 25;
	public static int MIN_HEIGHT = 17;
	public static final int PARAMETER_SPACING = 5;
	
	private int textWidth;
	private int textHeight;
	protected ArrayList<RType> parameter;
	
	public BooleanMethodObject(String name, ArrayList<RType> parameter) {
		super(false);
		this.name = name;
		this.drawName = name;
		this.parameter = parameter;
		
		textWidth = ScratchUtils.getTextWidth(name, TEXT_FONT);
		textHeight = ScratchUtils.getTextHeight(name, TEXT_FONT);
		
		maxChilds = parameter.size();
		
		for (int i = 0; i < parameter.size(); i++) {
			DockingBox temp = new DockingBox(0, 0, 0, 0, 0, 0, RType.BOOLEAN);
			dockings.put(i, temp);
		}
		
		updateBounds();
	}
	
	public BooleanMethodObject(String name, String drawName, ArrayList<RType> parameter) {
		super(false);
		this.name = name;
		this.drawName = drawName;
		this.parameter = parameter;
		
		textWidth = ScratchUtils.getTextWidth(drawName, TEXT_FONT);
		textHeight = ScratchUtils.getTextHeight(drawName, TEXT_FONT);
		
		maxChilds = parameter.size();
		
		for (int i = 0; i < parameter.size(); i++) {
			DockingBox temp = new DockingBox(0, 0, 0, 0, 0, 0, RType.BOOLEAN);
			dockings.put(i, temp);
		}
		
		updateBounds();
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
		this.drawName = name;
		textWidth = ScratchUtils.getTextWidth(name, TEXT_FONT);
		textHeight = ScratchUtils.getTextHeight(name, TEXT_FONT);
		updateBounds();
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		g.setColor(COLOR_TEXT);
		g.setFont(TEXT_FONT);
	    g.drawString(drawName, bounding.getX() + START_WIDTH, bounding.getY() + textHeight + (bounding.getHeight() - textHeight) / 2 - 2);
	    
	    for (int i = 0; i < maxChilds; i++)
			if (childs.get(i) != null)
				childs.get(i).render(g);
			else
				dockings.get(i).getType().render(g, dockings.get(i).getDockX() + bounding.getX(), dockings.get(i).getDockY() + bounding.getY());
	}
	
	@Override
	public void updateBounds() {
		// Breite berechnen
		int w = START_WIDTH;
		int h = MIN_HEIGHT;
		w += textWidth;
		
		//alle spacings und parameter in die breite mit einrechnen
		for (int i = 0; i < maxChilds; i++) {
			w += PARAMETER_SPACING;
			dockings.get(i).setPosition(w, 0);
			dockings.get(i).setDockX(w);
			
			if (childs.get(i) != null) {
				BoundingBox temp = childs.get(i).getGlobalBounding();
				w += temp.getWidth();
				h = (h < temp.getHeight() + 4) ? temp.getHeight() + 4 : h;
				dockings.get(i).setSize(temp.getWidth(), temp.getHeight());
			}
			else {
				w += dockings.get(i).getType().getWidth();
				h = (h < dockings.get(i).getType().getHeight() + 4) ? dockings.get(i).getType().getHeight() + 4 : h;
				dockings.get(i).setSize(dockings.get(i).getType().getWidth(), dockings.get(i).getType().getHeight());
			}
		}
		w += START_WIDTH;
		
		// Verticale Ausrichtung der Dockings anpassen
		for (int i = 0; i < maxChilds; i++) {
			dockings.get(i).setPosition(dockings.get(i).getX(), (h - dockings.get(i).getHeight()) / 2);
			dockings.get(i).setDockY((h - dockings.get(i).getHeight()) / 2);
		}
		
		w = (w < MIN_WIDTH) ? MIN_WIDTH : w;
		bounding.setSize(w, h);
		
		if (parent != null)
			parent.updateBounds();
	}

	@Override
	public Renderable clone() {
		return new BooleanMethodObject(name, parameter);
	}

	@Override
	public void writeSourceCode(StringBuffer buffer, int layer, boolean comment, boolean needsReturn) throws FunctionResultException {
		startLine(buffer, layer, comment);
		buffer.append(this.name + "()");
	}
}