# Java implementation of a Bank object

## Account
The Account class is a basic outline of a bank account, detailing personal information such as, 
- First and Last name
- Unique Account number
- Balance
- and user validated, plaintext passwords (which should **NOT** be emulated.)

The account class also keeps a static tally of total accounts created across all Bank objects.

## Bank
The Bank class implements a Bank object, detailing the 
- Total customers accrued
- if said bank FDIC Ensured
- and who is logged in.

## Card
Card is the super class of the CreditCard and DebitCard classes.
# Credit Card
Credit Card implements typical CC values
- Balance
- Limit
- and Interest
# Debit Card
Debit Card implements the `Card` class exclusively, as it just represents a Debit card.

