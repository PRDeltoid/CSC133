package com.mycompany.a2;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	public void add(Object object) {
		objects.add((GameObject) object);
	}

	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	public GameObject get(int index) {
		return objects.get(index);
	}
	
	public int size() {
		return objects.size();
	}
	
	public void clear() {
		objects.clear();
	}


	private class GameObjectIterator implements IIterator {
		private int currentObjectIndex = -1;

		public boolean hasNext() {
			//If our array has no elements, there can be no "next" item
			if(size() <= 0) {
				return false;
			//If our index = our size - 1,we've hit the end of the list and can't go any further
			} else if(currentObjectIndex == size() - 1) {
				return false;
			//Otherwise, we have a next object available
			} else {
				return true;
			}
		}

		public GameObject next() {
			currentObjectIndex += 1;
			return(objects.get(currentObjectIndex));
		}
	}

}