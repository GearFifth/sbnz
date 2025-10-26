-- Enable pgcrypto extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ########## FIRST, DELETE ALL EXISTING DATA IN THE CORRECT ORDER ##########
DELETE FROM location_tags;
DELETE FROM route;
DELETE FROM location;
DELETE FROM tag;
DELETE FROM rule_parameter;

-- ########## 1. CREATE ALL TAGS ##########
INSERT INTO tag (id, name) VALUES
                               (gen_random_uuid(), 'romance'), (gen_random_uuid(), 'lake'), (gen_random_uuid(), 'castle'), (gen_random_uuid(), 'island'),
                               (gen_random_uuid(), 'church'), (gen_random_uuid(), 'photography'), (gen_random_uuid(), 'walking'), (gen_random_uuid(), 'history'),
                               (gen_random_uuid(), 'cream cake'), (gen_random_uuid(), 'rowing'), (gen_random_uuid(), 'families'), (gen_random_uuid(), 'nature'),
                               (gen_random_uuid(), 'canyon'), (gen_random_uuid(), 'waterfall'), (gen_random_uuid(), 'hiking'), (gen_random_uuid(), 'wooden paths'),
                               (gen_random_uuid(), 'national park'), (gen_random_uuid(), 'peace'), (gen_random_uuid(), 'swimming'), (gen_random_uuid(), 'kayaking'),
                               (gen_random_uuid(), 'cycling'), (gen_random_uuid(), 'skiing'), (gen_random_uuid(), 'adventure'), (gen_random_uuid(), 'authentic'),
                               (gen_random_uuid(), 'mountaineering'), (gen_random_uuid(), 'wilderness'), (gen_random_uuid(), 'rivers'), (gen_random_uuid(), 'challenging'),
                               (gen_random_uuid(), 'mountains'), (gen_random_uuid(), 'alpine village'), (gen_random_uuid(), 'winter sports'), (gen_random_uuid(), 'city'),
                               (gen_random_uuid(), 'culture'), (gen_random_uuid(), 'architecture'), (gen_random_uuid(), 'gastronomy'), (gen_random_uuid(), 'bridges'),
                               (gen_random_uuid(), 'dragon'), (gen_random_uuid(), 'lifestyle'), (gen_random_uuid(), 'museums'), (gen_random_uuid(), 'cave'),
                               (gen_random_uuid(), 'geology'), (gen_random_uuid(), 'underground train'), (gen_random_uuid(), 'easily accessible'), (gen_random_uuid(), 'legend'),
                               (gen_random_uuid(), 'middle ages'), (gen_random_uuid(), 'cave castle'), (gen_random_uuid(), 'knights'), (gen_random_uuid(), 'UNESCO'),
                               (gen_random_uuid(), 'horses'), (gen_random_uuid(), 'show'), (gen_random_uuid(), 'stud farm'), (gen_random_uuid(), 'coast'),
                               (gen_random_uuid(), 'sea'), (gen_random_uuid(), 'venetian style'), (gen_random_uuid(), 'salt pans'), (gen_random_uuid(), 'wine'),
                               (gen_random_uuid(), 'old vine'), (gen_random_uuid(), 'festivals'), (gen_random_uuid(), 'pohorje'), (gen_random_uuid(), 'roman heritage'),
                               (gen_random_uuid(), 'carnival'), (gen_random_uuid(), 'kurentovanje'), (gen_random_uuid(), 'thermal spas'), (gen_random_uuid(), 'valley'),
                               (gen_random_uuid(), 'landscape park'), (gen_random_uuid(), 'eco-tourism'), (gen_random_uuid(), 'adrenaline'), (gen_random_uuid(), 'rafting'),
                               (gen_random_uuid(), 'WW1 history'), (gen_random_uuid(), 'paragliding'), (gen_random_uuid(), 'education'), (gen_random_uuid(), 'war'),
                               (gen_random_uuid(), 'historical center'), (gen_random_uuid(), 'counts of celje');

