show tables;

create table schedule (
	idx int not null auto_increment,
	mid varchar(20) not null,
	sDate datetime not null,					/*일정 등록한 날짜*/
	part varchar(10) not null,				/*1.모임, 2.업무, 3.학습, 4.여행, 0.기타*/
	content text not null,						/*일정 상세 내역*/
	primary key(idx),
	foreign key(mid) references member(mid)
);

drop table schedule;
desc schedule;

select * from schedule where date_format(sDate, '%Y-%m') = '2025-03' and mid = 'admin'; 
select * from schedule where date_format(sDate, '%Y-%m') = '2025-03' and mid = 'admin' order by sDate; 
select * from schedule where date_format(sDate, '%Y-%m') = '2025-03' and mid = 'admin' order by sDate, part; 
select *,count(part) as partCnt from schedule where date_format(sDate, '%Y-%m') = '2025-03' and mid = 'admin' group by sDate order by sDate, part; 
select *,count(part) as partCnt from schedule where date_format(sDate, '%Y-%m') = '2025-03' and mid = 'admin' group by sDate,part order by sDate, part; 


insert into schedule values (default, 'admin', '2025-03-17', '학습', '프로젝트 기획 초안');
insert into schedule values (default, 'admin', '2025-03-30', '업무', 'B팀 프로젝트 초안 회의');
insert into schedule values (default, 'admin', '2025-03-30', '학습', '크롤링 배우는 날');
insert into schedule values (default, 'admin', '2025-04-04', '모임', '어느날 모임, 11시 청주체육관 기념식수');
insert into schedule values (default, 'admin', '2025-04-05', '모임', '클라이밍 21시 청주체육관');
insert into schedule values (default, 'admin', '2025-04-12', '학습', '4팀 프로젝트 회의');
insert into schedule values (default, 'admin', '2025-04-21', '학습', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default, 'admin', '2025-04-22', '여행', '제천 mt가는 날');
insert into schedule values (default, 'admin', '2025-04-22', '모임', 'mt 기념 뒷풀이');
insert into schedule values (default, 'admin', '2025-04-23', '모임', '어린이날 기념 마라톤 참석, 오전 11시 출발');
insert into schedule values (default, 'admin', '2025-04-28', '모임', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default, 'admin', '2025-05-01', '기타', '보일러 고치는 날');
insert into schedule values (default, 'tir8686', '2025-03-15', '학습', '프로젝트 기획 초안');
insert into schedule values (default, 'tir8686', '2025-03-31', '업무', 'A팀 프로젝트 초안 회의');
insert into schedule values (default, 'tir8686', '2025-04-05', '모임', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default, 'tir8686', '2025-04-06', '모임', '클라이밍 20시 청주체육관');
insert into schedule values (default, 'tir8686', '2025-04-11', '학습', '3팀 프로젝트 회의');
insert into schedule values (default, 'tir8686', '2025-04-20', '학습', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default, 'tir8686', '2025-04-23', '여행', '경주 여행 가는 날');
insert into schedule values (default, 'tir8686', '2025-04-24', '모임', '어린이날 기념 마라톤 참석, 오전 11시 출발');
insert into schedule values (default, 'tir8686', '2025-04-28', '모임', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default, 'tir8686', '2025-05-04', '기타', '집청소하는 날');
insert into schedule values (default, 'atom', '2025-04-25', '여행', '제주도 여행, 일정 9시부터 18시까지');
insert into schedule values (default, 'atom', '2025-04-27', '업무', '14시에 울돌목카페에서 미팅 예정');
insert into schedule values (default, 'atom', '2025-04-28', '업무', '11시 쭈꾸미집에서 미팅겸 회식 예정');
insert into schedule values (default, 'atom', '2025-05-03', '기타', '휴일 예정, 새로 산 빨래건조기 시험');
insert into schedule values (default, 'atom', '2025-04-05', '모임', '사계절 반찬 맛보기 복대동 모임');
insert into schedule values (default, 'atom', '2025-04-02', '기타', '3팀 단합대회, 19시 충북대 중문 커피숍');
insert into schedule values (default, 'atom', '2025-04-01', '모임', '만우절 기념 지인들과 저녁약속');
insert into schedule values (default, 'atom', '2025-04-07', '모임', '초등학교 동창회, 성안길 제일카페 18시');
insert into schedule values (default, 'atom', '2025-04-08', '학습', 'JAVA 컬렉션 프레임워크 세미나');
insert into schedule values (default, 'atom', '2025-04-10', '기타', '봄맞이 대청소');