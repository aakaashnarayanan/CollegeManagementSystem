# CollegeManagementSystem (under development)

This is an java based application which uses 2 tire architecture (client GUI and Derby Database).
Module : 
1. Admin: the admin can access all the tables in database schema. Admin can create new student which in turn triggers necessory event on the database.
2. Staff: Each staff is assigned with an subject. the staff can view once the marks of assigned subject. and can alter the marks from the console.

DATABASE SCHEMA
![DbSchema](https://user-images.githubusercontent.com/52366077/123975324-dd126400-d9da-11eb-916b-8118e9dd7b62.jpg)



SCREENSHORTS: 

![image](https://user-images.githubusercontent.com/52366077/123976496-d0424000-d9db-11eb-91f0-1c408f96972a.png)

![image](https://user-images.githubusercontent.com/52366077/123976642-eea83b80-d9db-11eb-81cd-870f6782ca95.png)

![image](https://user-images.githubusercontent.com/52366077/123976904-29aa6f00-d9dc-11eb-85f8-5ab0743157a7.png)


SQL QUERIES :
 
cms.department:  

create table cms.department(dpt_id int PRIMARY KEY,dpt_name varchar(50));

cms.student: 

create table cms.student(stu_id int PRIMARY KEY,stu_name varchar(30),dpt_id int, FOREIGN KEY(dpt_id) REFERENCES cms.department (dpt_id));

cms.staff:  

create table cms.staff(staff_id int PRIMARY KEY,staff_name varchar(30),dpt_id int, FOREIGN KEY(dpt_id) REFERENCES cms.department (dpt_id));

cms.mark: 

create table cms.mark(subject_id int,stu_id int,mark1 int,mark2 int,mark3 int,mark4 int,mark5 int,mark6 int,PRIMARY KEY(subject_id,stu_id), FOREIGN KEY(subject_id) REFERENCES cms.subject(subject_id),FOREIGN KEY(stu_id) REFERENCES cms.student(stu_id));

cms.subject:

create table cms.subject(subject_id int PRIMARY KEY,subject_name varchar(30),staff_id int, FOREIGN KEY(staff_id) REFERENCES cms.staff(staff_id));



