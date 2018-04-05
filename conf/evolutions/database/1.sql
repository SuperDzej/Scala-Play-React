# Users schema

# --- !Ups
CREATE TABLE "user" (
  "id" SERIAL PRIMARY KEY,
  "first_name" TEXT NOT NULL,
  "last_name" TEXT NOT NULL,
  "email" TEXT NOT NULL,
  "username" TEXT NOT NULL,
  "isVerified" BIT NOT NULL,
  "isDisabled" BIT NOT NULL,
  "password" TEXT NOT NULL
)

CREATE TABLE "user_detail" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "religion" VARCHAR(40) NOT NULL,
  "country" VARCHAR(50) NOT NULL,
  "height" DECIMAL,
  "weight" DECIMAL,
  "skin" VARCHAR(20),
  "hair" VARCHAR(20),
  "gender" TEXT NOT NULL,
  "birthYear" INTEGER,
  "birthMonth" INTEGER,
  "birthDay" INTEGER,
  "userId" SERIAL REFERENCES user(id)
)

# --- !Downs
drop table "user"

drop table "user_detail"
