package com.mycompany.a2;

public interface ICollection {
	abstract void add(Object object);
	
	abstract IIterator getIterator();
}
