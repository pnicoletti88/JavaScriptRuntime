# JavaScriptRuntime

Fun project to write a simple javascript style runtime. Examples usages can be seen in the integration tests: https://github.com/pnicoletti88/JavaScriptRuntime/tree/master/src/test/java/com/Processes

Supported Features:
- Variable decleration: 
    - Number, String, Boolean and Undefined data type currently supported. 
    - Declare variable with "let".
- Variable reassignment: 
    - Variables do not have a type and can hold anytype of data
    - Variables have a scope, and the same variable can be redeclared in child scope.
- Mathematical operations: 
    - +, -, *, / are currently supported for Numerical operations
    - \+ is supported for strings
- Boolean operations: 
    - <, <=, ==, !=, >=, > for numbers
    - ==, != for strings
    - ==, !=, &&, || for booleans
- Loops: 
    - Simple for loop fully supported
    - Simple while loop fully supported
- Functions: 
    - Functions are fully supported. 
    - Syntax to declare a function: function name(params){body}
- Conditionals:
    - if, elif, and else are fully supported
    - Note that {} is required: ex if(true){do things...}
- setTimeout:
    - Non-blocking setTimeout is supported
