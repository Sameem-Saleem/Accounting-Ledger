# Accounting Ledger
This application can help track all financial transactions for a business or for personal use, with a simple terminal interface including multiple menus to streamline user-experience.

Note that information entered must follow the guidelines expressed in the documentation and this `README.md`, otherwise, if an error occurs, entered transactions will not be saved to the `transactions.csv` or specific file.



## Table Of Content
- [Start Screen](#start-screen)
    - [Access](#access)
    - [Default](#default)
    - [Exit](#exit)
- [Home Screen](#home-screen)
    - [Add Deposit](#add-deposit)
    - [Make Payment](#make-payment)
    - [Ledger](#ledger)
    - [Exit](#exit)
- [Ledger Screen](#ledger-screen)
    - [All](#all)
    - [Deposits](#deposits)
    - [Payments](#payments)
    - [Reports](#reports)
    - [Home](#home)
- [Reports Screen](#reports-screen)
    - [Month To Date](#month-to-date)
    - [Previous Month](#previous-month)
    - [Year To Date](#year-to-date)
    - [Previous Year](#previous-year)
    - [Search by Vendor](#search-by-vendor)
    - [Custom Search](#custom-search)
    - [Back](#back)
- [Interesting Code](#interesting-code)
    - [Custom Search Logic](#custom-search-logic)
    - [Descending Order Logic](#descending-order-logic)
    - [Using Data Class](#using-data-class)
    - [Display Logic](#display-logic)



## Installation
You can pull this repository locally to access the program. Make sure to use a Java IDE to run the program, or manually compile with Javac and run with Java. Steps are below:
1. `git clone https://github.com/Sameem-Saleem/Accounting-Ledger.git`
2. `javac src/main/java/com/pluralsight/Main`
3. `java src/main/java/com/pluralsight/Main`

Suggested software:
- [Java JDK](https://www.oracle.com/th/java/technologies/downloads) 
- [JetBrains IntelliJ](https://www.jetbrains.com/idea/download/)


If you have **previous transactions** you want to import, you must **add them manually** into the **transactions.csv** file (or whatever csv file you want to create), splitting the fields like the following (also, add the following line to the front of the file):
`DATE|TIME|DESCRIPTION|VENDOR|AMOUNT`

Example:
`2022-01-01|00:20:59|Software Installation|Sameem Saleem|500.0`

Notes:
- The **date** *must be* in `YYYY-MM-DD` format
- The **time** *must be* in `HH:MM:SS` 24 hour format 
- The **amount** *must be* in a float format, such as: `-50.0` 



## Start Screen
The start screen looks like the following:

![Start Screen](https://cdn.discordapp.com/attachments/219982898550276096/1296933134943653898/Screenshot_2024-10-18_132712.png?ex=6714169b&is=6712c51b&hm=6120de22ed30e423e8ce86a5c1a3392bbb24686325b5cc39b2b704742821f590&)

It provides the options to access a specific file, access the default file, and exit the program. The option chosen here determines what file the rest of the runtime will write to.

The user must enter the key correlating to the menu item, and press enter. *This is not case-sensitive.*



### Access
This option takes in an access point, or filename, creating or loading the corresponding file. 

If the file is newly created, it will prepend `DATE|TIME|DESCRIPTION|VENDOR|AMOUNT` to the front of the file.



### Default
This option uses the default `transactions.csv` file as an access point, loading the `transactions.csv` file. 



### Exit
This option closes the program.



## Home Screen
The home screen looks like the following:

![Home Screen](https://cdn.discordapp.com/attachments/219982898550276096/1296867436905234482/Screenshot_2024-10-18_090618.png?ex=6713d96b&is=671287eb&hm=2134b3507997b949a4043fa95cf74310289c78638c14db5e9314067895793a9f&)

It provides the options to add a deposit, make a payment, access the ledger menu, and exit the program. 

The user must enter the key correlating to the menu item, and press enter. *This is not case-sensitive.*



### Add Deposit
This option takes in a description, vendor, and deposit amount, generating the date and time via system. 

This information is then appended in to the `transactions.csv` file or accessed file in `DATE|TIME|DESCRIPTION|VENDOR|AMOUNT` format, where the amount if positive, indicating a deposit.

The following image displays adding a deposit:

![Deposit Example](https://cdn.discordapp.com/attachments/219982898550276096/1296870389481341062/Screenshot_2024-10-18_091809.png?ex=6713dc2b&is=67128aab&hm=54209c2336c17316f04861f9dd4694a6d88f42af1f570e087abcefc8e69825de&)



### Make Payment
This option takes in a description, vendor, and amount of payment, generating the date and time via system. 

This information is then appended in to the `transactions.csv` file or accessed file in `DATE|TIME|DESCRIPTION|VENDOR|AMOUNT` format, where the amount if negative, indicating a payment.

The following image displays making a payment:

![Payment Example](https://cdn.discordapp.com/attachments/219982898550276096/1296870612320518226/Screenshot_2024-10-18_091834.png?ex=6713dc60&is=67128ae0&hm=f38b2346edabfe667e9e3ab2bbaa945c2c04270da5f81dfb7fbe7466eb2edce4&)



### Ledger 
This option puts the user into the [ledger section](#ledger-screen).

Here, they may access various options to view their data.



### Exit
This option saves changes to the file and closes the program.

**Note that additions may not be written to the file if this option is not reached** or a misinput causes a crash in the program.



## Ledger screen
The ledger screen looks like the following:

![Ledger Screen](https://cdn.discordapp.com/attachments/219982898550276096/1296868157633466459/Screenshot_2024-10-18_091007.png?ex=6713da17&is=67128897&hm=1e4025ca07b27a8e1e52f6f0fdd5a111543510563a9b1dd424f4d583b69c0eb7&)

It provides the options to view all payments and deposits, view only payments, view only deposits, access the report menu, and return back to the home screen. 

The user must enter the key correlating to the menu item, and press enter. This is not case-sensitive.



### All
This option outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, descending order of date created.



### Deposits
This option outputs all deposits created during runtime and saved in the `transactions.csv` file or accessed file, descending order of date created.



### Payments
This option outputs all payments created during runtime and saved in the `transactions.csv` file or accessed file, descending order of date created.



### Reports
This option puts the user into the [reports section](#reports-screen).

Here, they may access various options to filter and view their data.



### Home
This option returns the user to the [home screen](#home-screen).



## Reports Screen
The reports screen looks like the following:

![Reports Screen](https://cdn.discordapp.com/attachments/219982898550276096/1296868859500036117/Screenshot_2024-10-18_091235.png?ex=6713dabe&is=6712893e&hm=d731088fc7c6208cd1c5b4741c8dbeda652ee7225d17145f851c9a9dae03d7e0&)

It provides the options to filter transactions, use the custom search feature, and return back to the ledger screen. 



### Month To Date
This option outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, **that were made during the current month**, descending order of date created.



### Previous Month
This option outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, **that were made last month**, descending order of date created.



### Year To Date
This option outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, **that were made during the current year**, descending order of date created.



### Previous Year
This option outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, **that were made last year**, descending order of date created.



### Search by Vendor
This option first asks for an input (a vendor to match transactions with).

Then, it outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, **that have a matching vendor to the input**, descending order of date created.



### Custom Search
This option prompts the following:
- What date to start searching from
- What date to stop searching at
- Description
- Vendor
- Amount
For any of these options, the user can click enter without typing anything, and it will skip that filter.

Then, it outputs all transactions, including both payments and deposits, created during runtime and saved in the `transactions.csv` file or accessed file, **that match all of the entered parameters**, descending order of date created.



### Back
This option returns the user to the [ledger screen](#ledger-screen).



## Interesting Code
The following are fun and interesting parts I encountered while programming this application.



### Custom Search Logic
I thought this would be much harder than it ended up being, it made me glad that IntelliJ suggested to change code, because now that I know this, it makes filtering from an array much easier! Previously, my logic was going to take up a lot of data and space. Additionally, I learned that you can delete objects from an `ArrayList` via the `Object`, not just the index.

![Search Logic](https://media.discordapp.net/attachments/219982898550276096/1296877744642326649/Screenshot_2024-10-18_094116.png?ex=6713e305&is=67129185&hm=0722bb46a72d07089be40130e2e106645027ca39eb1de21000c07831ab4c622b&=&format=webp&quality=lossless)



### Descending Order Logic
I was really worried about this part! It turned out, `Collections` had a method that could reverse it for you, I wasn't exactly sure how to do this on my own. Additionally, by creating a copy, when using my custom search, I'm not affecting the original `data` ArrayList.

![Descending Logic](https://media.discordapp.net/attachments/219982898550276096/1296877730817638430/Screenshot_2024-10-18_094124.png?ex=6713e301&is=67129181&hm=45404f1bd52be6a75045d931002c50eaa94cee44b5530ccd60c8054ac79d6580&=&format=webp&quality=lossless)



### Using Data Class
I didn't think this was all too necessary at first, but it makes life so much easier! When I made data classes for `Transaction` and `TransactionData`, it made things like this unbelievable fast and easy, as you can see:

![Vendor Logic](https://media.discordapp.net/attachments/219982898550276096/1296877720583671869/Screenshot_2024-10-18_094431.png?ex=6713e2ff&is=6712917f&hm=8d422c55b1ec6d4376a43ed1099f43cc5fe9ace54058550c493cdeebcd9e2ba5&=&format=webp&quality=lossless)



### Display Logic
I found a way to use switch-case that made my menus look very clean in the code and very simple to understand. I like this a lot and will definitely use it in the future when I'm doing switch-case, the best part is no need for `break`!

![Home Logic](https://media.discordapp.net/attachments/219982898550276096/1296877709745459221/Screenshot_2024-10-18_094806.png?ex=6713e2fc&is=6712917c&hm=98cd24696813a1243a90b3c9a9b117e9026f22c99d2070eb823487dadcb7fcbd&=&format=webp&quality=lossless)
