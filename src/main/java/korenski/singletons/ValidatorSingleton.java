package korenski.singletons;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public class ValidatorSingleton {

	private static ValidatorSingleton instance = null;
	private ValidatorFactory validatorFactory;
	javax.validation.Validator validator;
	
	private ValidatorSingleton() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	public static ValidatorSingleton getInstance() {
		if (instance == null) {
			instance = new ValidatorSingleton();
		}
		return instance;
	}

	
	public javax.validation.Validator getValidator() {
		return validator;
	}

	public static void setInstance(ValidatorSingleton instance) {
		ValidatorSingleton.instance = instance;
	}

	
	
}
