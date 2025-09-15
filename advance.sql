use companydb;
go

create table employees (
    id int primary key,
    name varchar(100),
    age int,
    department varchar(50),
    salary decimal(10, 2),
    email varchar(100),
    last_updated datetime
);
go

insert into employees (id, name, age, department, salary, email)
values
(1, 'john smith', 32, 'it', 75000.00, 'john.smith@company.com'),
(2, 'jane doe', 28, 'hr', 60000.00, 'jane.doe@company.com'),
(3, 'peter jones', 45, 'sales', 95000.00, 'peter.jones@company.com'),
(4, 'mary johnson', 38, 'hr', 60000.00, 'mary.johnson@company.com'),
(5, 'robert williams', 51, 'sales', 110000.00, 'robert.williams@company.com'),
(6, 'linda davis', 25, 'it', 80000.00, 'linda.davis@company.com'),
(7, 'paul johnsen', 42, 'sales', 95000.00, 'paul.johnsen@company.com'),
(8, 'david wilson', 30, 'it', 80000.00, 'david.wilson@company.com'),
(9, 'sarah brown', 34, 'hr', 55000.00, 'sarah.brown@company.com'),
(10, 'michael miller', 29, 'marketing', 68000.00, 'michael.miller@company.com');
go

create table departments (
    dept_id int primary key,
    dept_name varchar(50)
);
go

insert into departments (dept_id, dept_name)
values
(1, 'it'),
(2, 'hr'),
(3, 'sales'),
(4, 'marketing'),
(5, 'finance');
go

create table orders (
    order_id int primary key,
    order_date date
);
go

insert into orders (order_id, order_date)
values
(1, dateadd(month, -5, getdate())),
(2, dateadd(month, -5, getdate())),
(3, dateadd(month, -4, getdate())),
(4, dateadd(month, -4, getdate())),
(5, dateadd(month, -4, getdate())),
(6, dateadd(month, -3, getdate())),
(7, dateadd(month, -3, getdate())),
(8, dateadd(month, -2, getdate())),
(9, dateadd(month, -2, getdate())),
(10, dateadd(month, -1, getdate())),
(11, dateadd(month, -1, getdate())),
(12, getdate()),
(13, getdate()),
(14, getdate()),
(15, dateadd(month, -6, getdate()));
go

create table sales (
    sale_id int primary key,
    employee_id int,
    sale_amount decimal(10, 2),
    foreign key (employee_id) references employees(id)
);
go

insert into sales (sale_id, employee_id, sale_amount)
values
(1, 3, 5000.00),
(2, 3, 7500.00),
(3, 5, 12000.00),
(4, 5, 8000.00),
(5, 7, 6000.00);
go






create procedure sp_insertemployee
    @id int,
    @name varchar(100),
    @age int,
    @department varchar(50),
    @salary decimal(10, 2),
    @new_id int output
as
begin
    insert into employees (id, name, age, department, salary)
    values (@id, @name, @age, @department, @salary);
    set @new_id = @id;
end;
go

declare @inserted_id int;
exec sp_insertemployee 101, 'paul', 35, 'it', 75000, @inserted_id output;
select @inserted_id as newemployeeid;
go







create view vw_employeedepartmentsales as
select
    e.name as employeename,
    d.dept_name as departmentname,
    sum(s.sale_amount) as totalsales
from employees e
join departments d on e.department = d.dept_name
left join sales s on e.id = s.employee_id
group by e.name, d.dept_name;
go

select * from vw_employeedepartmentsales;
go






select department
from employees
group by department
having avg(salary) > 50000;




alter table employees
add last_updated datetime;
go

create trigger tr_updatelastupdated
on employees
after update
as
begin
    update employees
    set last_updated = getdate()
    from employees e
    inner join inserted i on e.id = i.id;
end;
go






select name
from employees
where name like '%john%';
go





alter table employees
add constraint uq_employees_email unique (email);
go




select e.name, d.dept_name
from employees e
join departments d on e.department = d.dept_name;

create index ix_employees_department on employees (department);
go
