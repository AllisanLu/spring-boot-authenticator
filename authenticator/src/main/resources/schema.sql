CREATE TABLE "TOKENS"
("ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
"TOKEN" VARCHAR(255))

ALTER TABLE "TOKENS" ADD CONSTRAINT
"SQL120325130144011" PRIMARY KEY ("ID");
