# WAProgrammingTest

Feature Specification
Make a simple web interface where users can upload a CSV file consisting of a column called Id (consisting of unique int identifiers), another column called Decision consisting of either a 0 or 1 and an arbitrary number of "variable" columns consisting of numeric data.

The app should parse the csv file and remove any records (rows) which meet both of the following conditions:
●	Have a Decision of 0
●	For each variable (column), no value falls between FMIN and FMAX.
Where FMIN and FMAX are the smallest and largest value for that variable across all records with a decision value of 1.

This new stripped down data should then be shown to the user in tabular format.

Please see some example input and output in this folder for clarification (output example are in csv form, but we expect the web interface to display output in your solution)
The deliverable
Imagine we are the other developer you have to hand off to, at the very least we should be able to compile and run the code relatively straight forwardly. The use of free 3rd party libraries and frameworks is encouraged.
