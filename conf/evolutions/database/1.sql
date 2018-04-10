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

CREATE TABLE "role" (
  "id" SERIAL PRIMARY KEY,
  "name" TEXT NOT NULL
);

CREATE TABLE "user_role" (
  "userId" SERIAL REFERENCES "user"(id),
  "roleId" SERIAL REFERENCES "role"(id)
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
);

CREATE TABLE "user_report" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "reason" VARCHAR(100) NOT NULL,
  "date" TIMESTAMP NOT NULL,
  "userId" SERIAL REFERENCES "user"(id),
  "reportedUserId" SERIAL REFERENCES "user"(id)
);

CREATE TABLE "user_interest" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "reason" VARCHAR(100) NOT NULL,
  "date" TIMESTAMP NOT NULL,
  "userId" SERIAL REFERENCES "user"(id)
);

# --- !Downs
DROP TABLE "user";

DROP TABLE "role";

DROP TABLE "user_role";

DROP TABLE "user_detail";

DROP TABLE "user_report";

DROP TABLE "user_interest";
