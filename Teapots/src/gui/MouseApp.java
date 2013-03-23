package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mediator.Mediator;

public class MouseApp extends MouseAdapter {
	Mediator med;

	public MouseApp(Mediator md) {
		super();
		med = md;
	}

	public void mousePressed(MouseEvent e) {
		//med.mouseDown(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		//med.mouseUp(e.getX(), e.getY());
	}
}
