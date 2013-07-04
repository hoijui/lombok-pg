package com.doctusoft.bean;

public final class Attributes {

	private Attributes() {}

	public static <E, T> Attribute<E, T> of( final Class<E> parent, final String name, final Class<T> type ) {
//		checkNotNull( parent );
//		checkNotEmpty( name );
		System.out.println("itt jartam");
		
		return new AttributeImpl<E, T>(parent, name, type);
	}
	
	public static <E, T> Attribute<E, T> of2( final String name ) {
//		checkNotNull( parent );
//		checkNotEmpty( name );
		System.out.println("itt jartam");
		
		return new AttributeImpl<E, T>(null, name,null);
	}
	

//	public static <E, T> Function<E, T> functionOf( final Attribute<E, T> attribute ) {
//		return new AttributeFunction<E, T>( attribute );
//	}
//
//	private static class AttributeFunction<E, T> implements Function<E, T> {
//
//		private final Attribute<E, T> attribute;
//
//		public AttributeFunction( final Attribute<E, T> attribute ) {
//			checkNotNull( attribute );
//			this.attribute = attribute;
//		}
//
//		@Override
//		public T apply( final E input ) {
//			return attribute.getValue( input );
//		}
//	}

}