-- ########## 2. CREATE LOCATIONS ##########
-- Gorenjska region
INSERT INTO location (id, name, type, region, ticket_price, visit_time_minutes, required_fitness, public_transport_accessibility, required_equipment, description, seasonal, opening_month, closing_month) VALUES
                                                                                                                                                                                                               (gen_random_uuid(), 'Lake Bled', 'Lake', 'Gorenjska', 50.00, 210, 'LOW', 'EXCELLENT', 'Comfortable walking shoes', 'The 50 EUR price is for the complete experience: pletna boat (20), church (12), castle (18). The walk around the lake itself is free.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Bled Castle', 'Castle', 'Gorenjska', 18.00, 105, 'MEDIUM', 'EXCELLENT', 'Comfortable shoes', 'Located above Lake Bled. The walk from the lake is a steep ascent.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Vintgar Gorge', 'Gorge', 'Gorenjska', 10.00, 135, 'MEDIUM', 'GOOD', 'Sturdy, non-slip footwear, waterproof jacket', 'One-way walk with a return path through the forest.', true, 4, 11),
                                                                                                                                                                                                               (gen_random_uuid(), 'Lake Bohinj', 'Lake', 'Gorenjska', 0.00, 210, 'LOW', 'GOOD', 'Comfortable shoes, swimsuit in summer', 'Access to the lake is free. Activities like visiting Savica Waterfall and Vogel are charged separately.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Savica Waterfall', 'Waterfall', 'Gorenjska', 4.00, 60, 'MEDIUM', 'GOOD', 'Hiking boots', 'Requires climbing more than 500 stone steps.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Triglav National Park', 'National Park', 'Gorenjska', 0.00, 480, 'HIGH', 'COMPLICATED', 'Full hiking equipment for high peaks', 'Entrance to the park is free, but some attractions within it are charged.', true, 6, 9),
                                                                                                                                                                                                               (gen_random_uuid(), 'Kranjska Gora', 'Mountain resort', 'Gorenjska', 0.00, 60, 'LOW', 'GOOD', 'Depends on the activity (ski equipment, hiking boots)', 'Base for winter and summer sports.', false, 1, 12);

-- Notranjsko-kraška and Obalno-kraška region
INSERT INTO location (id, name, type, region, ticket_price, visit_time_minutes, required_fitness, public_transport_accessibility, required_equipment, description, seasonal, opening_month, closing_month) VALUES
                                                                                                                                                                                                               (gen_random_uuid(), 'Postojna Cave', 'Cave', 'Notranjsko-kraška', 34.50, 90, 'LOW', 'GOOD', 'Warm clothing (jacket), comfortable shoes', 'Temperature is 10°C. The tour includes a train ride. A package deal with Predjama Castle is recommended.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Predjama Castle', 'Castle', 'Notranjsko-kraška', 22.50, 75, 'MEDIUM', 'GOOD', 'Comfortable shoes', 'Castle in the rock with many stairs. A package deal with Postojna Cave is recommended.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Škocjan Caves', 'Cave', 'Obalno-kraška', 24.00, 165, 'HIGH', 'COMPLICATED', 'Sturdy sports shoes, warm clothing', 'UNESCO heritage. Physically more demanding tour (5km, 1000 steps). Temperature is 12°C.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Piran', 'City', 'Obalno-kraška', 3.00, 210, 'MEDIUM', 'GOOD', 'Comfortable shoes', 'The price refers to the entrance to the city walls. The town itself is free.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Lipica Stud Farm', 'Stud Farm', 'Obalno-kraška', 16.00, 120, 'LOW', 'COMPLICATED', '', 'Famous Lipizzaner stud farm.', false, 1, 12);

