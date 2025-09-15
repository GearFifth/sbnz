-- Omogućavanje pgcrypto ekstenzije ako već nije omogućena
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ########## PRVO BRIŠEMO SVE POSTOJEĆE PODATKE U ISPRAVNOM REDOSLEDU ##########
DELETE FROM location_tags;
DELETE FROM route;
DELETE FROM location;
DELETE FROM tag;
DELETE FROM rule_parameter;

-- ########## 1. KREIRANJE SVIH TAGOVA ##########
INSERT INTO tag (id, name) VALUES
                               (gen_random_uuid(), 'romantika'), (gen_random_uuid(), 'jezero'), (gen_random_uuid(), 'dvorac'), (gen_random_uuid(), 'ostrvo'),
                               (gen_random_uuid(), 'crkva'), (gen_random_uuid(), 'fotografija'), (gen_random_uuid(), 'šetnja'), (gen_random_uuid(), 'istorija'),
                               (gen_random_uuid(), 'krempita'), (gen_random_uuid(), 'veslanje'), (gen_random_uuid(), 'porodice'), (gen_random_uuid(), 'priroda'),
                               (gen_random_uuid(), 'kanjon'), (gen_random_uuid(), 'vodopad'), (gen_random_uuid(), 'planinarenje'), (gen_random_uuid(), 'drvene staze'),
                               (gen_random_uuid(), 'nacionalni park'), (gen_random_uuid(), 'mir'), (gen_random_uuid(), 'plivanje'), (gen_random_uuid(), 'kajak'),
                               (gen_random_uuid(), 'biciklizam'), (gen_random_uuid(), 'skijanje'), (gen_random_uuid(), 'avantura'), (gen_random_uuid(), 'autentično'),
                               (gen_random_uuid(), 'alpinizam'), (gen_random_uuid(), 'divljina'), (gen_random_uuid(), 'reke'), (gen_random_uuid(), 'izazovno'),
                               (gen_random_uuid(), 'planine'), (gen_random_uuid(), 'alpsko selo'), (gen_random_uuid(), 'zimski sportovi'), (gen_random_uuid(), 'grad'),
                               (gen_random_uuid(), 'kultura'), (gen_random_uuid(), 'arhitektura'), (gen_random_uuid(), 'gastronomija'), (gen_random_uuid(), 'mostovi'),
                               (gen_random_uuid(), 'zmaj'), (gen_random_uuid(), 'životni stil'), (gen_random_uuid(), 'muzeji'), (gen_random_uuid(), 'pećina'),
                               (gen_random_uuid(), 'geologija'), (gen_random_uuid(), 'podzemni voz'), (gen_random_uuid(), 'lako dostupno'), (gen_random_uuid(), 'legenda'),
                               (gen_random_uuid(), 'srednji vek'), (gen_random_uuid(), 'pećinski dvorac'), (gen_random_uuid(), 'vitezi'), (gen_random_uuid(), 'UNESCO'),
                               (gen_random_uuid(), 'konji'), (gen_random_uuid(), 'predstava'), (gen_random_uuid(), 'ergela'), (gen_random_uuid(), 'obala'),
                               (gen_random_uuid(), 'more'), (gen_random_uuid(), 'venecijanski stil'), (gen_random_uuid(), 'solane'), (gen_random_uuid(), 'vino'),
                               (gen_random_uuid(), 'stara trta'), (gen_random_uuid(), 'festivali'), (gen_random_uuid(), 'pohorje'), (gen_random_uuid(), 'rimsko nasleđe'),
                               (gen_random_uuid(), 'karneval'), (gen_random_uuid(), 'kurentovanje'), (gen_random_uuid(), 'terme'), (gen_random_uuid(), 'dolina'),
                               (gen_random_uuid(), 'pejzažni park'), (gen_random_uuid(), 'eko turizam'), (gen_random_uuid(), 'adrenalin'), (gen_random_uuid(), 'rafting'),
                               (gen_random_uuid(), 'istorija WW1'), (gen_random_uuid(), 'paraglajding'), (gen_random_uuid(), 'edukacija'), (gen_random_uuid(), 'rat'),
                               (gen_random_uuid(), 'istorijski centar'), (gen_random_uuid(), 'grofovi celjski');

