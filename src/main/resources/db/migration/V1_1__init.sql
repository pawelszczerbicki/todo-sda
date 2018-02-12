create table if not exists users (
   id serial primary key ,
   login varchar(1024) not null,
   password varchar(1024) not null,
   created_at timestamp not null default now()
);

create table if not exists tasks(
  id serial primary key ,
  name varchar(1024) not null,
  created_at timestamp not null default now(),
  completed_at timestamp,
  completed_by int references users(id)
);

create table if not exists task_user(
  task_id int references tasks(id),
  user_id int references users(id),
  primary key (task_id, user_id)
)