-- Central, Podravska and other regions (UPDATED)
INSERT INTO location (id, name, type, region, ticket_price, visit_time_minutes, required_fitness, public_transport_accessibility, required_equipment, description, seasonal, opening_month, closing_month) VALUES
                                                                                                                                                                                                               (gen_random_uuid(), 'Ljubljana', 'Capital city', 'Osrednjeslovenska', 19.00, 240, 'LOW', 'EXCELLENT', 'Comfortable walking shoes', 'Capital city and an ideal base. The price refers to the combined ticket for the castle with the funicular.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Maribor', 'City', 'Podravska', 10.90, 300, 'LOW', 'EXCELLENT', 'Casual clothes and comfortable shoes', 'Styrian wine capital. The price refers to a basic tasting at the Old Vine House.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Ptuj', 'Historical city', 'Podravska', 10.00, 240, 'MEDIUM', 'GOOD', 'Comfortable shoes for cobblestones', 'Oldest town in Slovenia, UNESCO heritage. The price refers to the castle entrance fee.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Celje', 'Castle', 'Savinjska', 7.00, 120, 'MEDIUM', 'GOOD', 'Sturdy and comfortable shoes', 'Largest medieval fortress in Slovenia. Ideal as a stopover.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Logar Valley', 'Valley', 'Savinjska', 10.00, 180, 'MEDIUM', 'CAR_ONLY', 'Hiking boots', 'Landscape park. The price is for car entry.', true, 4, 10),
                                                                                                                                                                                                               (gen_random_uuid(), 'Soča Valley', 'Valley', 'Goriška', 0.00, 480, 'MEDIUM', 'COMPLICATED', 'Equipment for water sports or hiking', 'Center for adrenaline sports. Vršič Pass is seasonal.', true, 5, 10),
                                                                                                                                                                                                               (gen_random_uuid(), 'Kobarid Museum', 'Museum', 'Goriška', 8.00, 105, 'LOW', 'COMPLICATED', '', 'Provides historical context for the Soča Valley region, focused on World War I.', false, 1, 12),
                                                                                                                                                                                                               (gen_random_uuid(), 'Tolmin Gorges', 'Canyon', 'Goriška', 8.00, 90, 'MEDIUM', 'COMPLICATED', 'Non-slip footwear', 'Lowest entry point into Triglav National Park.', true, 4, 11);

-- ########## 3. LINK LOCATIONS AND TAGS ##########
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Lake Bled' AND t.name IN ('romance', 'lake', 'castle', 'island', 'church', 'photography', 'walking', 'history', 'cream cake', 'rowing', 'families');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Vintgar Gorge' AND t.name IN ('nature', 'canyon', 'waterfall', 'walking', 'hiking', 'photography', 'wooden paths');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Lake Bohinj' AND t.name IN ('nature', 'lake', 'national park', 'hiking', 'peace', 'swimming', 'kayaking', 'cycling', 'waterfall', 'skiing', 'adventure', 'authentic');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Triglav National Park' AND t.name IN ('national park', 'hiking', 'mountaineering', 'nature', 'wilderness', 'adventure', 'cycling', 'rivers', 'lake', 'challenging');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Kranjska Gora' AND t.name IN ('skiing', 'mountains', 'hiking', 'cycling', 'families', 'adventure', 'lake', 'alpine village', 'winter sports');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Ljubljana' AND t.name IN ('city', 'culture', 'history', 'castle', 'architecture', 'gastronomy', 'river', 'bridges', 'dragon', 'lifestyle', 'museums', 'historical center');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Postojna Cave' AND t.name IN ('cave', 'adventure', 'families', 'nature', 'geology', 'underground train', 'easily accessible');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Predjama Castle' AND t.name IN ('castle', 'history', 'legend', 'middle ages', 'cave castle', 'knights', 'photography');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Škocjan Caves' AND t.name IN ('cave', 'UNESCO', 'nature', 'adventure', 'hiking', 'geology', 'canyon', 'challenging', 'authentic');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Lipica Stud Farm' AND t.name IN ('horses', 'history', 'culture', 'show', 'families', 'nature', 'stud farm');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Piran' AND t.name IN ('city', 'coast', 'sea', 'history', 'venetian style', 'romance', 'gastronomy', 'walking', 'photography', 'salt pans');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Maribor' AND t.name IN ('city', 'wine', 'culture', 'history', 'gastronomy', 'river', 'old vine', 'festivals', 'pohorje', 'skiing');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Ptuj' AND t.name IN ('city', 'history', 'roman heritage', 'castle', 'culture', 'carnival', 'kurentovanje', 'wine', 'thermal spas');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Celje' AND t.name IN ('city', 'castle', 'history', 'middle ages', 'counts of celje', 'romance');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Logar Valley' AND t.name IN ('nature', 'valley', 'hiking', 'peace', 'waterfall', 'cycling', 'photography', 'landscape park', 'eco-tourism');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Soča Valley' AND t.name IN ('nature', 'river', 'adventure', 'adrenaline', 'kayaking', 'rafting', 'hiking', 'WW1 history', 'waterfall', 'cycling', 'paragliding');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Kobarid Museum' AND t.name IN ('museum', 'WW1 history', 'education', 'culture', 'war', 'history');
INSERT INTO location_tags (location_id, tag_id) SELECT l.id, t.id FROM location l, tag t WHERE l.name = 'Tolmin Gorges' AND t.name IN ('nature', 'canyon', 'national park', 'walking', 'bridge', 'rivers', 'geology');

