create table if not exists task_history (
   id serial primary key ,
   created_at timestamp not null default now()
);
