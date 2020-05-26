# --- !ups

CREATE TABLE "books" (
  "book_id" varchar(255) NOT NULL,
  "title" varchar(255),
  "author" varchar(255),
  "genre" varchar(255),
  "is_audio_book" boolean,
  "is_fiction" boolean,
  PRIMARY KEY ("book_id")
);

# ---!Downs

DROP TABLE "books";