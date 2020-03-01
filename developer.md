# Coding standart

This is our Java coding standard.


File names, packages:
* Use only lowercase letters in package names

  For example: `package company.com.project; NOT ~~package Company.Com.Project;~~`
* The first letters of all words in class names are uppercase

  For example: `public class MyFirstClass`
* Class names must be nouns

  For example: `public class MyFirstClass; NOT ~~public class AddPlus~~`

Method and variable names
* The first letter must be lowercase in the name of the methods, and the first letters of inner words - uppercase letters

  For example: `void addMatrix(...)`
* Variable names begin with a lowercase letter, and the first letters of inner words - uppercase letters

  For example: `int numberIssue`
* All letters are uppercase in the names of constants, the words are separated by lower underscores

  For example: `static final public END_OF_FILE = -1;`

Indentation, line breaks
* The line doesn’t exceed 80 characters
* If it exceeds, then you need to break it into several lines:
  * Transfer after a comma and before the operator
  * Use indent two tabulation to indicate nesting
  
  For example: 
  ```int result = function1(firstOperator,
         function2(secondOperator,
         thirdOperator);`
* Use indent tabulation

Arrangement of brackets, operators, blocks
* The definition of variables must be placed at the beginning of the block

  For example: 
  ```void method() {
         int result = 0;
         int i = 0;
         ...
         for (i = 0; i < N; i++) {
         ...
         }
     }`
* There is no space between the method name and the brackets for the parameters

  For example: `void add(int firstNumber)`
* Space separated:
  * Function parameters
  
    For example: `void add(int firstNumber, int secondNumber)`
  * Operators
  
    For example: `int result = a + b;`
  * Keyword (if, while, for) and the next bracket
  
    For example: `if ( a < b )`
* Opening bracket { doesn’t fit on a separate line

  For example: `void add(int firstNumber) {`
* The closing bracket } is aligned with the start line of this block and is located on a new line

  For example: 
  ```void method() {
         for (int i = 0; i < N; i++) {
         ...
         }
     }`
* Methods are separated by an empty string
* There is only one statement on the line

  For example: 
  ```if (a < b) {
         return a;
     }`

Code structuring
* Methods should be short and with comments
* Lack of magic numbers
  For example: `NOT ~~if ( a > 10)~~`

Such rules are accepted in our team.
