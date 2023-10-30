drop schema if exists `asm-03-spring-boot`;

create schema `asm-03-spring-boot`;

use `asm-03-spring-boot`;

set FOREIGN_KEY_CHECKS = 0;

CREATE TABLE user (
    `id` INT AUTO_INCREMENT NOT NULL,
	`name` VARCHAR(255) DEFAULT NULL,
	`gender` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL unique,
    `phone_number` VARCHAR(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `detail` varchar(255) DEFAULT NULL,
    `role_id` int(11) default null,
       PRIMARY KEY (`id`),
       foreign key (`role_id`) references role(id)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO user (name, gender, email, phone_number, password, address, description, detail, role_id)
VALUES
    ('Doctor John', 'Male', 'john@example.com', '1234567890', 'password123', '123 Main St', 'Description 1', 'Detail 1', 1),
  	('Admin', 'Male', 'admin@funix.edu.vn', '2222222222', '$2a$10$IkAYbpS4TsZBfftfC7MH3OOGCj7ghDIHagagLGS.D3FcqUSdNk3ka', '222 Maple St', 'Description 5', 'Detail 5', 3),
    ('Emily Davis', 'Female', 'emily@example.com', '5551234567', 'testpass', '789 Maple Rd', 'Registered Nurse', 'Dedicated to patient care and education', 1),
    ('Daniel Miller', 'Male', 'daniel@example.com', '2223334444', 'secretword', '456 Pine Ln', 'Neurologist', 'Expert in treating neurological disorders', 1),
    ('Sophia Wilson', 'Female', 'sophia@example.com', '1112223333', 'pass123word', '789 Birch St', 'Dermatologist', 'Specializes in skincare and cosmetic procedures', 1),
    ('Liam Martinez', 'Male', 'liam@example.com', '4445556666', 'mysecretpass', '123 Oak St', 'Cardiologist', 'Passionate about heart health and prevention', 1),
    ('Olivia Jackson', 'Female', 'olivia@example.com', '7778889999', 'mypassword123', '456 Elm Rd', 'Pediatrician', 'Caring for the health of young patients', 1),
    ('William Brown', 'Male', 'william@example.com', '8889990000', 'secure123pass', '789 Maple Ave', 'Ophthalmologist', 'Focused on eye care and vision health', 1);



CREATE TABLE role (
    `id` INT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

insert into role (name) values('DOCTOR'),('PATIENT'),('ADMIN'),('GUESS');

CREATE TABLE specialization (
    `id` INT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;
INSERT INTO specialization (name, description) VALUES
    ('Cardiology', 'Deals with heart-related issues'),
    ('Dermatology', 'Focuses on skin-related problems'),
    ('Neurology', 'Specializes in nervous system disorders'),
    ('Orthopedics', 'Deals with bone and joint issues'),
    ('Pediatrics', 'Focuses on medical care for children'),
    ('Ophthalmology', 'Specializes in eye-related problems'),
    ('Gastroenterology', 'Deals with digestive system disorders'),
    ('Endocrinology', 'Specializes in hormone-related conditions'),
    ('Oncology', 'Focuses on cancer treatment and care'),
    ('Psychiatry', 'Deals with mental health and disorders');

CREATE TABLE clinic (
    `id` INT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `phone` VARCHAR(255) DEFAULT NULL,
    `time_working` varchar(255) default null,
    `description` VARCHAR(255) DEFAULT NULL,
    `category_id` int(11),
    `price` varchar(255) default null,
    PRIMARY KEY (`id`),
    foreign key(`category_id`) references category(`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;
INSERT INTO clinic (name, address, phone, time_working, description, category_id, price) VALUES
    ('115 Hospital', 'Ho Chi Minh City', '+1 123-456-7890', 'Mon-Fri: 8:00 AM - 6:00 PM', 'A family-oriented clinic',1,500),
    ('Cho Ray Hospital', 'Ho Chi Minh City', '+1 987-654-3210', 'Mon-Sat: 9:00 AM - 7:00 PM', 'A modern clinic with advanced facilities',1,2000),
    ('Thong nhat Hospital', 'Ho Chi Minh City', '+1 555-123-4567', 'Mon-Fri: 8:30 AM - 5:30 PM', 'A small clinic in the city',2,700),
    ('Strong Bones Hospital', '321 Pine Lane, City D', '+1 111-222-3333', 'Mon-Sun: 24/7', 'A hospital with expertise in bone-related treatments',2,500),
    ('Kids Care Medical', '654 Cedar Drive, City E', '+1 444-555-6666', 'Mon-Fri: 9:00 AM - 5:00 PM', 'A medical center specializing in pediatric care',3,300),
    ('Healing Hands Clinic', '777 Maple Lane, City F', '+1 999-888-7777', 'Mon-Sat: 10:00 AM - 6:00 PM', 'A clinic providing holistic healing services',3,400),
    ('Dental Care Center', '456 Elm Street, City G', '+1 222-333-4444', 'Mon-Fri: 9:30 AM - 7:30 PM', 'A center for dental care and treatments',4,600),
    ('Vision Care Clinic', '987 Oak Drive, City H', '+1 888-999-1111', 'Mon-Fri: 8:30 AM - 5:30 PM', 'A clinic focused on vision and eye care',4,800),
    ('Mental Health Center', '555 Pine Avenue, City I', '+1 777-888-9999', 'Mon-Sun: 24/7', 'A center offering mental health support and therapy',5,300),
    ('Family Wellness Clinic', '789 Cedar Road, City J', '+1 666-555-4444', 'Mon-Sat: 9:00 AM - 6:00 PM', 'A clinic promoting family wellness and preventive care',5,600);

CREATE TABLE doctor_user (
    `id` INT AUTO_INCREMENT NOT NULL,
    `doctor_id` INT DEFAULT NULL,
    `specialization_id` INT DEFAULT NULL,
    `clinic_id` INT DEFAULT NULL,
    `doctor_info_id` int default null,
    `status_id` int default 1,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`doctor_id`)
        REFERENCES user (`id`),
    FOREIGN KEY (`specialization_id`)
        REFERENCES specialization (`id`),
    FOREIGN KEY (`clinic_id`)
        REFERENCES clinic (`id`),
        foreign key(`doctor_info_id`) references doctor_info(`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO doctor_user (doctor_id, specialization_id, clinic_id) VALUES
    (1, 1, 1),
    (3, 2, 2),
    (4, 3, 3),
    (5, 1, 1),
    (6, 2, 2),
    (7, 3, 2),
    (8, 4, 2);
    
CREATE TABLE doctor_info (
    `id` INT AUTO_INCREMENT NOT NULL,
    `general_introduction` TEXT,
    `training_process` TEXT,
    `achievements` TEXT,
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;
    
    
CREATE TABLE patient (
    `id` INT AUTO_INCREMENT NOT NULL,
    `doctor_id` INT(11) DEFAULT NULL,
    `patient_id` INT DEFAULT NULL,
    `status_id` INT(11) DEFAULT NULL,
    `name` VARCHAR(255) DEFAULT NULL,
    `medical_information_id` int (11),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`doctor_id`)
        REFERENCES doctor_user (id),
    FOREIGN KEY (`patient_id`)
        REFERENCES user (`id`),
	foreign key (`medical_information_id`) references medical_information(`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

-- INSERT INTO patient (doctor_id, patient_id, status_id, name, medical_information_id) VALUES
--     (1,2, 1, 'Patient1',1),
--     (1,3, 2, 'Patient2',2);
--     (1,4, 1, 'Patient3',2);
--     (2,5, 2, 'Emma Brown'),
--     (3,6, 1, 'Michael Williams');

    
CREATE TABLE schedule (
    `id` INT AUTO_INCREMENT NOT NULL,
    `date` varchar(255) DEFAULT NULL,
    `time` varchar(255) DEFAULT NULL,
    `max_booking` INT DEFAULT NULL,
    `sum_booking` INT DEFAULT NULL,
    `doctor_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`doctor_id`)
        REFERENCES doctor_user (id)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;
INSERT INTO schedule (date, time, max_booking, sum_booking, doctor_id) VALUES
    ('2023-08-03', '09:00:00', 10, 5, 1),
    ('2023-08-04', '10:30:00', 10, 6, 3),
    ('2023-08-05', '14:00:00', 10, 7, 4),
    ('2023-08-06', '16:45:00', 10, 4, 1),
    ('2023-08-07', '11:15:00', 10, 5, 3);
--     ('2023-08-08', '12:30:00', 10, 6, 2),
--     ('2023-08-09', '08:45:00', 10, 7, 3),
--     ('2023-08-10', '15:20:00', 10, 7, 3),
--     ('2023-08-11', '17:00:00', 10, 7, 3),
--     ('2023-08-12', '13:10:00', 10, 7, 4);

create table category (
`id` int auto_increment not null,
`name` VARCHAR(255) DEFAULT NULL,
primary key(`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO category (name)
VALUES 
('Polymetic'),
('Psychology'),
('Neurology'),
('Cardiology'),
('Dentistry');

create table appointment(
`id` int auto_increment not null,
`time_booking` varchar (255) default null,
`date_booking` varchar (255) default null,
`doctor_id` int(11),
`patient_id` int(11),
`schedule_id` int(11),
`status` tinyint(1),
primary key (`id`),
foreign key(`doctor_id`) references doctor_user(`id`),
foreign key(`patient_id`) references patient(`id`),
foreign key(`schedule_id`) references schedule(`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

-- INSERT INTO appointment (time_booking, date_booking, doctor_id, patient_id, schedule_id, status) VALUES
-- ('09:00:00', '2023-08-03', 1, 1,1, 0),
-- ('02:30:00', '2023-08-04', 1, 2,2, 0),
-- ('11:00:00', '2023-08-12', 1, 3,3, 0),
-- ('04:15:00', '2023-08-13', 4, 4,4,0),
-- ('08:30:00', '2023-08-14', 5, 5,5,0);


create table medical_information(
`id` int auto_increment not null,
`basic_disease` varchar(255),
`detailed_description` varchar(255),
primary key (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;
INSERT INTO medical_information (basic_disease, detailed_description)
VALUES 
('Hypertension', 'Patient has been diagnosed with hypertension. Blood pressure readings consistently elevated.'),
('Diabetes', 'Patient has type 2 diabetes. Requires regular blood sugar monitoring and insulin injections.'),
('Asthma', 'Patient suffers from chronic asthma. Frequent use of inhaler for symptom relief.')







