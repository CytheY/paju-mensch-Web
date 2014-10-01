package observer;

import java.util.*;

public class Observable{
	protected Vector<IObserver> subscribers = new Vector<IObserver>(2);
	
	public void addObserver(IObserver s){
		subscribers.addElement(s);
	}
	
	public void removeObserver(IObserver s){
		subscribers.removeElement(s);
	}
	
	public void removeAllObservers(){
		subscribers.removeAllElements();
	}
	
	public void notifyObserversArray(){
		for(Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();){
			IObserver observer = iter.next();
			observer.updatePrintArray();
		}
	}
	
	public void notifyObserversPrintDice() {
		for(Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();){
			IObserver observer = iter.next();
			observer.updatePrintDice();
		}
	}
	
	public void notifyObserversPrintActiveFigures(){
		for(Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();){
			IObserver observer = iter.next();
			observer.updatePrintFigures();
	}

}
}
