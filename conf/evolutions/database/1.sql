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

CREATE TABLE "user_interesting_info" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "name" VARCHAR(100) NOT NULL,
  "userId" SERIAL REFERENCES "user"(id)
);

CREATE TABLE "skill" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "name" VARCHAR(100) NOT NULL,
  "level" VARCHAR(50) NOT NULL
);

CREATE TABLE "user_skill" (
  "userId" SERIAL REFERENCES "user"(id),
  "skillId" SERIAL REFERENCES "skill"(id),
  "level" TEXT NULL,
  "yearsExperience" TEXT NULL
);

CREATE TABLE "project" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "name" VARCHAR(100) NOT NULL,
  "url" TEXT NOT NULL,
  "startDate" TIMESTAMP NOT NULL,
  "endDate" TIMESTAMP NULL
);

CREATE TABLE "project_skill" (
  "projectId" SERIAL REFERENCES "project"(id),
  "skillId" SERIAL REFERENCES "skill"(id)
);

CREATE TABLE "user_project" (
  "userId" SERIAL REFERENCES "user"(id),
  "projectId" SERIAL REFERENCES "project"(id)
);

CREATE TABLE "leave_category" (
  "id" SERIAL PRIMARY KEY,
  "name" TEXT NOT NULL,
);

CREATE TABLE "leave" (
  "id" SERIAL PRIMARY KEY,
  "description" TEXT NOT NULL,
  "startDate" TIMESTAMP NOT NULL,
  "endDate" TIMESTAMP NULL,
  "categoryId" SERIAL REFERENCES "leave_categoryId"(id)
);

CREATE TABLE "user_leave" (
  "leaveId" SERIAL REFERENCES "leave"(id),
  "userId" SERIAL REFERENCES "user"(id)
);

# --- !Downs
DROP TABLE "user";

DROP TABLE "role";

DROP TABLE "user_role";

DROP TABLE "user_detail";

DROP TABLE "user_report";

DROP TABLE "user_interest";

DROP TABLE "skill";

DROP TABLE "user_skill";

DROP TABLE "project";

DROP TABLE "project_skill";

DROP TABLE "user_project";

DROP TABLE "vacation";

DROP TABLE "user_vacation";
