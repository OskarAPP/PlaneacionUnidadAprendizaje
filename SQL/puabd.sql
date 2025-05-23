drop database if exists pua;
create database pua CHARACTER SET utf8 COLLATE utf8_general_ci;
use pua;

create table FACULTAD
(
	id_facultad int primary key auto_increment,
	facultad varchar(100),
	id_director int(100) unique,
	id_secretario_academico int (100) unique,
    micrositio varchar(200)
)engine=InnoDB;

create table ACADEMIA
(
	id_academia int primary key auto_increment,
	nombre_academia varchar(250),
	id_presidente int(100) unique,
	id_secretario int(100) unique,
	id_facultad int(100),
	foreign key(id_facultad) references FACULTAD(id_facultad)
)engine=InnoDB;

create table CARRERA
(
	id_carrera int primary key auto_increment,
	id_facultad int (100),
	nombre_carrera varchar(250),
	plan_estudios int(4),
	id_coordinador int(100), 
	foreign key (id_facultad) references FACULTAD(id_facultad)
)engine=InnoDB;

create table AREAM
(
	id_area int primary key auto_increment,
	area varchar(250)
)engine=InnoDB;

create table NUCLEOM
(
	id_nucleo int primary key auto_increment,
	nucleo varchar(250)
)engine=InnoDB;

create table TIPOM
(
	id_tipo int primary key auto_increment,
	tipo varchar(250)
)engine=InnoDB;

create table MATERIA
(
	id_materia int primary key auto_increment,
	id_area int,
	id_nucleo int,
	id_tipo int,
	nombre_materia varchar(250),
	creditos_totales int,
	horas_totales int,
	horas_teoricas int,
	horas_practicas int,
	art57 char(2),
	foreign key (id_area) references AREAM(id_area),
	foreign key (id_nucleo) references NUCLEOM(id_nucleo),
	foreign key (id_tipo) references TIPOM(id_tipo)
)engine=InnoDB;

create table CARRERA_MATERIA
(
	id_carrera int,
	id_materia int,
	primary key(id_carrera, id_materia),
	foreign key (id_carrera) references CARRERA(id_carrera),
	foreign key (id_materia) references MATERIA(id_materia)
)engine=InnoDB;

create table ACADEMIA_MATERIA
(
	id_materia int,
	id_academia int,
	primary key (id_materia, id_academia),
	foreign key (id_materia) references MATERIA(id_materia),
	foreign key (id_academia) references ACADEMIA(id_academia)
)engine=InnoDB;

create table AMBIENTE
(
	 id_ambiente int primary key auto_increment,
	 ambiente varchar(250)
)engine=InnoDB;

create table MATERIAL
(
	id_material int primary key auto_increment,
	material varchar (250)

)engine=InnoDB;

create table PUA 
(
	id_pua int primary key auto_increment,
	id_materia int(100),
	acreditacion_competencias int,
	competencia_uda text,
	competencia_formacion text,
	planEstudio int, 
	estado tinyint(1) not null,
	autorizado tinyint(1) not null,
	foreign key (id_materia) references MATERIA(id_materia)
)engine=InnoDB;

create table pua_version(
    id int primary key auto_increment not null,
    id_Pua int not null,
    pua longblob,
    version int not null,
    materia varchar(100) not null,
    planEstudio int not null,
    foreign key(id_Pua) references PUA(id_Pua)
)engine=InnoDB;

