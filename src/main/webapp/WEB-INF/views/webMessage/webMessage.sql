show tables;

create table webMessage (
	idx 	int not null auto_increment,		/*메세지 고유번호*/
	title varchar(100) not null,					/*메세지 제목*/
	content text not null,								/*메세지 내용*/
	sendId	varchar(20) not null, 				/*보내는 사람 아이디*/
	sendSw	char(1) default 's',					/*보낸메세지(s), 휴지통(g), 휴지통삭제(x) 표시*/
	sendDate datetime default now(),			/*메세지 보낸날짜*/
	receiveId	varchar(20) not null, 				/*받는 사람 아이디*/
	receiveSw	char(1) default 'n',					/*받은메세지(n), 읽은메세지(r), 휴지통삭제(x) 표시*/
	receiveDate datetime default now(),			/*메세지 받은날짜/읽은날짜 */
	primary key(idx),
	foreign key(sendId) references member(mid),
	foreign key(receiveId) references member(mid)
);

desc webMessage;
select * fom webMessage;

insert into webMessage values (default, '안녕 아톰아', '이번주 주말에 뭐하니?', 'tir8686',default,default,'atom',default,default);
insert into webMessage values (default, '안녕 관리자님', '제가 보낸 메세지 누락이 있습니다.', 'tir8686',default,default,'admin',default,default);
insert into webMessage values (default, '안녕 티르야', '주말에 공부해~~~', 'atom',default,default,'tir8686','r',default);
insert into webMessage values (default, '티르님', '확인해보겠습니다!', 'admin',default,default,'tir8686',default,default);
