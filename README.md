# CollegeManagementSystem (under development)

This is an java based application which uses 2 tire architecture (client GUI and Derby Database).

Module : 

1. Admin: the admin can access all the tables in database schema. Admin can create new student which in turn triggers necessory event on the database.
2. Staff: Each staff is assigned with an subject. the staff can view the marks of assigned subject. and can alter the marks from the console.

DATABASE SCHEMA
![DbSchema](https://user-images.githubusercontent.com/52366077/123975324-dd126400-d9da-11eb-916b-8118e9dd7b62.jpg)



SCREENSHORTS: 

![image](https://user-images.githubusercontent.com/52366077/123999596-510b3700-d9f0-11eb-9eae-8442a22d8b01.png)

![image](https://user-images.githubusercontent.com/52366077/123999785-80ba3f00-d9f0-11eb-9622-01acb336579e.png)

![image](https://user-images.githubusercontent.com/52366077/123999686-68e2bb00-d9f0-11eb-94b0-95ce75f91eda.png)


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