create table EVALUACION_FINAL
(
	id_evaluacion_final int primary key auto_increment,
	evaluacion_final varchar(50),
	ponderacion int,
	id_pua int,
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

#delete from EVALUACION_COMPETENCIAS where ponderacion = 60;

create table EVALUACION_COMPETENCIAS
(
	id_evaluacion_competencias int primary key auto_increment,
	evaluacion_final varchar(50),
	ponderacion int,
	id_pua int,
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

create table PERFILACAD
(
	id_perfil_acad int primary key auto_increment,
    perfil_acad text,
    id_pua int,
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

create table PERFILPROF
(
	id_perfil_prof int primary key auto_increment,
    perfil_prof text,
    id_pua int,
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

create table PERFILDOC
(
	id_perfil_doc int primary key auto_increment,
    perfil_doc text,
    id_pua int,
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

create table SUBCOMPETENCIA(
	id_subcompetencia int primary key auto_increment,
	id_pua int,
	num_subcompetencia int,
	subcompetencia text,
	sesiones int(100),
	ponderacion int(100),
	parcial int (1),
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

create table SUBCOMPETENCIA_AMBIENTE (
	id_subcompetencia int,
	id_ambiente int,
	foreign key (id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia),
	foreign key (id_ambiente) references AMBIENTE(id_ambiente)
)engine=InnoDB;

create table SUBCOMPETENCIA_MATERIAL(
	id_subcompetencia int,
	id_material int,
	foreign key (id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia),
	foreign key (id_material) references MATERIAL(id_material)
)engine=InnoDB;


create table TEMA
(
	id_tema int primary key auto_increment,
	tema varchar(250),
	numero_tema int,
	id_subcompetencia int,
	foreign key(id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia)
)engine=InnoDB;


create table SUBTEMA
(
	id_subtema int primary key auto_increment,
	id_tema int,
	subtemas varchar(250),
	numero_subtemas int,
	foreign key(id_tema) references TEMA(id_tema)
)engine=InnoDB;

create table TIPOBIB
(
	id_tipo_bibliografia int primary key auto_increment,
	tipo varchar(100)
)engine=InnoDB;

INSERT INTO `pua`.`TIPOBIB` (`tipo`) VALUES ('Básica');
INSERT INTO `pua`.`TIPOBIB` (`tipo`) VALUES ('Complementaria');

create table EDITORIAL
(
	id_editorial int primary key auto_increment,
	editorial varchar (50) not null
)engine=InnoDB;

create table LIBRO
(
	id_libro int primary key auto_increment,
	libro varchar(255),
	id_editorial int not null,
	autor_principal varchar(150),
	foreign key (id_editorial) references EDITORIAL(id_editorial)
)engine=InnoDB;

create table BIBLIOGRAFIA_PUA
(
	id_pua int,
	id_libro int,
	id_tipo_bibliografia int,
	foreign key(id_pua) references PUA (id_pua),
	foreign key(id_libro) references LIBRO(id_libro),
	foreign key(id_tipo_bibliografia) references TIPOBIB(id_tipo_bibliografia)
)engine=InnoDB;

create table BIBLIOGRAFIA_SUBCOMPETENCIA
(
	id_subcompetencia int,
    id_libro int,
    foreign key(id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia),
    foreign key(id_libro) references LIBRO(id_libro)
)engine=InnoDB;


create table CRITERIO
(
	id_criterio int primary key auto_increment,
	criterio text,
    id_subcompetencia int,
    foreign key (id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia)
)engine=InnoDB;

create table EVIDENCIA
(
	id_evidencia int primary key,
	evidencia varchar(250)
)engine=InnoDB;

create table EVIDENCIA_SUBCOMPETENCIA 
(
	id_subcompetencia int,
	id_evidencia int,
    ponderacion int,
	foreign key(id_evidencia) references EVIDENCIA(id_evidencia),
    foreign key(id_subcompetencia) references SUBCOMPETENCIA (id_subcompetencia)
)engine=InnoDB;

create table DOCENTE
(
	id_docente int primary key auto_increment not null,
	prefijo varchar(10),
	nombre varchar(50),
	apellido_paterno varchar(50),
	apellido_materno varchar(50),
	correo varchar(100) unique
)engine=InnoDB;

create table ACCESO(
    id_acceso int primary key auto_increment,
    id_docente int not null,
    correo varchar(100) unique,
	pass varchar(10) not null,
    rol varchar(30) not null,
	estado tinyint(1) not null,
    foreign key(id_docente) references DOCENTE(id_docente)
)engine=InnoDB;

create table DOCENTE_MATERIA
(
	id_docente int not null,
	id_materia int not null,
	primary key (id_docente, id_materia),
	foreign key (id_docente) references DOCENTE(id_docente),
	foreign key (id_materia) references MATERIA(id_materia)
)engine=InnoDB;

create table PUA_DOCENTE
(
	id_docente int not null,
	id_pua int not null,
	primary key (id_docente, id_pua),
	foreign key (id_docente) references DOCENTE(id_docente),
	foreign key (id_pua) references PUA(id_pua)
)engine=InnoDB;

create table DOCENTE_FACULTAD
(
	id_docente int(100),
	id_facultad int(100),
	primary key (id_docente, id_facultad),
	foreign key (id_docente) references DOCENTE(id_docente),
	foreign key (id_facultad) references FACULTAD(id_facultad)
)engine=InnoDB;

create table COMPETENCIAGEN
(
	id_generica int primary key auto_increment,
	competencia_generica varchar (250)
)engine=InnoDB;

create table COMPETENCIAESP
(
	id_especifica int primary key auto_increment,
	competencia_especifica varchar(250),
	id_carrera int,
	foreign key (id_carrera) references CARRERA(id_carrera)
)engine=InnoDB;

create table PUA_COMPETENCIAGEN
(
	id_pua int,
	id_generica int,
	primary key (id_pua, id_generica),
	foreign key (id_pua) references PUA(id_pua),
	foreign key (id_generica) references COMPETENCIAGEN (id_generica)
)engine=InnoDB;

create table PUA_COMPETENCIAESP
(
	id_pua int,
	id_especifica int,
	primary key (id_pua, id_especifica),
	foreign key (id_pua) references PUA(id_pua),
	foreign key (id_especifica) references COMPETENCIAESP(id_especifica)
)engine=InnoDB;

create table ACTIVIDADDOC
(
	id_actividad_docente int primary key auto_increment,
	actividad_docente text,
    rol_actividad varchar(10),
	id_subcompetencia int,
	foreign key(id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia)
)engine=InnoDB;

create table ACTIVIDADAL
(
	id_actividad_alumno int primary key auto_increment,
	actividad_alumno text,
    rol_actividad varchar(10),
	id_subcompetencia int,
	foreign key(id_subcompetencia) references SUBCOMPETENCIA(id_subcompetencia)
)engine=InnoDB;

INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Enfermería');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Medicina');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Odontología');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Ciencias Químico Biológicas');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Ciencias Sociales');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Contaduría y Administración');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Derecho');
INSERT INTO `pua`.`FACULTAD` (`facultad`) VALUES ('Humanidades');
INSERT INTO `pua`.`FACULTAD` (`facultad`,`id_director`,`id_secretario_academico`) VALUES ('Ingeniería','1','2');

#insertar carreras de Ingenería
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`,`id_coordinador`) VALUES ('9', 'Ingeniería en Sistemas Computacionales', '2009','4');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`,`id_coordinador`) VALUES ('9', 'Ingeniería en Mecatrónica', '2009','5');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`,`id_coordinador`) VALUES ('9', 'Ingeniería en Energía', '2009','7');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`,`id_coordinador`) VALUES ('9', 'Ingeniería Civil y Administración', '2010','6');
#insertar carreras de Enfermeria
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('1', 'Licenciatura en Enfermería', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('1', 'Licenciatura en Fisioterapia', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('1', 'Licenciatura en Gerontología', '2009');
#insertar carreras Medicina
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('2', 'Licenciatura en Médico Cirujano', '2011');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('2', 'Licenciatura en Nutrición', '2009');
#insertar carrera odontologia
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('3', 'Licenciatura en Cirujano Dentista', '2009');
#insertar carreras ciencias quim bio
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('4', 'Químico Farmacéutico Biólogo', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('4', 'Biología', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('4', 'Ingeniería Bioquímica Ambiental', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('4', 'Licenciatura en Ciencia y Tecnología de Alimentos', '2009');
#insertar carreras ciencias sociales
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('5', 'Licenciatura en Ciencias Politicas y Administración Pública', '2013');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('5', 'Licenciatura en Economía', '2009');
#insertar carreras contaduria y admon
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('6', 'Licenciatura en Contaduría', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('6', 'Licenciatura en Administracíon y Finanzas', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('6', 'Licenciatura en Administración de PYMES', '2013');
#insertar carreras derecho
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('7', 'Licenciatura en Derecho', '2009');
#insertar carreras humanidades
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('8', 'Licenciatura en Historia', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('8', 'Licenciatura en Literatura', '2009');
INSERT INTO `pua`.`CARRERA` (`id_facultad`, `nombre_carrera`, `plan_estudios`) VALUES ('8', 'Licenciatura en Psicología', '2009');

#insertar al catalogo de ACADEMIA
INSERT INTO `pua`.`ACADEMIA` (`nombre_academia`,`id_presidente`,`id_secretario`,`id_facultad`) VALUES ('Básicas','12','13',9);
INSERT INTO `pua`.`ACADEMIA` (`nombre_academia`,`id_presidente`,`id_secretario`,`id_facultad`) VALUES ('Ing. CyA','14','15',9);
INSERT INTO `pua`.`ACADEMIA` (`nombre_academia`,`id_presidente`,`id_secretario`,`id_facultad`) VALUES ('Ing. Energía','16','17',9);
INSERT INTO `pua`.`ACADEMIA` (`nombre_academia`,`id_presidente`,`id_secretario`,`id_facultad`) VALUES ('Ing. ISC','11','10',9);
INSERT INTO `pua`.`ACADEMIA` (`nombre_academia`,`id_presidente`,`id_secretario`,`id_facultad`) VALUES ('Ing. Mecatrónica','18','19',9);
INSERT INTO `pua`.`ACADEMIA` (`nombre_academia`,`id_presidente`,`id_secretario`,`id_facultad`) VALUES ('Administración',null,null,6);

#insertar areas de materia
INSERT INTO `pua`.`AREAM` (`area`) VALUES ('Ciencias Básicas y Matemáticas');
INSERT INTO `pua`.`AREAM` (`area`) VALUES ('Ciencias de la Ingeniería');
INSERT INTO `pua`.`AREAM` (`area`) VALUES ('Ingeniería Aplicada');
INSERT INTO `pua`.`AREAM` (`area`) VALUES ('Sociedad y Humanidades');
INSERT INTO `pua`.`AREAM` (`area`) VALUES ('Otros Cursos');
INSERT INTO `pua`.`AREAM` (`area`) VALUES ('Administración');

#insertar al catalogo nucleos
INSERT INTO `pua`.`NUCLEOM` (`nucleo`) VALUES ('Básico');
INSERT INTO `pua`.`NUCLEOM` (`nucleo`) VALUES ('Sustantivo');
INSERT INTO `pua`.`NUCLEOM` (`nucleo`) VALUES ('Integral');

#insertar tipo
INSERT INTO `pua`.`TIPOM` (`tipo`) VALUES ('Obligatorio');
INSERT INTO `pua`.`TIPOM` (`tipo`) VALUES ('Optativo');

#insertando algunas materias
INSERT INTO `pua`.`MATERIA` (`id_area`, `id_nucleo`, `id_tipo`, `nombre_materia`, `creditos_totales`, `horas_totales`, `horas_teoricas`, `horas_practicas`, `art57`) VALUES ('2', '2', '2', 'Lenguaje de Programación I', '4', '4', '2', '2', 'NO' );
INSERT INTO `pua`.`MATERIA` (`id_area`, `id_nucleo`, `id_tipo`, `nombre_materia`, `creditos_totales`, `horas_totales`, `horas_teoricas`, `horas_practicas`, `art57`) VALUES ('1', '2', '1', 'Topografía', '8', '6', '3', '3', 'SI' );
INSERT INTO `pua`.`MATERIA` (`id_area`, `id_nucleo`, `id_tipo`, `nombre_materia`, `creditos_totales`, `horas_totales`, `horas_teoricas`, `horas_practicas`, `art57`) VALUES ('2', '2', '1', 'Electricidad y Magnetismo', '6', '6', '3', '3', 'NO' );
INSERT INTO `pua`.`MATERIA` (`id_area`, `id_nucleo`, `id_tipo`, `nombre_materia`, `creditos_totales`, `horas_totales`, `horas_teoricas`, `horas_practicas`, `art57`) VALUES ('1', '1', '1', 'Estática', '3', '3', '2', '1', 'NO' );
INSERT INTO `pua`.`MATERIA` (`id_area`, `id_nucleo`, `id_tipo`, `nombre_materia`, `creditos_totales`, `horas_totales`, `horas_teoricas`, `horas_practicas`, `art57`) VALUES ('1', '1', '1', 'Cálculo Diferencial', '4', '4', '2', '2', 'NO');
INSERT INTO `pua`.`MATERIA` (`id_area`, `id_nucleo`, `id_tipo`, `nombre_materia`, `creditos_totales`, `horas_totales`, `horas_teoricas`, `horas_practicas`, `art57`) VALUES ('2', '2', '1', 'Compiladores', '4', '4', '2', '2', 'NO');

#insertando algunas materias a varias carreras
insert into CARRERA_MATERIA values(1,1); #logica
insert into CARRERA_MATERIA values(4,2); #topo
insert into CARRERA_MATERIA values(3,3); #electr
insert into CARRERA_MATERIA values(2,4); #est
insert into CARRERA_MATERIA values(1, 5); 
insert into CARRERA_MATERIA values(2, 5);
insert into CARRERA_MATERIA values(3, 5); #calculo
insert into CARRERA_MATERIA values(1, 6);

#Vinculando las academias con sus respectivas materias
insert into ACADEMIA_MATERIA values(1,4); #Lenguaje-ISC
insert into ACADEMIA_MATERIA values(2,2); #Topo-ICA
insert into ACADEMIA_MATERIA values(3,5); #Electricidad-IM
insert into ACADEMIA_MATERIA values(4,5); #Estatica-IM
insert into ACADEMIA_MATERIA values(5,1); #Caculo dif-Basicas
insert into ACADEMIA_MATERIA values(6,4); #Compiladores-ISC


#algunas competencias genericas

INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Conocimiento de la lengua extranjera');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('La utilización de las TIC´s en el ámbito profesional');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Habilidades de investigación');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Habilidades cognitivas');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Capacidad individual');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Capacidades metodológicas');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Capacidad de organización');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Sensibilidad para temas medioambientales');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Capacidad de liderazgo');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Destrezas sociales');
INSERT INTO `pua`.`COMPETENCIAGEN` (`competencia_generica`) VALUES ('Compromiso social');

#competencias especificas
INSERT INTO `pua`.`COMPETENCIAESP` (`competencia_especifica`,`id_carrera`) VALUES ('Diseñar, inplantar y operar soluciones técnologicas controladas mediante sistemas computacionales.','1');
INSERT INTO `pua`.`COMPETENCIAESP` (`competencia_especifica`,`id_carrera`) VALUES ('Diseñar, y construir sistemas y componentes de software aplicando las técnicas de los sistemas inteligentes en cualquier ámbito de aplicación.','1');

#libros editorial
INSERT INTO `pua`.`EDITORIAL` (`editorial`) VALUES ('McGraw Hill');
INSERT INTO `pua`.`EDITORIAL` (`editorial`) VALUES ('Iberoamérica');
INSERT INTO `pua`.`EDITORIAL` (`editorial`) VALUES ('PEARSON');
INSERT INTO `pua`.`EDITORIAL` (`editorial`) VALUES ('Wiley');
INSERT INTO `pua`.`EDITORIAL` (`editorial`) VALUES ('Thomson');

INSERT INTO `pua`.`LIBRO` (`libro`, `id_editorial`, `autor_principal`) VALUES ('Introducción a la programación con JAVA', '1', 'John S. Dean, Raymond H. Dean');
INSERT INTO `pua`.`LIBRO` (`libro`, `id_editorial`, `autor_principal`) VALUES ('Java Cómo Programar', '3', 'P. J. Deitel, H. M. Deitel');
INSERT INTO `pua`.`LIBRO` (`libro`, `id_editorial`, `autor_principal`) VALUES ('Cálculo', '1', 'Ron Larson, Bruce H. Edwards');
INSERT INTO `pua`.`LIBRO` (`libro`, `id_editorial`, `autor_principal`) VALUES ('BIG JAVA Early Objects', '4', 'Cay Horstmann');
INSERT INTO `pua`.`LIBRO` (`libro`, `id_editorial`, `autor_principal`) VALUES ('Compiladores, principios, técnicas y herramientas', '3', 'Alfred V. Aho, Monica S. Lam, Ravi Sethi, Jeffrey D. Ullman');
INSERT INTO `pua`.`LIBRO` (`libro`, `id_editorial`, `autor_principal`) VALUES ('Construccion de compiladores, principios y práctica', '5', 'Louden, Kenneth C.');


#insertar al catalago de docentes

INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('M. en C.','Guadalupe Manuel','Estrada','Segovia','gmestrad@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('M. en C','Carlos Alfonso','Chavez','Arias','cachavez@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('M. en I.','Juan Carlos','Ovando','Sierra','jcovando@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtra','Nancy Geogina','Ortiz','Cuevas','nagortiz@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Dr.','Hector Manuel','Quej','Cosgaya','hecmquej@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtra','Gabriela Patricia','Aldana','Narvaez','gpaldana@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtro.','Roger','Sanchez','Parrao','rmsanche@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('L.A.','Ivan del Jesus','Maldonado','Palomo','jmaldon@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('M. en. C.','Juan Jesús','Moncada','Bolón','jjmoncad@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtra','Diana Concepción','Mex','Alvarez','diancmex@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtro.','Enrique','Perera','Abreu','enperera@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Dr.','Francisco R.','Lezama','Zárraga','frlezama@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtra.','Alejandra del C.','Castro','Gongora','accastro@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtro.','Francisco','Barrera','Lao','fjbarrer@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtro.','Jorge A.','Berzunza','Valladares','jaberzun@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Dr.','Jorge de Jesús','Chan','González','jorjchan@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Ing.','Hugo','Rodríguez','Lara','hrodrigu@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Dr.','José Ruben','Lagunas','Jimenez','jrlaguna@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Dr.','Víctor Manuel del Jesús','Moo','Yam','victmmoo@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Mtro.','Daniel Alberto','Mendoza','Islas','Daniel@uacam.mx');
INSERT INTO `pua`.`DOCENTE` (`prefijo`,`nombre`,`apellido_paterno`,`apellido_materno`,`correo`) VALUES ('Ing.','Jorge Luis','Chuc','López','jorlchuc@uacam.mx');

#insertar acceso de docentes

INSERT INTO ACCESO VALUES (1,1,'gmestrad@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (2,2,'cachavez@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (3,3,'jcovando@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (4,4,'nagortiz@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (5,5,'hecmquej@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (6,6,'gpaldana@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (7,7,'rmsanche@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (8,8,'jmaldon@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (9,9,'jjmoncad@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (10,10,'diancmex@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (11,11,'enperera@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (12,12,'frlezama@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (13,13,'accastro@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (14,14,'fjbarrer@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (15,15,'jaberzun@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (16,16,'jorjchan@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (17,17,'hrodrigu@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (18,18,'jrlaguna@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (19,19,'victmmoo@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (20,20,'Daniel@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (21,21,'jorlchuc@uacam.mx','12345678','docente',1);
INSERT INTO ACCESO VALUES (NULL, '1', 'gmestrad@uacam.ms', '12345678', 'administrador', '1');

#Asignacion de facultades a los Docentes

INSERT INTO DOCENTE_FACULTAD values (1,9);
INSERT INTO DOCENTE_FACULTAD values (2,9);
INSERT INTO DOCENTE_FACULTAD values (3,9);
INSERT INTO DOCENTE_FACULTAD values (4,9);
INSERT INTO DOCENTE_FACULTAD values (5,9);
INSERT INTO DOCENTE_FACULTAD values (6,9);
INSERT INTO DOCENTE_FACULTAD values (7,9);
INSERT INTO DOCENTE_FACULTAD values (8,9);
INSERT INTO DOCENTE_FACULTAD values (9,9);
INSERT INTO DOCENTE_FACULTAD values (10,9);
INSERT INTO DOCENTE_FACULTAD values (11,9);
INSERT INTO DOCENTE_FACULTAD values (12,9);
INSERT INTO DOCENTE_FACULTAD values (13,9);
INSERT INTO DOCENTE_FACULTAD values (14,9);
INSERT INTO DOCENTE_FACULTAD values (15,9);
INSERT INTO DOCENTE_FACULTAD values (16,9);
INSERT INTO DOCENTE_FACULTAD values (17,9);
INSERT INTO DOCENTE_FACULTAD values (18,9);
INSERT INTO DOCENTE_FACULTAD values (19,9);
INSERT INTO DOCENTE_FACULTAD values (19,1);
INSERT INTO DOCENTE_FACULTAD values (21,9);

INSERT INTO `pua`.`AMBIENTE` (`ambiente`) VALUES ('Salón de clases');
INSERT INTO `pua`.`AMBIENTE` (`ambiente`) VALUES ('Biblioteca');
INSERT INTO `pua`.`AMBIENTE` (`ambiente`) VALUES ('Independiente');
INSERT INTO `pua`.`AMBIENTE` (`ambiente`) VALUES ('Laboratorio');

INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('1', 'Solución de problemas y ejercicios');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('2', 'ExaDes');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('3', 'Otros exámenes');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('4', 'Reportes o informes');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('5', 'Mapas o representaciones gráficas');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('6', 'Diseños, modelos y proyectos');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('7', 'Exposiciones presenciales');
INSERT INTO `pua`.`EVIDENCIA` (`id_evidencia`, `evidencia`) VALUES ('8', 'Prácticas de laboratorio o campo');

INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Pizarrón');
INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Proyector');
INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Suite de Office');
INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Presentación multimedia');
INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Apuntes del profesor');
INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Equipos de cómputo');
INSERT INTO `pua`.`MATERIAL` (`material`) VALUES ('Software de Desarrollo');


create table tipo(
	idTipo int not null AUTO_INCREMENT PRIMARY KEY,
    tipo varchar(50) not null
)engine=InnoDB;

create table tipo_docente(
	id int not null auto_increment primary key,
	idTipo int not null REFERENCES tipo(idTipo),
    id_docente int not null REFERENCES docente(id_docente),
    idFacultad int REFERENCES facultad(id_facultad),
    idCarrera int REFERENCES carrera(id_carrera),
    idAcademia int references academia(id_academia)
)engine=InnoDB;


INSERT INTO tipo VALUES (1,'Director de facultad'),
(2,'Secretario academico'),
(3,'Coordinador de carrera'),
(4,'Presidente de academia'),(5,'Secretario de academia'),(6,'Docente');

INSERT INTO tipo_docente VALUES (null,1,1,9,null,null),(null,2,2,9,null,null);
INSERT INTO tipo_docente VALUES (null,3,4,9,1,null),(null,3,5,9,2,null),(null,3,7,9,3,null),(null,3,6,9,4,null);
INSERT INTO tipo_docente VALUES (null,4,12,9,null,1),(null,5,13,9,null,1),(null,4,14,9,null,2),(null,5,15,9,null,2),(null,4,16,9,null,3),(null,5,17,9,null,3),(null,4,11,9,null,4),(null,5,10,9,null,4),(null,4,18,9,null,5),(null,5,19,9,null,5);
INSERT INTO tipo_docente VALUES (null,6,3,9,null,null),(null,6,8,9,null,null),(null,6,9,9,null,null),(null,6,20,9,null,null),(null,6,19,1,null,null),(null,6,21,9,null,null);

insert into materia values(7,2,2,1,'Investigación de operaciones I',3,3,2,1,'NO');
insert into materia values(8,2,2,1,'Investigación de operaciones II',3,3,2,1,'NO');
insert into materia values(9,2,2,1,'Sistemas I',3,3,2,1,'NO');
insert into materia values(10,2,2,1,'Sistemas II',3,3,2,1,'NO');

insert into academia_materia values(7,4);
insert into academia_materia values(8,4);
insert into academia_materia values(9,4);
insert into academia_materia values(10,4);

insert into carrera_materia values (1,7);
insert into carrera_materia values (1,8);
insert into carrera_materia values (1,9);
insert into carrera_materia values (1,10);

insert into docente_materia values(5,1);
INSERT INTO docente_materia VALUES (1,1);
INSERT INTO docente_materia VALUES (21,6);
insert into docente_materia values(10,7);
insert into docente_materia values(10,8);
insert into docente_materia values(21,9);
insert into docente_materia values(21,10);