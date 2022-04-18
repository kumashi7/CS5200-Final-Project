# Create the schema if necessary.
CREATE SCHEMA IF NOT EXISTS MovieRank;
USE MovieRank;

DROP TABLE IF EXISTS Ratings;
DROP TABLE IF EXISTS PersonWorkedAs;
DROP TABLE IF EXISTS Principals;
DROP TABLE IF EXISTS HadRole;
DROP TABLE IF EXISTS Writers;
DROP TABLE IF EXISTS Directors;
DROP TABLE IF EXISTS MovieGenres;
DROP TABLE IF EXISTS AliasAttributes;
DROP TABLE IF EXISTS AliasType;
DROP TABLE IF EXISTS Alias;
DROP TABLE IF EXISTS Persons;
DROP TABLE IF EXISTS Movies;

CREATE TABLE Movies (
title_id VARCHAR(255) NOT NULL,
title_type VARCHAR(255),
primary_title VARCHAR(255),
original_title VARCHAR(255),
is_Adult BOOLEAN,
start_year INTEGER,
end_year INTEGER,
runtime_minutes INTEGER,
CONSTRAINT pk_Movies_title_id PRIMARY KEY (title_id)
);

CREATE TABLE Alias (
title_id VARCHAR(255),
ordering INTEGER,
title VARCHAR(255),
region VARCHAR(255),
language VARCHAR(255),
is_original_title BOOLEAN,
CONSTRAINT pk_Alias_title_idAndordering PRIMARY KEY (title_id, ordering),
CONSTRAINT fk_Alias_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE AliasType (
title_id VARCHAR(255),
ordering INTEGER,
type VARCHAR(255),
CONSTRAINT pk_AliasType_title_idAndordering PRIMARY KEY (title_id, ordering),
CONSTRAINT fk_AliasType_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE AliasAttributes (
title_id VARCHAR(255),
ordering INTEGER,
Attribute VARCHAR(255),
CONSTRAINT pk_AliasAttributes_title_idAndordering PRIMARY KEY (title_id, ordering),
CONSTRAINT fk_AliasAttribute_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE MovieGenres (
title_id VARCHAR(255),
genre VARCHAR(255),
CONSTRAINT pk_MovieGenres_title_idandgenre PRIMARY KEY (title_id, genre),
CONSTRAINT fk_MovieGenres_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Persons (
  name_id VARCHAR(255),
  name_ VARCHAR(255),
  birth_year INTEGER,
  death_year INTEGER,
  CONSTRAINT pk_Persons_name_id PRIMARY KEY (name_id)
);

CREATE TABLE Directors (
title_id VARCHAR(255),
name_id VARCHAR(255),
CONSTRAINT pk_Directors_title_idAndname_id PRIMARY KEY (title_id, name_id),
CONSTRAINT fk_Directors_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_Directors_name_id FOREIGN KEY (name_id)
    REFERENCES Persons(name_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE PersonWorkedAs (
  name_id VARCHAR(255),
  profession VARCHAR(255),
  CONSTRAINT pk_PersonWorkedAs_professionAndname_id PRIMARY KEY (profession, name_id),
  CONSTRAINT fk_PersonWorkedAs_name_id FOREIGN KEY (name_id)
    REFERENCES Persons(name_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE HadRole (
  title_id VARCHAR(255),
  name_id VARCHAR(255),
  role_ VARCHAR(255),
  
  CONSTRAINT pk_HadRole_role_Andtitle_idAndname_id PRIMARY KEY (role_, name_id, title_id),
  
  CONSTRAINT fk_HadRole_name_id FOREIGN KEY (name_id)
    REFERENCES Persons(name_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    
  CONSTRAINT fk_HadRole_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Writers (
  title_id VARCHAR(255),
  name_id VARCHAR(255),
  
  CONSTRAINT pk_Writers_name_idAndtitle_id PRIMARY KEY (name_id, title_id),
  
  CONSTRAINT fk_Writers_name_id FOREIGN KEY (name_id)
    REFERENCES Persons(name_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    
  CONSTRAINT fk_Writers_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Principals (
  title_id VARCHAR(255),
  ordering INTEGER,
  name_id VARCHAR(255),
  job_category VARCHAR(255),
  job VARCHAR(255),
  
  CONSTRAINT pk_Principals_orderingAndname_idAndtitle_id PRIMARY KEY (ordering, name_id, title_id),
  
  CONSTRAINT fk_Principals_name_id FOREIGN KEY (name_id)
    REFERENCES Persons(name_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
    
  CONSTRAINT fk_Principals_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Ratings(
  title_id VARCHAR(255) NOT NULL,
  average_rating DOUBLE NOT NULL,
  num_votes INTEGER NOT NULL,
  CONSTRAINT pk_Ratings_title_id_average_rating_num_votes PRIMARY KEY (title_id, average_rating, num_votes),

  CONSTRAINT fk_Ratings_title_id FOREIGN KEY (title_id)
    REFERENCES Movies(title_id)
    ON UPDATE CASCADE ON DELETE CASCADE
);