-- ########## 4. CREATE ROUTES ##########
-- Existing routes between main hubs
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Lake Bled'), 45),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Lake Bohinj'), 75),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Postojna Cave'), 40),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Piran'), 75),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Kranjska Gora'), 60),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Maribor'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Lake Bohinj'), 30),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Postojna Cave'), 75),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Piran'), 110),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Kranjska Gora'), 35),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Maribor'), 135),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bohinj'), (SELECT id FROM location WHERE name='Postojna Cave'), 105),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bohinj'), (SELECT id FROM location WHERE name='Piran'), 140),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bohinj'), (SELECT id FROM location WHERE name='Kranjska Gora'), 65),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bohinj'), (SELECT id FROM location WHERE name='Maribor'), 165),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojna Cave'), (SELECT id FROM location WHERE name='Piran'), 60),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojna Cave'), (SELECT id FROM location WHERE name='Kranjska Gora'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojna Cave'), (SELECT id FROM location WHERE name='Maribor'), 120),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Piran'), (SELECT id FROM location WHERE name='Kranjska Gora'), 140),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Piran'), (SELECT id FROM location WHERE name='Maribor'), 195),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Kranjska Gora'), (SELECT id FROM location WHERE name='Maribor'), 180),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Celje'), 60),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Soča Valley'), 120),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Celje'), 80),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Soča Valley'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Maribor'), (SELECT id FROM location WHERE name='Celje'), 40),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Celje'), (SELECT id FROM location WHERE name='Soča Valley'), 175);

-- ########## ADDITIONAL, EXPANDED ROUTES ##########
-- Routes within the Gorenjska region
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Vintgar Gorge'), 10),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bled'), (SELECT id FROM location WHERE name='Bled Castle'), 5),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bohinj'), (SELECT id FROM location WHERE name='Savica Waterfall'), 10),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Kranjska Gora'), (SELECT id FROM location WHERE name='Triglav National Park'), 15);

-- Routes within the Karst and Coastal regions
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojna Cave'), (SELECT id FROM location WHERE name='Predjama Castle'), 15),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojna Cave'), (SELECT id FROM location WHERE name='Škocjan Caves'), 30),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Postojna Cave'), (SELECT id FROM location WHERE name='Lipica Stud Farm'), 30),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Piran'), (SELECT id FROM location WHERE name='Lipica Stud Farm'), 30),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Piran'), (SELECT id FROM location WHERE name='Škocjan Caves'), 40),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lipica Stud Farm'), (SELECT id FROM location WHERE name='Škocjan Caves'), 20);

-- Routes within Eastern Slovenia
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Maribor'), (SELECT id FROM location WHERE name='Ptuj'), 30),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ptuj'), (SELECT id FROM location WHERE name='Celje'), 45),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ljubljana'), (SELECT id FROM location WHERE name='Logar Valley'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Celje'), (SELECT id FROM location WHERE name='Logar Valley'), 75);

-- Routes within the Soča Valley (Goriška region)
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Soča Valley'), (SELECT id FROM location WHERE name='Kobarid Museum'), 20),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Soča Valley'), (SELECT id FROM location WHERE name='Tolmin Gorges'), 40),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Kobarid Museum'), (SELECT id FROM location WHERE name='Tolmin Gorges'), 20);

-- Additional logical inter-regional routes
INSERT INTO route (id, location_a_id, location_b_id, travel_time_minutes) VALUES
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Kranjska Gora'), (SELECT id FROM location WHERE name='Soča Valley'), 45), -- Via Vršič Pass (seasonal!)
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Ptuj'), (SELECT id FROM location WHERE name='Ljubljana'), 90),
                                                                              (gen_random_uuid(), (SELECT id FROM location WHERE name='Lake Bohinj'), (SELECT id FROM location WHERE name='Tolmin Gorges'), 60); -- Road via Soriška planina

-- ########## 5. CREATE RULE PARAMETERS ##########
INSERT INTO rule_parameter (param_key, param_value) VALUES
                                                        ('BUDGET_LIMIT_LOW', 15.0),
                                                        ('BUDGET_LIMIT_MEDIUM', 40.0);