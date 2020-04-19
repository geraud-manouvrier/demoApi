package cl.geraud.demoApi.rs.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cl.geraud.demoApi.rs.model.PersonDb;

@Repository
public class PersonDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PersonDb getPersonFromDbByRut(String rut) {
		PersonDb personDb=new PersonDb();
		
		
		int rutInt;
		char rutDv;
		rutInt=Integer.parseInt(rut.split("-")[0]);
		rutDv=rut.split("-")[1].charAt(0);
		String sql = "SELECT rutint, rutdv, nameperson, lastname, age, course FROM public.person WHERE rutint=? and rutdv=?;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql,rutInt, rutDv);
		
		if(result.next()) {
			personDb.setRutInt(result.getInt("rutint"));
			personDb.setRutDv(result.getString("rutdv").charAt(0));
			personDb.setName(result.getString("nameperson"));
			personDb.setLastName(result.getString("lastname"));
			personDb.setAge(result.getInt("age"));
			personDb.setCourse(result.getString("course"));
			
		}
		
		return personDb;
	}
	
	public List<PersonDb> getPersons() {

		List<PersonDb> personsDb = new ArrayList<PersonDb>();
		
		
		String sql = "SELECT rutint, rutdv, nameperson, lastname, age, course FROM public.person ORDER BY rutint ASC;";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		while(result.next()) {
			PersonDb personDb=new PersonDb();
			personDb.setRutInt(result.getInt("rutint"));
			personDb.setRutDv(result.getString("rutdv").charAt(0));
			personDb.setName(result.getString("nameperson"));
			personDb.setLastName(result.getString("lastname"));
			personDb.setAge(result.getInt("age"));
			personDb.setCourse(result.getString("course"));
			personsDb.add(personDb);
			
		}
		
		return personsDb;
	}
	
	public boolean savePerson(PersonDb person) /* throws Exception */ {
		
		int affectedRows;
		String sql = "INSERT INTO public.person(rutint, rutdv, nameperson, lastname, age, course) VALUES (?, ?, ?, ?, ?, ?); ";
		affectedRows=jdbcTemplate.update(sql, person.getRutInt(), person.getRutDv(), 
								person.getName(), person.getLastName(), 
								person.getAge(), person.getCourse());
		
		return !(affectedRows==0);
	}
	
	public boolean updatePersinByRut(PersonDb person) {
		
		int affectedRows;
		
		String sql = "UPDATE public.person SET "+
				"nameperson=COALESCE(?,nameperson), lastname=COALESCE(?,lastname), "+
				"age=COALESCE(?, age), course=COALESCE(?,course) "+
				"WHERE rutint=? and rutdv=?;";
		
		Integer intTemp=null;
		if (person.getAge()!=0)
			intTemp=person.getAge();
		
		
		affectedRows=jdbcTemplate.update(sql, 
								person.getName(), person.getLastName(), 
								intTemp, person.getCourse(), 
								person.getRutInt(), person.getRutDv());
		
		return !(affectedRows==0);
	}
	
	
	
	public boolean deletePersonByRut(String rut) {
		
		int rutInt;
		char rutDv;
		int affectedRows;
		
		rutInt		= Integer.parseInt(rut.split("-")[0]);
		rutDv		= rut.split("-")[1].charAt(0);
		String sql	= "delete from public.person where rutint=? and rutdv=?;";
		affectedRows= jdbcTemplate.update(sql,rutInt, rutDv);
		
		return !(affectedRows==0);

	}
	

}
