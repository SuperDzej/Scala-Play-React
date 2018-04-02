# Users schema

# --- !Ups
CREATE TABLE "user" (
  "id" SERIAL PRIMARY KEY,
  "first_name" TEXT NOT NULL,
  "last_name" TEXT NOT NULL,
  "mobile" BIGINT NOT NULL,
  "email" TEXT NOT NULL,
  "password" TEXT NOT NULL
)

# --- !Downs
drop table "user"
