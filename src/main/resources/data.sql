INSERT INTO auteurs (id, nom, prenom, nationalite, remarque)
VALUES
    (1, 'Hugo', 'Victor', 'Française', NULL),
    (2, 'Rowling', 'J.K.', 'Britannique', NULL),
    (3, 'Camus', 'Albert', 'Française', NULL),
    (4, 'Tolkien', 'J.R.R.', 'Britannique', NULL),
    (5, 'Asimov', 'Isaac', 'Américaine', 'N''a aucun livre');

INSERT INTO livres
(id, titre, isbn, prix, annee_publication, stock, auteurId)
VALUES
    (1, 'Les Misérables', '978-2-07-040850-4', 12.50, 1862, 5, 1),
    (2, 'Notre-Dame de Paris', '978-2-07-041239-6', 9.90, 1831, 3, 1),
    (3, 'Harry Potter à l''école des sorciers', '978-2-07-054127-0', 8.90, 1997, 10, 2),
    (4, 'Harry Potter et la Chambre des secrets', '978-2-07-054128-7', 8.90, 1998, 7, 2),
    (5, 'L''Étranger', '978-2-07-036024-8', 6.50, 1942, 4, 3),
    (6, 'La Peste', '978-2-07-036042-2', 7.80, 1947, 6, 3),
    (7, 'Le Seigneur des Anneaux', '978-2-267-02700-4', 25.00, 1954, 3, 4),
    (8, 'Le Hobbit', '978-2-267-02600-7', 15.00, 1937, 0, 4);

INSERT INTO emprunts
(id, livreId, emprunteur, emailEmprunteur, dateEmprunt,
 dateRetourPrevue, dateRetourEffective, rendu)
VALUES
    (1, 1, 'Marie Dupont', 'marie.dupont@email.com',
     '2026-09-01', '2026-09-15', '2026-09-10', TRUE),

    (2, 3, 'Jean Martin', 'jean.martin@email.com',
     '2026-09-05', '2026-09-19', NULL, FALSE),

    (3, 5, 'Sophie Bernard', 'sophie.bernard@email.com',
     '2026-09-20', '2026-10-04', NULL, FALSE);