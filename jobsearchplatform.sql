drop schema if exists `asm-02-spring-boot`;

create schema `asm-02-spring-boot`;

use `asm-02-spring-boot`;

set FOREIGN_KEY_CHECKS = 0;

CREATE TABLE company (
    `id` INT AUTO_INCREMENT NOT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `description` TEXT DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `logo` VARCHAR(255) DEFAULT NULL,
    `company_name` VARCHAR(255) DEFAULT NULL,
    `phone_number` VARCHAR(255) DEFAULT NULL,
    `status` INT(11) DEFAULT NULL,
    `user_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`)
        REFERENCES user (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO company (address, description, email, logo, company_name, phone_number, status, user_id)
VALUES 
('Ho Chi Minh City', 'A technology company specializing in software development.', 'info@company1.com', 'fpt-logo.png', 'FPT SoftWare', '+1 (123) 456-7890', 1,1),
('Silicon Valley', 'A multinational e-commerce company.', 'info@company2.com', 'google-logo.jpeg', 'Google', '+1 (987) 654-3210', 1,2),
('789 Oak St, London', 'A global consulting firm providing business solutions.', 'info@company3.com', 'Microsoft_logo.svg.png', 'Microsoft', '+44 1234 567890', 1,3),
('1 Hacker Way, Menlo Park', 'A social media and technology company.', 'info@company4.com', 'meta-logo.jpeg', 'Meta', '+1 (123) 456-7890', 1,4),
('701 First Avenue, Sunnyvale', 'A multinational technology company specializing in hardware and software products.', 'info@company5.com', 'apple-logo.avif', 'Apple Inc.', '+1 (987) 654-3210', 1,5),
('410 Terry Avenue North, Seattle', 'An American multinational technology company that focuses on e-commerce, cloud computing, digital streaming, and artificial intelligence.', 'info@company6.com', 'amazon-logo.jpeg', 'Amazon', '+1 (123) 456-7890', 1,6),
('1600 Amphitheatre Parkway, Mountain View', 'A technology company specializing in Internet-related services and products.', 'info@company7.com', 'alphabet-logo.jpeg', 'Alphabet Inc.', '+1 (987) 654-3210', 1,7),
('350 5th Ave, New York', 'A global financial services firm providing investment banking, securities, and wealth management services.', 'info@company8.com', 'goldmansacks-logo.webp', 'Goldman Sachs', '+1 (123) 456-7890', 1,8),
('345 Park Ave, San Jose', 'A multinational technology company that designs, manufactures, and sells networking equipment.', 'info@company9.com', 'cisco-logo.png', 'Cisco Systems', '+1 (987) 654-3210', 1,9),
('221 Baker Street, London', 'A British multinational technology company specializing in Internet-related services and products.', 'info@company10.com', 'rakuten-logo.jpeg', 'Rakuten', '+44 1234 567890', 1,10);


CREATE TABLE recruitment (
    `id` INT AUTO_INCREMENT NOT NULL,
    `title` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `experience` VARCHAR(255) DEFAULT NULL,
    `quantity` INT(11) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `deadline` VARCHAR(255) DEFAULT NULL,
    `salary` VARCHAR(255) DEFAULT NULL,
    `created_at` VARCHAR(255) DEFAULT NULL,
    `type` VARCHAR(255) DEFAULT NULL,	
    `job_rank` VARCHAR(255) DEFAULT NULL,
    `status` INT(11) DEFAULT NULL,
    `company_id` INT(11) DEFAULT NULL,
    `category_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`company_id`)
        REFERENCES company (`id`),
    FOREIGN KEY (`category_id`)
        REFERENCES category (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO recruitment (`title`, `description`, `experience`, `quantity`, `address`, `deadline`, `salary`, `created_at`, `type`, `job_rank`, `status`, `company_id`, `category_id`)
VALUES
  ('Software Engineer', 'Looking for a skilled Software Engineer', '2+ years', 5, 'Ho Chi Minh City', '2023-08-30', '8000', '2023-07-19', 'Full-Time', 'Senior', 1, 1, 1),
  ('BrSE', 'Seeking an experienced Bride System Engineer', '3+ years', 3, 'Tokyo Japan', '2023-09-15', '8000', '2023-07-20', 'Full-Time', 'Manager', 1, 1, 1),
  ('Data Analyst', 'Data Analyst position for entry-level candidates', '1+ years', 2, 'Ha Noi City', '2023-08-31', '7000', '2023-07-21', 'Full-Time', 'Lead', 1, 1, 1),
  ('Accountant', 'Experienced Accountant needed for financial analysis', '4+ years', 4, 'Da Nang City', '2023-09-10', '6000', '2023-07-22', 'Full-Time', 'Senior', 1, 1, 1),
  ('Graphic Designer', 'Creative Graphic Designer wanted for marketing materials', '2+ years', 3, 'Can Tho City', '2023-09-25', '5000', '2023-07-23', 'Full-Time', 'Intermediate', 1, 1, 2),
  ('Sales Representative', 'Sales Representative position for motivated individuals', '1+ years', 5, '333 Birch St', '2023-08-20', '4000', '2023-07-24', 'Full-Time', 'Junior', 1, 2, 2),
  ('HR Manager', 'Experienced HR Manager required for talent acquisition', '5+ years', 1, '444 Cedar St', '2023-09-05', '3000', '2023-07-25', 'Full-Time', 'Senior', 1, 2, 2),
  ('Customer Support', 'Customer Support role for assisting customers', '1+ years', 2, '555 Oak St', '2023-08-15', '3000', '2023-07-26', 'Part-Time', 'Junior', 1, 2, 3),
  ('IT Technician', 'IT Technician position for troubleshooting', '3+ years', 4, '666 Elm St', '2023-09-28', '3000', '2023-07-27', 'Full-Time', 'Intermediate', 1, 3, 3),
  ('Project Manager', 'Experienced Project Manager required for managing projects', '6+ years', 1, '777 Pine St', '2023-09-18', '3000', '2023-07-28', 'Full-Time', 'Senior', 1, 3, 4);



CREATE TABLE applypost (
    `id` INT AUTO_INCREMENT NOT NULL,
    `image` varchar(255) default null,
    `created_at` VARCHAR(255) DEFAULT NULL,
    `name_cv` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `status` INT(11) DEFAULT NULL,
    `recruitment_id` INT(11) DEFAULT NULL,
    `user_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`recruitment_id`)
        REFERENCES recruitment (`id`),
    FOREIGN KEY (`user_id`)
        REFERENCES user (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

-- INSERT INTO applypost (created_at, name_cv, email, address, description, status, recruitment_id, user_id)
-- VALUES 
--     ('2023-07-16', 'John Doe CV', 'john.doe@example.com', '123 Main St, City A, Country X', 'I am interested in the Software Engineer position.', 1, 1, 1),
--     ('2023-07-16', 'Jane Smith CV', 'jane.smith@example.com', '456 Elm St, City B, Country Y', 'I have experience in UX/UI design and would love to join your team.', 1, 2, 2),
--     ('2023-07-16', 'Mike Johnson CV', 'mike.johnson@example.com', '789 Oak St, City C, Country Z', 'I am a business analyst with a strong analytical background.', 1, 3, 3),
--     ('2023-07-16', 'Sarah Thompson CV', 'sarah.thompson@example.com', '111 Pine St, City D, Country W', 'I have a passion for software development and problem-solving.', 1, 4, 4),
--     ('2023-07-16', 'Robert Brown CV', 'robert.brown@example.com', '222 Birch St, City E, Country V', 'I am experienced in hardware design and interested in joining your company.', 1, 5, 5),
--     ('2023-07-16', 'Emily Davis CV', 'emily.davis@example.com', '333 Cedar St, City F, Country U', 'I have extensive knowledge in e-commerce and online marketing.', 1, 6, 6),
--     ('2023-07-16', 'Michael Wilson CV', 'michael.wilson@example.com', '444 Maple St, City G, Country T', 'I am a software engineer with expertise in developing cutting-edge applications.', 1, 7, 7),
--     ('2023-07-16', 'Sophia Anderson CV', 'sophia.anderson@example.com', '555 Walnut St, City H, Country S', 'I have a background in finance and keen interest in investment banking.', 1, 8, 8),
--     ('2023-07-16', 'David Taylor CV', 'david.taylor@example.com', '666 Pine St, City I, Country R', 'I have experience in networking and network security.', 1, 9, 9),
--     ('2023-07-16', 'Olivia Thomas CV', 'olivia.thomas@example.com', '777 Elm St, City J, Country Q', 'I am a software engineer with a focus on web development.', 1, 10, 10);

CREATE TABLE user (
    `id` INT AUTO_INCREMENT NOT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `full_name` VARCHAR(255) DEFAULT NULL,
    `image` VARCHAR(255) DEFAULT NULL,
    `password` VARCHAR(128) DEFAULT NULL,
    `phone_number` VARCHAR(255) DEFAULT NULL,
    `status` INT(11) DEFAULT NULL,
    `role_id` INT(11) DEFAULT NULL,
    `cv_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`)
        REFERENCES role (`id`),
            FOREIGN KEY (`cv_id`)
        REFERENCES cv (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO user (address, description, email, full_name, image, password, phone_number, status, role_id)
VALUES 
('123 Main St, New York', 'Software engineer with 5+ years of experience.', 'mentor@gmail.com', 'Mentor Funix', 'elon-musk.png', 'password1', '+1 (123) 456-7890', 0,1),
('456 Elm St, San Francisco', 'UX/UI designer passionate about creating intuitive user experiences.', 'xter@gmail.com', 'Thanh Trinh', 'profile2.jpg', 'password2', '+1 (987) 654-3210', 1,2),
('789 Oak St, London', 'Business analyst with expertise in data analysis and reporting.', 'mentor2@gmail.com', 'Bill Gates', 'bill-gates-ava.png', 'password3', '+44 1234 567890', 1,1),
('1 Hacker Way, Menlo Park', 'Experienced software engineer with a focus on backend development.', 'sarah@example.com', 'Sarah Thompson', 'profile4.jpg', 'password4', '+1 (123) 456-7890', 1,1),
('701 First Avenue, Sunnyvale', 'Hardware engineer with expertise in designing cutting-edge products.', 'robert@example.com', 'Robert Brown', 'profile5.jpg', 'password5', '+1 (987) 654-3210', 1,1),
('410 Terry Avenue North, Seattle', 'E-commerce specialist with a strong background in online marketing.', 'emily@example.com', 'Emily Davis', 'profile6.jpg', 'password6', '+1 (123) 456-7890', 1,1),
('1600 Amphitheatre Parkway, Mountain View', 'Experienced software engineer specializing in web development.', 'michael@example.com', 'Michael Wilson', 'profile7.jpg', 'password7', '+1 (987) 654-3210', 1,1),
('350 5th Ave, New York', 'Finance professional with expertise in investment banking.', 'sophia@example.com', 'Sophia Anderson', 'profile8.jpg', 'password8', '+1 (123) 456-7890', 1,1),
('345 Park Ave, San Jose', 'Network engineer experienced in designing and managing enterprise networks.', 'david@example.com', 'David Taylor', 'profile9.jpg', 'password9', '+1 (987) 654-3210', 1,1),
('221 Baker Street, London', 'Software engineer specializing in mobile application development.', 'olivia@example.com', 'Olivia Thomas', 'profile10.jpg', 'password10', '+44 1234 567890', 1,1);


create table category (
`id` int auto_increment not null,
`name` VARCHAR(255) DEFAULT NULL,
`number_choose` int(11) default null ,
primary key(`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

INSERT INTO category (name, number_choose)
VALUES 
('Software Development', 4),
('Web Development', 3),
('Data Science', 2),
('UX/UI Design', 1),
('Business Analytis', 0),
('DevOps', 0);

CREATE TABLE role (
    `id` INT AUTO_INCREMENT NOT NULL,
    `role_name` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

insert into role(role_name) values ('HR'), ('Applicant');

CREATE TABLE cv (
    `id` INT AUTO_INCREMENT NOT NULL,
    `file_name` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

CREATE TABLE save_job (
    `id` INT AUTO_INCREMENT NOT NULL,
    `recruitment_id` INT(11) DEFAULT NULL,
    `user_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`recruitment_id`)
        REFERENCES recruitment (`id`),
    FOREIGN KEY (`user_id`)
        REFERENCES user (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;

create table follow_company(
    `id` INT AUTO_INCREMENT NOT NULL,
    `company_id` INT(11) DEFAULT NULL,
    `user_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`company_id`)
        REFERENCES company (`id`),
    FOREIGN KEY (`user_id`)
        REFERENCES user (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=LATIN1;