-- ########## 2. KREIRANJE LOKACIJA ##########
-- Gorenjska regija
INSERT INTO location (id, name, type, region, ticket_price, visit_time_minutes, required_fitness, public_transport_accessibility, required_equipment, description, seasonal, opening_month, closing_month) VALUES
                                                                                                                                                                                                               (gen_random_uuid(), 'Blejsko jezero', 'Jezero', 'Gorenjska', 50.00, 210, 'LOW', 'EXCELLENT', 'Udobna obuća za hodanje', 'Cena od 50 EUR je za kompletno iskustvo: pletna (20), crkva (12), dvorac (18). Sama šetnja oko jezera je besplatna.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Blejski grad', 'Dvorac', 'Gorenjska', 18.00, 105, 'MEDIUM', 'EXCELLENT', 'Udobna obuća', 'Nalazi se iznad Blejskog jezera. Pešačenje od jezera je strm uspon.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Soteska Vintgar', 'Klanac', 'Gorenjska', 10.00, 135, 'MEDIUM', 'GOOD', 'Čvrsta, neklizajuća obuća, vodootporna jakna', 'Jednosmerna šetnja sa povratkom kroz šumu.', true, 4, 11),
                                                                                                                                                                                                               (gen_random_uuid(), 'Bohinjsko jezero', 'Jezero', 'Gorenjska', 0.00, 210, 'LOW', 'GOOD', 'Udobna obuća, kupaći kostim leti', 'Pristup jezeru je besplatan. Aktivnosti poput posete Slapu Savica i Vogelu se naplaćuju odvojeno.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Slap Savica', 'Vodopad', 'Gorenjska', 4.00, 60, 'MEDIUM', 'GOOD', 'Planinarska obuća', 'Zahteva uspon uz više od 500 kamenih stepenika.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Triglavski narodni park', 'Nacionalni park', 'Gorenjska', 0.00, 480, 'HIGH', 'COMPLICATED', 'Kompletna planinarska oprema za visoke vrhove', 'Ulaz u park je besplatan, ali se pojedine atrakcije unutar njega naplaćuju.', true, 6, 9),
                                                                                                                                                                                                               (gen_random_uuid(), 'Kranjska Gora', 'Planinsko odmaralište', 'Gorenjska', 0.00, 60, 'LOW', 'GOOD', 'Zavisi od aktivnosti (skijaška oprema, planinarske cipele)', 'Baza za zimske i letnje sportove.', false, 1, 12);

-- Notranjsko-kraška i Obalno-kraška regija
INSERT INTO location (id, name, type, region, ticket_price, visit_time_minutes, required_fitness, public_transport_accessibility, required_equipment, description, seasonal, opening_month, closing_month) VALUES
                                                                                                                                                                                                               (gen_random_uuid(), 'Postojnska jama', 'Pećina', 'Notranjsko-kraška', 34.50, 90, 'LOW', 'GOOD', 'Topla odeća (jakna), udobna obuća', 'Temperatura je 10°C. Obilazak uključuje vožnju vozićem. Preporučuje se paket sa Predjamskim gradom.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Predjamski grad', 'Dvorac', 'Notranjsko-kraška', 22.50, 75, 'MEDIUM', 'GOOD', 'Udobna obuća', 'Dvorac u steni sa mnogo stepenica. Preporučuje se paket sa Postojnskom jamom.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Škocjanske jame', 'Pećina', 'Obalno-kraška', 24.00, 165, 'HIGH', 'COMPLICATED', 'Čvrsta sportska obuća, topla odeća', 'UNESCO baština. Fizički zahtevniji obilazak (5km, 1000 stepenika). Temperatura je 12°C.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Piran', 'Grad', 'Obalno-kraška', 3.00, 210, 'MEDIUM', 'GOOD', 'Udobna obuća', 'Cena se odnosi na ulaz na gradske zidine. Sam grad je besplatan.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Kobilarna Lipica', 'Ergela', 'Obalno-kraška', 16.00, 120, 'LOW', 'COMPLICATED', 'Nema specijalnih zahteva', 'Poznata ergela lipicanera.', false, 1, 12);

