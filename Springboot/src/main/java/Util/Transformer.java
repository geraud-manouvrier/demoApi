package Util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.geraud.demoApi.rs.model.PersonDb;
import cl.geraud.demoApi.rs.model.request.PersonRequest;
import cl.geraud.demoApi.rs.model.response.PersonResponse;

@Service
public class Transformer {

	//Transforming operations from DB to business model
	public PersonResponse personDbToPersonResponse(PersonDb personDb) {
		
		PersonResponse personResponse=new PersonResponse();
		
		if (personDb.getRutInt()!=0) {
			personResponse.setRut(String.valueOf(personDb.getRutInt())+"-"+personDb.getRutDv());
			personResponse.setName(personDb.getName());
			personResponse.setLastName(personDb.getLastName());
			personResponse.setAge(personDb.getAge());
			personResponse.setCourse(personDb.getCourse());
		}
		
		return personResponse;
	}

	public PersonDb personRequestToPersonDb(PersonRequest person) {
		
		PersonDb personTemp=new PersonDb();
		
		if (person.getRut()!=null) {
		
			int rutInt;
			char rutDv;
			rutInt=Integer.parseInt(person.getRut().split("-")[0]);
			rutDv=person.getRut().split("-")[1].charAt(0);
			
			personTemp.setRutInt(rutInt);
			personTemp.setRutDv(rutDv);
			personTemp.setName(person.getName());
			personTemp.setLastName(person.getLastName());
			personTemp.setAge(person.getAge());
			personTemp.setCourse(person.getCourse());
		}
		
		return personTemp;
	}

	public List<PersonResponse> personsDbToPersonsResponse(List<PersonDb> personsDb) {
		
		List<PersonResponse> personsResponse=new ArrayList<PersonResponse>();
		for (int i=0; i<personsDb.size(); i++) {
			PersonResponse personTemp;
			personTemp = personDbToPersonResponse(personsDb.get(i));
			personsResponse.add(personTemp);
		}
		return personsResponse;
	}

}
