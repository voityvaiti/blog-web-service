ALTER TABLE usr ADD created_date TIMESTAMPTZ;
ALTER TABLE usr ADD updated_date TIMESTAMPTZ;

ALTER TABLE post ALTER COLUMN publication_datetime TYPE TIMESTAMPTZ;
ALTER TABLE post RENAME publication_datetime TO created_date;
ALTER TABLE post ADD updated_date TIMESTAMPTZ;