-- Centralna, Podravska i ostale regije (AŽURIRANO)
INSERT INTO location (id, name, type, region, ticket_price, visit_time_minutes, required_fitness, public_transport_accessibility, required_equipment, description, seasonal, opening_month, closing_month) VALUES
                                                                                                                                                                                                               (gen_random_uuid(), 'Ljubljana', 'Glavni grad', 'Osrednjeslovenska', 19.00, 240, 'LOW', 'EXCELLENT', 'Udobna obuća za hodanje', 'Glavni grad i idealna baza. Cena se odnosi na kombinovanu ulaznicu za dvorac sa uspinjačom.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Maribor', 'Grad', 'Podravska', 10.90, 300, 'LOW', 'EXCELLENT', 'Ležerna odeća i udobna obuća', 'Štajerska prestonica vina. Cena se odnosi na osnovnu degustaciju u Kući Stare trte.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Ptuj', 'Istorijski grad', 'Podravska', 10.00, 240, 'MEDIUM', 'GOOD', 'Udobna obuća za kaldrmu', 'Najstariji grad u Sloveniji, UNESCO nasleđe. Cena se odnosi na ulaz u dvorac.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Celje', 'Dvorac', 'Savinjska', 7.00, 120, 'MEDIUM', 'GOOD', 'Čvrsta i udobna obuća', 'Najveća srednjovekovna tvrđava u Sloveniji. Idealna kao usputna stanica.', false, 1, 12), -- ISPRAVLJENO IME
                                                                                                                                                                                                               (gen_random_uuid(), 'Logarska dolina', 'Dolina', 'Savinjska', 10.00, 180, 'MEDIUM', 'CAR_ONLY', 'Planinarska obuća', 'Pejzažni park. Cena je ulaz automobilom.', true, 4, 10),
                                                                                                                                                                                                               (gen_random_uuid(), 'Dolina Soče', 'Dolina', 'Goriška', 0.00, 480, 'MEDIUM', 'COMPLICATED', 'Oprema za vodene sportove ili planinarenje', 'Centar za adrenalinske sportove. Vršič prelaz je sezonski.', true, 5, 10),
                                                                                                                                                                                                               (gen_random_uuid(), 'Kobariški muzej', 'Muzej', 'Goriška', 8.00, 105, 'LOW', 'COMPLICATED', 'Nema posebnih zahteva', 'Pruža istorijski kontekst za region Doline Soče, fokusiran na Prvi svetski rat.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Tolminska korita', 'Kanjon', 'Goriška', 8.00, 90, 'MEDIUM', 'COMPLICATED', 'Neklizajuća obuća', 'Najniža ulazna tačka u Triglavski nacionalni park.', true, 4, 11);


