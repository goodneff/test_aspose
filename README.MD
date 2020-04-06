# T9 Spelling

## Overview

This is a repository with a solution for `T9 Spelling` problem described [here](https://code.google.com/codejam/contest/dashboard?c=351101#s=p2).

The solution is accomplished in a business application manner. It means that we prefer the software architecture to the performance (cost of operations, not an arithmetic complexity).

## Build

This project is managed by `Apache Maven` tool.
The used version is __3.6.3__.

To build the app follow the repository directory and run the foolwing command
```
mvn package
```

After the build process is succeed the executable JAR is placed under the next path `<repo_dir>/t9spelling-cmd/target/t9spelling-cmd-<version>-jar-with-dependencies.jar`.

## Usage

Run the following command:
```
java -jar t9spelling-cmd-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
```

The application requires an input as described in the [original article](https://code.google.com/codejam/contest/dashboard?c=351101#s=p2).
```
N
desired_message_1
desired_message_2
...
desired_message_N
```

For example,
```
1
hello world
```

The output of the example would be the next:
```
Case #1: 4433555 555666096667775553
```

In fact, it is more convenient to use a file as an input.
```
java -jar t9spelling-cmd-0.0.1-SNAPSHOT-jar-with-dependencies.jar < input_file.txt > output_file.txt
```

## IDE Settings

The code style follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
This style is maintained by the corresponding IntelliJ Idea [Plug-in](https://github.com/google/google-java-format).
