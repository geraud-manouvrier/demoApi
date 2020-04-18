package Util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import cl.geraud.demoApi.rs.model.request.PersonRequest;

@Service
public class Validator {
	//Requests validations
	public int validatePersonRequest(PersonRequest person, boolean withOptionalfields) {
		
		if (!isValidRut(person.getRut(), withOptionalfields))
			return 100;
		if (!isValidName(person.getName(), withOptionalfields))
			return 200;
		if (!isValidLastName(person.getLastName(), withOptionalfields))
			return 300;
		if (!isValidAge(person.getAge(),  withOptionalfields))
			return 400;
		if (!isValidCourse(person.getCourse(), withOptionalfields))
			return 500;
		return 0;
	}
	
	
	//Generic Validations
	public boolean isValidRut(String rut, boolean isNulleable) {
		if (rut ==null && isNulleable)
			return true;
		if (rut ==null && !isNulleable)
			return false;
		
		if (!isValidMaskRut(rut))
			return false;
		
		int rutIntTemp=Integer.valueOf(rut.split("-")[0].replace(".", ""));
		if (rut.charAt(rut.length()-1)!=calculateDv(rutIntTemp))
			return false;
		
		return true;
	}
	public boolean isValidName(String name, boolean isNulleable) {
		Tool tool	= new Tool();
		int maxLen	= tool.getPropertiesInt("lenName");
		
		if (name ==null && isNulleable)
			return true;
		if (name ==null && !isNulleable)
			return false;
		
		if (name.length()>maxLen)
			return false;
		
		return true;
	}

	public boolean isValidLastName(String lastName, boolean isNulleable) {
		Tool tool	= new Tool();
		int maxLen	= tool.getPropertiesInt("lenLastName");
		
		if (lastName ==null && isNulleable)
			return true;
		if (lastName ==null && !isNulleable)
			return false;
		
		if (lastName.length()>maxLen)
			return false;
		
		return true;
	}


	public boolean isValidCourse(String course, boolean isNulleable) {
		Tool tool	= new Tool();
		int maxLen	= tool.getPropertiesInt("lenCourse");
		
		if (course ==null && isNulleable)
			return true;
		if (course ==null && !isNulleable)
			return false;
		
		if (course.length()>maxLen)
			return false;
		
		return true;
	}

	public boolean isValidAge(int age, boolean isNulleable) {
		Tool tool	= new Tool();
		int minAge	= tool.getPropertiesInt("minAge");
		int maxAge	= tool.getPropertiesInt("maxAge");
		
		//Remember, int never are null!!!! (int != Integer)
		if (age ==0 && isNulleable)
			return true;
		if (age ==0 && !isNulleable)
			return false;
		
		if (age<minAge || age>maxAge)
			return false;
		
		
		return true;
	}
	
	public char calculateDv(int rutInt) {
		
		String rutStr=String.valueOf(rutInt);
		
		int factor = 2;
		int acum=0;
		char dv;
		
		for (int  i= rutStr.length()-1; i >=0; i--) {
			acum=acum+(Integer.valueOf(rutStr.substring(i, i+1))*factor);
			
			if (factor == 7)
				factor = 1;
			
			factor++;
		}

		int digInt=11-(acum % 11);
		
		
		if (digInt==11)
			dv='0';
		else if (digInt==10)
			dv='K';
		else
			dv=Integer.toString(digInt).charAt(0);	// Character.forDigit(digInt, RADIX);
		
		
		return dv;
		
	}
	
	public boolean isValidMaskRut(String rut) {
		String [] mask=new String[2];
		//XXX.YYY.ZZZ-D
		mask[0]="[1-9][0-9]{0,2}(\\.[0-9]{3}){0,2}-[0-9kK]";
		//XXXYYYZZZ-D
		mask[1]="[1-9][0-9]{0,8}-[0-9kK]";
		
		for (int i=0; i<mask.length; i++) {
			if (Pattern.matches(mask[i], rut))
				return true;
		}
		
		return false;
	}
	

}
