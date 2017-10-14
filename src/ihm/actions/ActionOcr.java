package ihm.actions;

import java.awt.image.BufferedImage;
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
	
	private static final Logger LOGGER = Logger.getLogger(ActionOcr.class);
	
	private boolean active = false;
	
	private BufferedImage imageEndAction;
	
	private static List<ActionOcr> actions = new ArrayList<ActionOcr>();
	
	private List<ActionOCRListener> listeners = new ArrayList<ActionOCRListener>();
	
	private static List<ActionAllOCRListener> listenersAll = new ArrayList<ActionAllOCRListener>();
	
	private boolean done = false;
	
	private boolean enable = false;
	
	private static ActionOCRListener actionOCRListener = new ActionOCRListener() {

		@Override
		public void enableChange(boolean oldEnable, boolean newEnable) {
			// nothing to do
			
		}

		@Override
		public void doneChange(boolean oldDone, boolean newDone) {
			checkEnable();
		}
		
		
	};
	
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
			act.addActionListener(actionOCRListener);
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
	
	public void addActionListener(ActionOCRListener actionListener) {
		listeners.add(actionListener);
	}
	
	public void removeActionListener(ActionOCRListener actionListener) {
		listeners.remove(actionListener);
	}
	public ActionOCRListener[] getListeners() {
		return listeners.toArray(new ActionOCRListener[0]);
	}
	
	public static void addActionAllOCRListener(ActionAllOCRListener actionListener) {
		listenersAll.add(actionListener);
	}
	
	public static void removeActionAllOCRListener(ActionAllOCRListener actionListener) {
		listenersAll.remove(actionListener);
	}
	public static ActionAllOCRListener[] getAllOCRListeners() {
		return listenersAll.toArray(new ActionAllOCRListener[0]);
	}
	
	private static void fireActionChange(ActionOcr action) {
		ActionAllOCRListener[] tmp = getAllOCRListeners();
		for(ActionAllOCRListener listener : tmp) {
			listener.actionChange(action);
		}
	}
	
	private void fireDoneChange(boolean oldDone,boolean newDone) {
		ActionOCRListener[] tmp = getListeners();
		for(ActionOCRListener listener : tmp) {
			listener.doneChange(oldDone, newDone);
		}
	}
	
	private void fireEnableChange(boolean oldEnable,boolean newEnable) {
		ActionOCRListener[] tmp = getListeners();
		for(ActionOCRListener listener : tmp) {
			listener.enableChange(oldEnable, newEnable);
		}
	}
	
	public abstract JPanel getPanelOption();

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		boolean oldEnable = this.enable;
		this.enable = enable;
		if(oldEnable != this.enable) {
			if(this.enable) {
				LOGGER.debug(getClass().getName()+": is enable");
			}else {
				LOGGER.debug(getClass().getName()+": is disable");
			}
			fireEnableChange(oldEnable, this.enable);
		}
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public final void setDone(boolean done) {
		boolean oldDone = this.done;
		this.done = done;
		if(oldDone != this.done) {
			fireDoneChange(oldDone, this.done);
			checkEnable();
		}
		if(!this.done) {
			resetDoneAfter();
			checkEnable();
		}
	}
	
	private void resetDoneAfter() {
		String className = getClass().getName();
		if(className.equals(ActionOpen.class.getName())) {
			getAction(ActionRescale.class.getName()).setDone(false);
		}else if(className.equals(ActionRescale.class.getName())) {
			getAction(ActionGreyScale.class.getName()).setDone(false);
		}else if(className.equals(ActionGreyScale.class.getName())) {
			getAction(ActionGomme.class.getName()).setDone(false);
		}else if(className.equals(ActionGomme.class.getName())) {
			getAction(ActionFindLine.class.getName()).setDone(false);
		}else if(className.equals(ActionFindLine.class.getName())) {
			getAction(ActionOCRCalcul.class.getName()).setDone(false);
		}
	}
	
	private static void checkEnable() {
		ActionOpen actionOpen= (ActionOpen) getAction(ActionOpen.class.getName());
		ActionRescale actionRescale= (ActionRescale) getAction(ActionRescale.class.getName());
		ActionGreyScale actionGreyScale= (ActionGreyScale) getAction(ActionGreyScale.class.getName());
		ActionGomme actionGomme= (ActionGomme) getAction(ActionGomme.class.getName());
		ActionFindLine actionFindLine= (ActionFindLine) getAction(ActionFindLine.class.getName());
		ActionOCRCalcul actionOcr= (ActionOCRCalcul) getAction(ActionOCRCalcul.class.getName());

		actionOpen.setEnable(true);
		
		actionRescale.setEnable(actionOpen.isDone());
		
		actionGreyScale.setEnable(actionRescale.isDone());
		
		actionGomme.setEnable(actionGreyScale.isDone());
		
		actionFindLine.setEnable(actionGomme.isDone());
		
		
		actionOcr.setEnable(actionFindLine.isDone());
	}

	/**
	 * @return the imageEndAction
	 */
	public BufferedImage getImageEndAction() {
		return imageEndAction;
	}
	
	/**
	 * 
	 * @param image {@link BufferedImage}
	 */
	protected void setImageEndAction(BufferedImage image) {
		this.imageEndAction = image;
	}
	
	public BufferedImage getImageStart() {
		String className = getClass().getName();
		if(className.equals(ActionOpen.class.getName())) {
			return null;
		}else if(className.equals(ActionRescale.class.getName())) {
			return ActionOcr.getAction(ActionOpen.class.getName()).getImageEndAction();
		}else if(className.equals(ActionGreyScale.class.getName())) {
			return ActionOcr.getAction(ActionRescale.class.getName()).getImageEndAction();
		}else if(className.equals(ActionGomme.class.getName())) {
			return ActionOcr.getAction(ActionGreyScale.class.getName()).getImageEndAction();
		}else if(className.equals(ActionFindLine.class.getName())) {
			return ActionOcr.getAction(ActionGomme.class.getName()).getImageEndAction();
		}else if(className.equals(ActionOCRCalcul.class.getName())) {
			return ActionOcr.getAction(ActionFindLine.class.getName()).getImageEndAction();
		}
		return null;
	}


}
