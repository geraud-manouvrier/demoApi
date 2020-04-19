package cl.geraud.demoApi.rs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import Util.Transformer;
import Util.Validator;
import cl.geraud.demoApi.rs.dao.PersonDao;
import cl.geraud.demoApi.rs.model.request.PersonRequest;
import cl.geraud.demoApi.rs.model.response.PersonResponse;

@RestController
public class PeopleController {

	@Autowired
	PersonDao personDao;
	
	@GetMapping("/people")
	public ResponseEntity<List<PersonResponse>> getPersons(
								@RequestHeader(value = "Content-Type", required = false) String headerContentType,
								@RequestHeader(value = "Authorization", required = false) String headerAutorization,
								@RequestHeader(value = "Accept", required = false) String headerAccept) {
		
		Transformer util				= new Transformer();
		Validator validator				= new Validator();
		List<PersonResponse> response;
		HttpHeaders responseHeaders 	= new HttpHeaders();
		
		responseHeaders.set("Content-Type", "application/json");
		
		if (validator.validateRequestHeaders(headerContentType, headerAutorization, headerAccept)!=0)
			return new ResponseEntity<List<PersonResponse>>(responseHeaders, HttpStatus.BAD_REQUEST);

		response	= util.personsDbToPersonsResponse(personDao.getPersons());
		
		//This line has not sense, but if in the future it is wanted change th status when there are no person in DB, is easiest
		if (response.isEmpty())
			return new ResponseEntity<List<PersonResponse>>(responseHeaders, HttpStatus.OK);

		
		return new ResponseEntity<List<PersonResponse>>(response, responseHeaders, HttpStatus.OK);
	}
	
	@GetMapping("/people/{rut}")
	public ResponseEntity<PersonResponse> getPersonById(
								@RequestHeader(value = "Content-Type", required = false) String headerContentType,
								@RequestHeader(value = "Authorization", required = false) String headerAutorization,
								@RequestHeader(value = "Accept", required = false) String headerAccept, @PathVariable("rut") String rut) {
		
		Transformer util			= new Transformer();
		Validator validator			= new Validator();
		PersonResponse response;
		HttpHeaders responseHeaders	= new HttpHeaders();
		
		if (rut!=null)
			rut=rut.replace("K", "k");
		
		responseHeaders.set("Content-Type", "application/json");
		
		if (validator.validateRequestHeaders(headerContentType, headerAutorization, headerAccept)!=0)
			return new ResponseEntity<PersonResponse>(responseHeaders, HttpStatus.BAD_REQUEST);
		
		if (!validator.isValidRut(rut, false))
			return new ResponseEntity<PersonResponse>(responseHeaders, HttpStatus.BAD_REQUEST);

		rut=rut.replace(".", "");
		
		response				= util.personDbToPersonResponse(personDao.getPersonFromDbByRut(rut));
		
		if (response.getRut()==null)
			return new ResponseEntity<PersonResponse>(responseHeaders, HttpStatus.NOT_FOUND);

		return new ResponseEntity<PersonResponse>(response, responseHeaders, HttpStatus.OK);
	}
	
	@PostMapping(value="/people")
	public ResponseEntity<Void> createPerson(
								@RequestHeader(value = "Content-Type", required = false) String headerContentType,
								@RequestHeader(value = "Authorization", required = false) String headerAutorization,
								@RequestHeader(value = "Accept", required = false) String headerAccept, @RequestBody PersonRequest person) {

		Transformer util			= new Transformer();
		Validator validator			= new Validator();
		HttpHeaders responseHeaders	= new HttpHeaders();
		int statusValidation;
		boolean statusCreate;
		
		if (person.getRut()!=null)
			person.setRut(person.getRut().replace("K", "k"));
		
		responseHeaders.set("Content-Type", "application/json");
		
		if (validator.validateRequestHeaders(headerContentType, headerAutorization, headerAccept)!=0)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);
		
		statusValidation		= validator.validatePersonRequest(person, false);
		if (statusValidation!=0)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);

		person.setRut(person.getRut().replace(".", ""));
		statusCreate	=personDao.savePerson(util.personRequestToPersonDb(person));
		
		if (statusCreate)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.FORBIDDEN);
		
	}
	

	
	@PutMapping("/people/{rut}")
	public ResponseEntity<Void> updatePersonById(
								@RequestHeader(value = "Content-Type", required = false) String headerContentType,
								@RequestHeader(value = "Authorization", required = false) String headerAutorization,
								@RequestHeader(value = "Accept", required = false) String headerAccept, @PathVariable("rut") String rut, @RequestBody PersonRequest person) {
		
		Transformer util			= new Transformer();
		Validator validator			= new Validator();
		HttpHeaders responseHeaders	= new HttpHeaders();
		int statusValidation;
		boolean statusUpdate;
		
		if (person.getRut()!=null)
			person.setRut(person.getRut().replace("K", "k"));
		if (rut!=null)
			rut=rut.replace("K", "k");
		
		responseHeaders.set("Content-Type", "application/json");
		
		if (validator.validateRequestHeaders(headerContentType, headerAutorization, headerAccept)!=0)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);
		
		statusValidation = validator.validatePersonRequest(person, true);
		if (statusValidation!=0)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);

		
		if (!validator.isValidRut(rut, false))
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);

		rut=rut.replace(".", "");
		
		if (person.getRut()==null)
			person.setRut(rut);
		else {
			person.setRut(person.getRut().replace(".",  ""));
			if (!rut.contentEquals(person.getRut()))
				return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);
		}
		
		statusUpdate = personDao.updatePersinByRut(util.personRequestToPersonDb(person));
		if (statusUpdate)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
		else
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.FORBIDDEN);
	}
	
	
	
	
	@DeleteMapping("/people/{rut}")
	public ResponseEntity<Void> deletePersonById(
								@RequestHeader(value = "Content-Type", required = false) String headerContentType,
								@RequestHeader(value = "Authorization", required = false) String headerAutorization,
								@RequestHeader(value = "Accept", required = false) String headerAccept, @PathVariable("rut") String rut)
	{
		Validator validator			= new Validator();
		HttpHeaders responseHeaders	= new HttpHeaders();
		boolean statusDelete;
		
		if (rut!=null)
			rut=rut.replace("K", "k");
		
		responseHeaders.set("Content-Type", "application/json");
		
		if (validator.validateRequestHeaders(headerContentType, headerAutorization, headerAccept)!=0)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);
		
		if (!validator.isValidRut(rut, false))
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.BAD_REQUEST);

		rut=rut.replace(".", "");
		
		statusDelete=personDao.deletePersonByRut(rut);
		
		if (statusDelete)
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.OK);
		else
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.NOT_FOUND);
	}
	
	

	
}
