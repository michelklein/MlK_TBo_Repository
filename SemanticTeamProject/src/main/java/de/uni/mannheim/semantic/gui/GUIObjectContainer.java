package de.uni.mannheim.semantic.gui;

import de.uni.mannheim.semantic.model.FBPerson;

public class GUIObjectContainer {
FBPerson fbp;

public FBPerson getFbp() {
	return fbp;
}

public GUIObjectContainer(FBPerson fbp) {
	super();
	this.fbp = fbp;
}


}
