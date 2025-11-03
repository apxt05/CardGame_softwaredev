#!/bin/bash

# Stop on first error
set -e

# 1️⃣ Remove any old JUnit jar
rm -f junit-platform-console-standalone.jar

# 2️⃣ Download correct JUnit jar
echo "Downloading JUnit standalone jar..."
curl -L -o junit-platform-console-standalone.jar \
https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.4/junit-platform-console-standalone-1.9.4.jar

# Check if download succeeded
if [ ! -f junit-platform-console-standalone.jar ]; then
    echo "Failed to download JUnit jar. Exiting."
    exit 1
fi

# 3️⃣ Compile production code
echo "Compiling source code..."
mkdir -p out
javac -d out src/*.java

# 4️⃣ Compile test code
echo "Compiling test code..."
javac -cp out:junit-platform-console-standalone.jar -d out tests/*.java

# 5️⃣ Run tests
echo "Running tests..."
java -jar junit-platform-console-standalone.jar --class-path out --scan-class-path

echo "✅ All done."