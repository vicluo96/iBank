# BrokeBank SetUp
## Step 1: Software Setup
- Make sure you've installed IntelliJ and MySQL on your local machine
## Step 2: Env Setup
1. Git clone this repo
2. Open `BrokeBank` with Intellij and there should be a pop-up window in the right corner with title `Maven build script found`, then click `load`
3. If not, right click `Backend/pom.xml` file and click `Add as Maven Project`
4. Replace the username and password fields in `Backend/src/main/resources/application.yml` with your MySQL username and password 
## Step 2: MySQL Database Setup
1. Open MySQL Server
2. Connect to Local Instance 3306
3. Execute the `db/bank_dump.sql` file, it will initialize the needed database
## Step 3: Run Application
1. Backend: Right click `Backend/src/main/java/com/group3/brokebank/BrokeBankApplication.java`, and then click `Run 'BrokeBankAppli.....main()'`
2. Frontend: Open terminal in IntelliJ and enter`cd frontend`, and then`npm install`, finally `npm start`
3. You should see the BrokeBank running on `http://localhost:3000` in your browser
4. If not, paste the url into your browser