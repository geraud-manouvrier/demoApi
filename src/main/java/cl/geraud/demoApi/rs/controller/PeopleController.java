package cl.geraud.demoApi.rs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Util.Transformer;
import Util.Validator;
import cl.geraud.demoApi.rs.dao.PersonDao;
import cl.geraud.demoApi.rs.model.request.PersonRequest;
import cl.geraud.demoApi.rs.model.response.PersonResponse;

@RestController
public class PeopleController {

	/*
	@Autowired
	private JdbcTemplate jdbcTemplate;
	*/
	@Autowired
	PersonDao personDao;
	
	
	@GetMapping("/people")
	public List<PersonResponse> getPersons()
	{
		List<PersonResponse> response	= new ArrayList<PersonResponse>();
		Transformer util			= new Transformer();
		//utilvalidateRut
		response				= util.personsDbToPersonsResponse(personDao.getPersons());
		
		//if response is null
		
	    return response;
	}
	
	@GetMapping("/people/{rut}")
	public ResponseEntity<PersonResponse> getPersonById(@PathVariable("rut") String rut) {
		
		PersonResponse response	= new PersonResponse();
		Transformer util		= new Transformer();
		
		Validator validator		= new Validator();
		
		if (!validator.isValidRut(rut, false)) {
			return new ResponseEntity<PersonResponse>(HttpStatus.BAD_REQUEST);
		}
		rut=rut.replace(".", "");
		
		response				= util.personDbToPersonResponse(personDao.getPersonFromDbByRut(rut));
		
		if (response.getRut()==null) {
			return new ResponseEntity<PersonResponse>(HttpStatus.NOT_FOUND);
		}
		
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping(value="/people")
	public void createPerson(@RequestBody PersonRequest person) /* throws Exception --- throws NoSuchAlgorithmException */ {

		Validator validator		= new Validator();
		int statusValidation	= validator.validatePersonRequest(person, false);
		if (statusValidation!=0) {
			return;
		}
		person.setRut(person.getRut().replace(".", ""));
		Transformer util		= new Transformer();
		boolean statusCreate	=personDao.savePerson(util.personRequestToPersonDb(person));
		
		if (statusCreate)
			return;
		else
			return;
		
	}
	

	
	@PutMapping("/people/{rut}")
	public void updatePersonById(@PathVariable("rut") String rut, @RequestBody PersonRequest person) {
		
		Validator validator		= new Validator();
		int statusValidation	= validator.validatePersonRequest(person, true);
		if (statusValidation!=0) {
			return;
		}
		
		if (!validator.isValidRut(rut, false)) {
			return;
		}
		rut=rut.replace(".", "");
		
		if (person.getRut()==null)
			person.setRut(rut);
		Transformer util			= new Transformer();
		boolean statusUpdate=personDao.updatePersinByRut(util.personRequestToPersonDb(person));
		if (statusUpdate)
			return;
		else
			return;
	}
	
	
	
	
	@DeleteMapping("/people/{rut}")
	public void deletePersonById(@PathVariable("rut") String rut)
	{
		boolean statusDelete=personDao.deletePersonByRut(rut);
		if (statusDelete)
			return;
		else
			return;
	}
	
	

	
	
//	
//	@PostMapping(value="/reporte-test")
//	public int listarTest(@RequestParam String usuario, @RequestParam String contrasena) throws NoSuchAlgorithmException{
//
//		//
//		String sql = "select count(*) as cant FROM public.testtb;";
//		
//		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
//		
//		int diff = -1;
//		
//		if(result.next()) {
//			diff = (int) result.getDouble("cant");
//			
//		}
//		
//		return diff;
//		
//	}
	
//	@GetMapping(value="/person/list/")
//	public int listPerson(@RequestParam(name="name", required = false, defaultValue = "Ahmed") String name) throws NoSuchAlgorithmException{
//
//		//
//		String sql = "select count(*) as cant FROM public.testtb;";
//		
//		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
//		
//		int diff = -1;
//		
//		if(result.next()) {
//			diff = (int) result.getDouble("cant");
//			
//		}
//		
//		return diff;
//		
//	}
	
	
	/*
	 * @RequestMapping(method = RequestMethod.GET) =>>>> @GetMapping
	 * 
	 * @RequestParam(required = false, defaultValue = "someValue", value="someAttr") String someAttr
	 * 
	 * @RequestMapping(
		  value = "/ex/foos", 
		  headers = { "key1=val1", "key2=val2" },
		  method = GET, 
		  headers = "Accept=application/json")
	 */
}
