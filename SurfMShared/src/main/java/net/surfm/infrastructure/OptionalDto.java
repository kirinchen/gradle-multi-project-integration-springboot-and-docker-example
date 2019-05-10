package net.surfm.infrastructure;

public class OptionalDto<T> {
	
	private T body;
	
	private boolean present;
	
	

	public OptionalDto() {
	}

	public OptionalDto(T body) {
		super();
		this.body = body;
		present = (this.body != null);
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
	
	public static <E> OptionalDto<E> gen(E e){
		return new OptionalDto<E>(e);
	}
	
	public static <E> OptionalDto<E> empty(){
		return new  OptionalDto<E>();
	}
	

}
