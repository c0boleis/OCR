package ihm.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

public abstract class ActionOcr extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3374833288984520537L;
	
	private boolean active = false;
	
	private static List<ActionOcr> actions = new ArrayList<ActionOcr>();
	
	private static List<ActionOCRListener> listeners = new ArrayList<ActionOCRListener>();
	
	/**
	 * @param name
	 */
	public ActionOcr(String name) {
		super(name);
	}

	public static ActionOcr getAction(String nameId) {
		ActionOcr[] actionsTmp = getActions();
		for(ActionOcr action : actionsTmp) {
			if(action.getNameId().equals(nameId)) {
				return action;
			}
		}
		return createAction(nameId);
	}
	
	private static ActionOcr createAction(String nameId) {
		try {
			Class<?> classAction = ActionOcr.class.getClassLoader().loadClass(nameId);
			ActionOcr act =  (ActionOcr) classAction.getConstructor().newInstance();
			actions.add(act);
			return act;
		} catch (ClassNotFoundException | InstantiationException |
				IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException |
				SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param act the active to set
	 */
	public void setActive(boolean act) {
		if(act) {
			unActiveAllAction();
		}
		Logger.getLogger(getClass()).info("ActionOcr.setActive("+act+")");
		this.active = act;
		if(act) {
			fireActionChange(this);
		}
	}
	
	private static void unActiveAllAction() {
		ActionOcr[] actionsTmp = getActions();
		for(ActionOcr action : actionsTmp) {
			action.setActive(false);
		}
	}
	
	public static ActionOcr[] getActions() {
		return actions.toArray(new ActionOcr[0]);
	}


	public String getNameId() {
		return getClass().getName();
	}
	
	public static void addActionListener(ActionOCRListener actionListener) {
		listeners.add(actionListener);
	}
	
	public static void removeActionListener(ActionOCRListener actionListener) {
		listeners.remove(actionListener);
	}
	public static ActionOCRListener[] getListeners() {
		return listeners.toArray(new ActionOCRListener[0]);
	}
	
	private void fireActionChange(ActionOcr action) {
		ActionOCRListener[] tmp = getListeners();
		for(ActionOCRListener listener : tmp) {
			listener.actionChange(action);
		}
	}
	
	public abstract JPanel getPanelOption();


}