-- ########## 3. POVEZIVANJE LOKACIJA I TAGOVA ##########
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Blejsko jezero' AND t.name IN ('romantika', 'jezero', 'dvorac', 'ostrvo', 'crkva', 'fotografija', 'šetnja', 'istorija', 'krempita', 'veslanje', 'porodice');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Soteska Vintgar' AND t.name IN ('priroda', 'kanjon', 'vodopad', 'šetnja', 'planinarenje', 'fotografija', 'drvene staze');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Bohinjsko jezero' AND t.name IN ('priroda', 'jezero', 'nacionalni park', 'planinarenje', 'mir', 'plivanje', 'kajak', 'biciklizam', 'vodopad', 'skijanje', 'avantura', 'autentično');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Triglavski narodni park' AND t.name IN ('nacionalni park', 'planinarenje', 'alpinizam', 'priroda', 'divljina', 'avantura', 'biciklizam', 'reke', 'jezera', 'izazovno');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Kranjska Gora' AND t.name IN ('skijanje', 'planine', 'planinarenje', 'biciklizam', 'porodice', 'avantura', 'jezero', 'alpsko selo', 'zimski sportovi');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Ljubljana' AND t.name IN ('grad', 'kultura', 'istorija', 'dvorac', 'arhitektura', 'gastronomija', 'reka', 'mostovi', 'zmaj', 'životni stil', 'muzeji', 'istorijski centar');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Postojnska jama' AND t.name IN ('pećina', 'avantura', 'porodice', 'priroda', 'geologija', 'podzemni voz', 'lako dostupno');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Predjamski grad' AND t.name IN ('dvorac', 'istorija', 'legenda', 'srednji vek', 'pećinski dvorac', 'vitezi', 'fotografija');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Škocjanske jame' AND t.name IN ('pećina', 'UNESCO', 'priroda', 'avantura', 'planinarenje', 'geologija', 'kanjon', 'izazovno', 'autentično');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Kobilarna Lipica' AND t.name IN ('konji', 'istorija', 'kultura', 'predstava', 'porodice', 'priroda', 'ergela');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Piran' AND t.name IN ('grad', 'obala', 'more', 'istorija', 'venecijanski stil', 'romantika', 'gastronomija', 'šetnja', 'fotografija', 'solane');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Maribor' AND t.name IN ('grad', 'vino', 'kultura', 'istorija', 'gastronomija', 'reka', 'stara trta', 'festivali', 'pohorje', 'skijanje');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Ptuj' AND t.name IN ('grad', 'istorija', 'rimsko nasleđe', 'dvorac', 'kultura', 'karneval', 'kurentovanje', 'vino', 'terme');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Celje' AND t.name IN ('grad', 'dvorac', 'istorija', 'srednji vek', 'grofovi celjski', 'romantika');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Logarska dolina' AND t.name IN ('priroda', 'dolina', 'planinarenje', 'mir', 'vodopad', 'biciklizam', 'fotografija', 'pejzažni park', 'eko turizam');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Dolina Soče' AND t.name IN ('priroda', 'reka', 'avantura', 'adrenalin', 'kajak', 'rafting', 'planinarenje', 'istorija WW1', 'vodopad', 'biciklizam', 'paraglajding');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Kobariški muzej' AND t.name IN ('muzej', 'istorija WW1', 'edukacija', 'kultura', 'rat', 'istorija');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Tolminska korita' AND t.name IN ('priroda', 'kanjon', 'nacionalni park', 'šetnja', 'most', 'reke', 'geologija');


-- ########## 4. KREIRANJE RUTA ##########
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Blejsko jezero'), 45),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Bohinjsko jezero'), 75),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Postojnska jama'), 40),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Piran'), 75),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Kranjska Gora'), 60),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Maribor'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Bohinjsko jezero'), 30),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Postojnska jama'), 75),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Piran'), 110),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Kranjska Gora'), 35),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Maribor'), 135),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Bohinjsko jezero'), (SELECT id FROM location WHERE name='Postojnska jama'), 105),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Bohinjsko jezero'), (SELECT id FROM location WHERE name='Piran'), 140),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Bohinjsko jezero'), (SELECT id FROM location WHERE name='Kranjska Gora'), 65),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Bohinjsko jezero'), (SELECT id FROM location WHERE name='Maribor'), 165),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojnska jama'), (SELECT id FROM location WHERE name='Piran'), 60),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojnska jama'), (SELECT id FROM location WHERE name='Kranjska Gora'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojnska jama'), (SELECT id FROM location WHERE name='Maribor'), 120),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Piran'), (SELECT id FROM location WHERE name='Kranjska Gora'), 140),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Piran'), (SELECT id FROM location WHERE name='Maribor'), 195),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Kranjska Gora'), (SELECT id FROM location WHERE name='Maribor'), 180),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Celje'), 60),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Dolina Soče'), 120),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Celje'), 80),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Blejsko jezero'), (SELECT id FROM location WHERE name='Dolina Soče'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Maribor'), (SELECT id FROM location WHERE name='Celje'), 40),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Celje'), (SELECT id FROM location WHERE name='Dolina Soče'), 175);

-- ########## 5. KREIRANJE PARAMETARA ZA PRAVILA ##########
INSERT INTO rule_parameter (param_key, param_value) VALUES
                                                        ('BUDGET_LIMIT_LOW', 15.0),
                                                        ('BUDGET_LIMIT_MEDIUM', 40.0);