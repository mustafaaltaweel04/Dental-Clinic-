drop database if exists Clinic;
create database Clinic;
use Clinic;

-- /-----------------------------------------------------------------------------------------------------

drop table if exists Patients; 
create table Patients(
P_name varchar(32),
P_id int AUTO_INCREMENT,
p_phonenumber varchar(12) ,
primary key (P_id)
);

-- /------------------------------------------------------------------------------------
drop table if exists Treatmenthistory; 
create table Treatmenthistory(
Ttype varchar(32),
Ttime time,
P_id int,
Tdate date,
Tname  varchar(32),
primary key (Tname,P_id),
foreign key (P_id) references Patients(P_id) on delete cascade
);

-- /-------------------------------------------------------------------------------------------------------------------
drop table if exists Diagnosis; 
create table Diagnosis(
Did int AUTO_INCREMENT,
Dtype varchar(32),
primary key(Did));


-- /--------------------------------------------------------------------
drop table if exists Diagnosis_patient; 
create table Diagnosis_patient(
Did int,
P_id int,
primary key(Did,P_id),
foreign key (P_id) references Patients(P_id),
foreign key (Did) references Diagnosis(Did)
);
--  /-----------------------------------------------------

drop table if exists Appointments; 
create table Appointments(
Appointment_id int AUTO_INCREMENT,
A_Date date,
A_time time,
P_id int,
R_id int,
D_id int,
primary key(Appointment_id),
foreign key (P_id) references Patients(P_id),
foreign key (D_id) references Doctor(D_id),
foreign key (R_id) references Room(R_id)
);

-- /---------------------------------------------------------------------
drop table if exists Finantial; 
create table Finantial(
F_id int AUTO_INCREMENT,
amount int ,
balance  int,
F_type varchar(32),
P_id int,
primary key(F_id),
foreign key(P_id) references Patients(P_id)
);

-- /-----------------------------------------------
drop table if exists Room; 
create table Room(
R_id int AUTO_INCREMENT,
service_type varchar(32),
d_id int,
primary key(R_id),
foreign key(d_id) references doctor(d_id)
);

-- /--------------------------------------------------

drop table if exists Doctor; 
create table  Doctor(
D_id int AUTO_INCREMENT,
D_name varchar(32),
D_speciality varchar(32),
D_phonenumber varchar(12),
C_id int,
foreign key (C_id) references Clinic(C_id),
primary key(D_id)
);

-- /----------------------------------------------------
drop table if exists Clinic; 
create table  Clinic(
C_id int AUTO_INCREMENT,
C_name varchar(32),
C_phonenumber Long,
D_id int,
primary key(C_id),
foreign key(d_id) references doctor(d_id)
);


-- /------------------------------------------------
drop table if exists Laboratory; 
create table  Laboratory(
L_id int,
C_id int,
L_name varchar(32) AUTO_INCREMENT,
L_phonenumber varchar(12) ,
L_service varchar(32),
foreign key (C_id) references Clinic(C_id),
primary key(L_id)
);

-- /-----------------------------------------------
drop table if exists Payment ; 
create table  Payment(
P_id int AUTO_INCREMENT,
L_id int,
P_amount_owns varchar(32),
P_amount_mustpay varchar(32),
foreign key (L_id) references Laboratory(L_id),
primary key(P_id)
);