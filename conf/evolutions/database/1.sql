# Users schema

# --- !Ups
CREATE TABLE "user" (
  "id" SERIAL PRIMARY KEY,
  "first_name" TEXT NOT NULL,
  "last_name" TEXT NOT NULL,
  "email" VARCHAR(50) UNIQUE NOT NULL,
  "username" VARCHAR(50) UNIQUE NOT NULL,
  "isVerified" BOOLEAN NOT NULL,
  "isDisabled" BOOLEAN NOT NULL,
  "password" TEXT NOT NULL
);

CREATE TABLE "user_detail" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "religion" VARCHAR(40) NOT NULL,
  "country" VARCHAR(50) NOT NULL,
  "height" DECIMAL,
  "weight" DECIMAL,
  "skin" VARCHAR(20),
  "hair" VARCHAR(20),
  "gender" VARCHAR(20) NOT NULL,
  "age" SMALLINT,
  "userId" SERIAL REFERENCES "user"(id)
)

# --- !Downs
drop table "user"

drop table "user_detail"
