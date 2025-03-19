show tables;

select * from member;

select mid from member where userDel='OK';

--날짜함수--
select now();
select year(now());
select month(now());
select day(now());
select hour(now());
select minute(now());
select second(now());
select week(now());
select weekday(now());
select weekday('2025-3-22');
select weekday('2025-3-23');

select day(now()),month(now()),day(now());
select day(now()) 년,month(now()) 월,day(now()) 일;

select concat(year(now()),'-',month(now()),'-',day(now())) as ymd;

-- 시간 연산
select second(now());
select date_add(now(), interval 10 second);

select minute(now());
select date_add(now(), interval 10 minute);

select hour(now());
select date_add(now(), interval 10 hour);

select day(now());
select date_add(now(), interval 10 day);

select month(now());
select date_add(now(), interval 10 month);

select year(now());
select date_add(now(), interval 10 year);

select hour(now());
select date_sub(now(), interval 10 hour);

select hour(now());
select date_add(now(), interval -10 hour);

-- 날짜 차이를 구하는 함수 : datediff / timestampdiff
-- 1. 앞에서 뒤를 뺌
select datediff(now(), '2025-3-18');
select datediff('2025-3-18', now());


-- 2. 뒤에서 앞을 뺌
select timestampdiff(minute, '2025-3-19 15:30:0', now());

select mid 아이디,startDate 가입일 from member;

-- 가입한 날로부터 오늘은 몇일이 지났는지를 출력?

select mid 아이디,startDate 가입일, datediff(now(), startDate) as 지난날수 from member;
select mid 아이디,startDate 가입일, datediff(now(), startDate) as 지난날수 from member where userDel='OK';

select mid ,startDate , datediff(now(), startDate) as deleteDiff from member where userDel='OK';
select mid 아이디,startDate 가입일, timestampdiff(day, startDate, now()) as 지난날수 from member;
