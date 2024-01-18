package telran.cars.exceptions;

import telran.cars.api.ServiceExceptionMessages;
@SuppressWarnings("serial")
public class IllegalModelStateException extends IllegalStateException {
	public IllegalModelStateException() {
		super(ServiceExceptionMessages.MODEL_ALREADY_EXISTS);
	}
	

}
