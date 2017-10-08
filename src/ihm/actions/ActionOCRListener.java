package ihm.actions;

public interface ActionOCRListener {
	
	public void enableChange(boolean oldEnable, boolean newEnable);
	
	public void doneChange(boolean oldDone, boolean newDone);

}
