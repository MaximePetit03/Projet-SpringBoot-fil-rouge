INSERT INTO auteurs (id, nom, prenom, nationalite)
VALUES
    (1, 'Hugo', 'Victor', 'Française'),
    (2, 'Rowling', 'J.K.', 'Britannique'),
    (3, 'Camus', 'Albert', 'Française'),
    (4, 'Tolkien', 'J.R.R.', 'Britannique'),
    (5, 'Asimov', 'Isaac', 'Américaine');

-- On retire 'id' de la liste pour laisser H2 les générer proprement de 1 à 8
INSERT INTO livres (titre, isbn, prix, annee_publication, stock, auteur_id)
VALUES
    ('Les Misérables', '978-2-07-040850-4', 12.50, 1862, 5, 1),
    ('Notre-Dame de Paris', '978-2-07-041239-6', 9.90, 1831, 3, 1),
    ('Harry Potter à l''école des sorciers', '978-2-07-054127-0', 8.90, 1997, 10, 2),
    ('Harry Potter et la Chambre des secrets', '978-2-07-054128-7', 8.90, 1998, 7, 2),
    ('L''Étranger', '978-2-07-036024-8', 6.50, 1942, 4, 3),
    ('La Peste', '978-2-07-036042-2', 7.80, 1947, 6, 3),
    ('Le Seigneur des Anneaux', '978-2-267-02700-4', 25.00, 1954, 3, 4),
    ('Le Hobbit', '978-2-267-02600-7', 15.00, 1937, 0, 4);

INSERT INTO emprunts
(id, livre_id, emprunteur, email_emprunteur, date_emprunt, date_retour_prevue, date_retour_effective, rendu, retard)
VALUES
    (1, 1, 'Marie Dupont', 'marie.dupont@email.com', '2026-09-01', '2026-09-15', '2026-09-10', TRUE, FALSE),
    (2, 3, 'Jean Martin', 'jean.martin@email.com', '2026-09-05', '2026-09-19', NULL, FALSE, TRUE),
    (3, 5, 'Sophie Bernard', 'sophie.bernard@email.com', '2026-09-20', '2026-10-04', NULL, FALSE, TRUE);

ALTER TABLE auteurs ALTER COLUMN id RESTART WITH 6;
ALTER TABLE livres ALTER COLUMN id RESTART WITH 9;
ALTER TABLE emprunts ALTER COLUMN id RESTART WITH 4;
