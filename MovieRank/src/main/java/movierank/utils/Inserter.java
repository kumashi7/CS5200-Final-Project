package movierank.utils;

import java.sql.SQLException;
import java.util.List;

import movierank.dal.*;
import movierank.model.*;


public class Inserter {
	public static void main(String[] args) throws SQLException {
        
        
		// DAO instances.
		PersonsDao personDao = PersonsDao.getInstance();
		AliasDao aliasDao = AliasDao.getInstance();
		WriterDao writerDao = WriterDao.getInstance();
		HadRoleDao hadRoleDao = HadRoleDao.getInstance();
		MoviesDao movieranksDao = MoviesDao.getInstance();
		
		Movies movierank1 = new Movies("TT12345", "007", "movieranktype", "007", true, 2020, 0, 120);
		movierank1 = movieranksDao.create(movierank1);

		Movies getMovie = movieranksDao.getMovieByTitleId("TT12345");
		System.out.println("Get movierank by id: " + getMovie.getOriginal_title());
		
		movieranksDao.updateMovieEndyear(getMovie, 2022);
		System.out.println("Update movierank end year: " + getMovie.getEnd_year());
		
		movieranksDao.delete(movierank1);
		
		
		// INSERT objects from our model.
		Persons person = new Persons("b", "bruce", 1924, 2111);
		person = personDao.create(person);
		Persons person1 = new Persons("b1", "bruce1", 1928, 3333);
		person1 = personDao.create(person1);
		Persons person2 = new Persons("b2", "bruce2", 1929, 4444);
		person2 = personDao.create(person2);
	
		// READ.
		Persons p1 = personDao.getPersonByName_id("b");
		System.out.format("Reading person: n:%s b:%s d:%s \n",
			p1.getName_(), p1.getBirth_year(), p1.getDeath_year());
		
		
		Movies movierank2 = new Movies("TT12345", "007", "movieranktype", "007", true, 2020, 0, 120);
		movierank2 = movieranksDao.create(movierank2);
		
		Movies movieranknew = new Movies("new title", "009", "movieranktype", "00134", true, 20210, 0, 120);
		movieranknew = movieranksDao.create(movieranknew);
		
		Writer writer = new Writer("b4", "bruce4", 1929, 5555, movierank2);
		writerDao.create(writer);
		Writer w = writerDao.getWriterFromNameIdAndTitleId("TT12345", "b4");
		System.out.format("Reading writer: n:%s t:%s \n",
				w.getNameId(), w.getMovie().getTitle_id());
		writerDao.updateTitleId(w, "new title");
		w = writerDao.getWriterFromNameIdAndTitleId("new title", "b4");
		System.out.format("Reading writer: n:%s t:%s \n",
				w.getNameId(), w.getMovie().getTitle_id());
		writerDao.delete(w);
		
		
		Movies movierank3 = new Movies("TTnew", "002081", "movieranktype2", "jasper", true, 2045, 0, 120);
		movierank3 = movieranksDao.create(movierank3);
		Alias a1 = new Alias(movierank3, 1, "good title", "NA", "english", true);
		a1 = aliasDao.create(a1);
		aliasDao.getAliasFromTitleIdAndOrdering("TTnew", 1);
		System.out.format("Reading Alias: tid:%s o:%d t:%s r:%s, l:%s\n",
				a1.getMovie().getTitle_id(), a1.getOrdering(), a1.getTitle(), a1.getRegion(), a1.getLanguage());
		
		aliasDao.updateTitle(a1, "great new title");
		aliasDao.getAliasFromTitleIdAndOrdering("TTnew", 1);
		System.out.format("Reading Alias: tid:%s o:%d t:%s r:%s, l:%s\n",
				a1.getMovie().getTitle_id(), a1.getOrdering(), a1.getTitle(), a1.getRegion(), a1.getLanguage());
		aliasDao.delete(a1);
		
		HadRole h1 = new HadRole(movierank1, person, "a");
		h1 = hadRoleDao.create(h1);

		HadRole hadRole1 = hadRoleDao.getHadRoleByTitle_id("TT2");
	    List<HadRole> hadRole2 = hadRoleDao.getHadRoleByRole_("a");
	    System.out.println("Get hadRole by id: " + hadRole1.getRole_());
	    for(HadRole hadRole : hadRole2) {
	    	System.out.println("Get hadRole by id: " + hadRole.getRole_());
	    }

		hadRoleDao.delete(h1);
		
	}
}
