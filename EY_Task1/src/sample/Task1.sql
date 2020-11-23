CREATE TABLE Lines (
    id BIGSERIAL ,
    date text,
    rus text,
    eng text,
    intnum numeric(9),
    doublenum numeric(10,8)
);

SELECT SUM (intnum) AS total FROM lines;

SELECT CASE WHEN c % 2 = 0 AND c > 1 THEN (a[1]+a[2])/2 ELSE a[1] END
FROM
    (
        SELECT ARRAY(SELECT doublenum FROM Lines ORDER BY doublenum OFFSET (c-1)/2 LIMIT 2) AS a, c
        FROM (SELECT count(*) AS c FROM Lines where doublenum is not null) AS count
        OFFSET 0
    )
        AS midrows;

