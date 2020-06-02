CREATE TABLE item
(
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    path character varying(256),
    dsc character varying(256)
);