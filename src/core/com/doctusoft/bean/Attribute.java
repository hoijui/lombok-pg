package com.doctusoft.bean;

public interface Attribute<E, T> {

	T getValue( E instance );

	void setValue( E instance, T value );

	Class<E> getParent();

	Class<T> getType();

	String getName();

}
