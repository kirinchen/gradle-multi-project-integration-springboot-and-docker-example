package net.surfm.jpatool;

import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("serial")
public class SetString extends HashSet<String>{

	public SetString() {
		super();
	}

	public SetString(Collection<? extends String> c) {
		super(c);
	}

	public SetString(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public SetString(int initialCapacity) {
		super(initialCapacity);
	}
	
	
	
}