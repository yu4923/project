-- Create table
create table customers(
    customer_id varchar2(20) constraint customer_id_pk primary key,
    customer_pw varchar2(20) not null,
    customer_name varchar2(50) not null,
    contact_num varchar2(20)
);
insert into customers values('admin', 'admin', 'admin', null);

create table movies(
    movie_id number(10) constraint movie_id_pk primary key,
    title varchar2(50),
    director varchar2(50),
    runtime number(5)
);

create table timetable(
    screen_id number(10) constraint screen_id_pk primary key,
    movie_id number(10) not null,
    showing_date date not null,
    cinema varchar2(20) not null,
    constraint movie_id_fk foreign key(movie_id) references movies(movie_id)
);

create table orders(
    order_id number(10) constraint order_id_pk primary key,
    customer_id varchar2(20) not null,
    screen_id number(10) not null,
    num_people number(5) not null,
    showing_time varchar2(50) not null,
    order_date date not null,
    
    constraint screen_id_fk foreign key(screen_id) references timetable(screen_id),
    constraint customer_id_fk foreign key(customer_id) references customers(customer_id)
);

-- Create Sequence
create sequence movie_id_seq;
create sequence screen_id_seq;
create sequence order_id_seq;

-- insert movies
insert into movies values(movie_id_seq.nextval, '범죄도시2', '이상용', 106);
insert into movies values(movie_id_seq.nextval, '천박사 퇴마 연구소-설경의 비밀', '김성식', 98);
insert into movies values(movie_id_seq.nextval, '1947 보스톤', '강제규', 108);
insert into movies values(movie_id_seq.nextval, '거미집', '김지운', 132);
insert into movies values(movie_id_seq.nextval, '더 넌 2', '마이클 차베즈', 110);
insert into movies values(movie_id_seq.nextval, '30일', '남대중', 119);
insert into movies values(movie_id_seq.nextval, '크리에이터', '가렛 에드워즈', 133);

-- insert screen
insert into timetable values(screen_id_seq.nextval, 1, to_date('2022-05-18 11:40', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 3, to_date('2023-10-02 21:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-02 08:50', 'YYYY-MM-DD HH24:MI'), '부산');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-02 16:40', 'YYYY-MM-DD HH24:MI'), '대전');
insert into timetable values(screen_id_seq.nextval, 5, to_date('2023-10-02 22:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 4, to_date('2023-10-03 11:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-03 14:30', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-03 14:40', 'YYYY-MM-DD HH24:MI'), '부산');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-03 21:20', 'YYYY-MM-DD HH24:MI'), '대전');
insert into timetable values(screen_id_seq.nextval, 2, to_date('2023-10-04 09:00', 'YYYY-MM-DD HH24:MI'), '인천');
insert into timetable values(screen_id_seq.nextval, 2, to_date('2023-10-04 16:30', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 4, to_date('2023-10-04 17:40', 'YYYY-MM-DD HH24:MI'), '인천');
insert into timetable values(screen_id_seq.nextval, 3, to_date('2023-10-05 19:00', 'YYYY-MM-DD HH24:MI'), '광주');
insert into timetable values(screen_id_seq.nextval, 4, to_date('2023-10-05 15:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-05 15:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 2, to_date('2023-10-06 11:30', 'YYYY-MM-DD HH24:MI'), '대전');
insert into timetable values(screen_id_seq.nextval, 3, to_date('2023-10-06 15:30', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-06 16:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 3, to_date('2023-10-07 15:30', 'YYYY-MM-DD HH24:MI'), '대구');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-07 09:20', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 5, to_date('2023-10-07 16:40', 'YYYY-MM-DD HH24:MI'), '광주');
insert into timetable values(screen_id_seq.nextval, 5, to_date('2023-10-08 10:30', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-08 19:30', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 4, to_date('2023-10-08 20:10', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 5, to_date('2023-10-08 13:30', 'YYYY-MM-DD HH24:MI'), '부산');
insert into timetable values(screen_id_seq.nextval, 2, to_date('2023-10-09 12:40', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-09 16:20', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-09 23:40', 'YYYY-MM-DD HH24:MI'), '부산');
insert into timetable values(screen_id_seq.nextval, 2, to_date('2023-10-10 09:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 4, to_date('2023-10-10 14:00', 'YYYY-MM-DD HH24:MI'), '서울');
insert into timetable values(screen_id_seq.nextval, 6, to_date('2023-10-11 18:00', 'YYYY-MM-DD HH24:MI'), '부산');
insert into timetable values(screen_id_seq.nextval, 5, to_date('2023-10-11 20:30', 'YYYY-MM-DD HH24:MI'), '인천');

commit;
